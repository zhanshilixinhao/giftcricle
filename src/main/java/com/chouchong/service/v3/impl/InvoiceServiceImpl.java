package com.chouchong.service.v3.impl;

import com.chouchong.common.ErrorCode;
import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.v3.InvoiceRecordMapper;
import com.chouchong.dao.v3.MemberChargeRecordMapper;
import com.chouchong.dao.v3.StoreMapper;
import com.chouchong.dao.webUser.SysAdminMapper;
import com.chouchong.entity.v3.InvoiceRecord;
import com.chouchong.entity.v3.Store;
import com.chouchong.exception.ServiceException;
import com.chouchong.service.v3.InvoiceService;
import com.chouchong.service.v3.vo.*;
import com.chouchong.service.webUser.vo.WebUserInfo;
import com.chouchong.utils.BigDecimalUtil;
import com.chouchong.utils.TimeUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

/**
 * @author linqin
 * @date 2020/2/13 17:17
 */
@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    private InvoiceRecordMapper invoiceRecordMapper;

    @Autowired
    private MemberChargeRecordMapper memberChargeRecordMapper;

    @Autowired
    private SysAdminMapper sysAdminMapper;

    /**
     * 添加发票记录
     *
     * @param invoice
     * @return
     */
    @Override
    public Response addInvoice(InvoiceRecord invoice) {
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        Store store = storeMapper.selectByAdminId(webUserInfo.getSysAdmin().getId());
        if (store == null) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "添加发票记录出错");
        }
        // 校验开票金额是否正确
        Response response = getInvoice(invoice.getCardId(), invoice.getUserId());
        BigDecimal amount = ((InvoiceVo1) response.getData()).getAmount();
        if (amount == null || amount.compareTo(invoice.getAmount()) < 0) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "发票金额最多可填" + amount + "元");
        }
        InvoiceRecord record = new InvoiceRecord();
        record.setCardId(invoice.getCardId());
        record.setUserId(invoice.getUserId());
        record.setAmount(invoice.getAmount());
        record.setSummary(invoice.getSummary());
        record.setStoreId(store.getId());
        record.setAdminId(webUserInfo.getSysAdmin().getId());
        record.setImage(invoice.getImage());
        int insert = invoiceRecordMapper.insert(record);
        if (insert < 1) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "添加失败");
        }
        return ResponseFactory.sucMsg("添加成功");
    }

    /**
     * 获取发票列表
     *
     * @param
     * @return
     */
    @Override
    public Response getInvoice(Integer cardId, Integer userId) {
        InvoiceVo1 vo1 = new InvoiceVo1();
        // 查询总充值
        BigDecimal ch = new BigDecimal("0");
        List<ChargeVo> chargeVos = memberChargeRecordMapper.selectByUserIdCardId(userId, cardId);
        if (!CollectionUtils.isEmpty(chargeVos)) {
            for (ChargeVo chargeVo : chargeVos) {
                ch = BigDecimalUtil.add(ch.doubleValue(), chargeVo.getRechargeMoney().doubleValue());
            }
        }
        vo1.setTotalCharge(ch);
        // 总开票
        BigDecimal ni = new BigDecimal("0");
        // 查询开票列表
        List<InvoiceVo> invoiceVos = invoiceRecordMapper.selectByUserIdCardId(userId, cardId);
        if (!CollectionUtils.isEmpty(invoiceVos)) {
            for (InvoiceVo invoiceVo : invoiceVos) {
                ni = BigDecimalUtil.add(ni.doubleValue(), invoiceVo.getAmount().doubleValue());
//                if (!StringUtils.isEmpty(invoiceVo.getImage())){
//                    invoiceVo.setImage("https://liyuquan.cn/static" + invoiceVo.getImage());
//                }
            }
        }
        vo1.setTotalInvoice(ni);
        vo1.setInvoiceVos(invoiceVos);
        vo1.setAmount(BigDecimalUtil.sub(ch.doubleValue(), ni.doubleValue()));
        return ResponseFactory.sucData(vo1);
    }


    /**
     * 发票记录列表查询
     * @param page
     * @param phone 号码
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public Response getInvoiceList(PageQuery page, String phone, Long startTime, Long endTime) throws ParseException {
        if (startTime != null) {
            startTime = TimeUtils.time(startTime);
        }
        if (endTime != null) {
            endTime = TimeUtils.timeEnd(endTime);
        }
        // 角色
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        Integer adminId = null;
        // 商家
        if (webUserInfo.getRoleId() == 3) {
            Integer cAdminId = webUserInfo.getSysAdmin().getId();
            List<Integer> list = sysAdminMapper.selectIdByCreatedId(cAdminId);
            if (list.size() == 0) {
                return ResponseFactory.suc();
            }
            PageHelper.startPage(page.getPageNum(), page.getPageSize());
            List<InvoiceVo> invoiceVos = invoiceRecordMapper.selectByKey(phone, startTime,endTime,list);
            PageInfo pageInfo = new PageInfo<>(invoiceVos);
            return ResponseFactory.page(invoiceVos, pageInfo.getTotal(), pageInfo.getPages(),
                    pageInfo.getPageNum(), pageInfo.getPageSize());

        } else if (webUserInfo.getRoleId() == 5) {
            adminId = webUserInfo.getSysAdmin().getId();
        }
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<InvoiceVo> invoiceVos = invoiceRecordMapper.selectByKey1(phone, startTime,endTime, adminId);
        PageInfo pageInfo = new PageInfo<>(invoiceVos);
        return ResponseFactory.page(invoiceVos, pageInfo.getTotal(), pageInfo.getPages(),
                pageInfo.getPageNum(), pageInfo.getPageSize());
    }


}
