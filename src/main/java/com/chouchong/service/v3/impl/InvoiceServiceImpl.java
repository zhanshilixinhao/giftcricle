package com.chouchong.service.v3.impl;

import com.chouchong.common.ErrorCode;
import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.v3.InvoiceRecordMapper;
import com.chouchong.dao.v3.MemberExpenseRecordMapper;
import com.chouchong.dao.v3.StoreMapper;
import com.chouchong.entity.v3.InvoiceRecord;
import com.chouchong.entity.v3.MemberExpenseRecord;
import com.chouchong.entity.v3.Store;
import com.chouchong.exception.ServiceException;
import com.chouchong.service.v3.InvoiceService;
import com.chouchong.service.v3.vo.*;
import com.chouchong.service.webUser.vo.WebUserInfo;
import com.chouchong.utils.BigDecimalUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
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
    private MemberExpenseRecordMapper memberExpenseRecordMapper;
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
        InvoiceRecord record = new InvoiceRecord();
        record.setCardId(invoice.getCardId());
        record.setUserId(invoice.getUserId());
        record.setAmount(invoice.getAmount());
        record.setSummary(invoice.getSummary());
        record.setStoreId(store.getId());
        record.setAdminId(webUserInfo.getSysAdmin().getId());
        int insert = invoiceRecordMapper.insert(record);
        if (insert < 1) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "添加失败");
        }
        return ResponseFactory.sucMsg("添加成功");
    }

    /**
     * 获取发票列表
     *
     * @param page
     * @return
     */
    @Override
    public Response getInvoice(Integer cardId,Integer userId) {
        InvoiceVo1 vo1 = new InvoiceVo1();
        // 查询总消费
        BigDecimal ex = new BigDecimal("0");
        List<ExpenseVo> expenseVos = memberExpenseRecordMapper.selectByUserIdCardId(userId, cardId);
        if (!CollectionUtils.isEmpty(expenseVos)){
            for (ExpenseVo expenseVo : expenseVos) {
                ex = BigDecimalUtil.add(ex.doubleValue(),expenseVo.getExpenseMoney().doubleValue());
            }
        }
        vo1.setTotalExpense(ex);
        // 总开票
        BigDecimal ni = new BigDecimal("0");
        // 查询开票列表
        List<InvoiceVo> invoiceVos = invoiceRecordMapper.selectByUserIdCardId(userId,cardId);
        if (!CollectionUtils.isEmpty(invoiceVos)){
            for (InvoiceVo invoiceVo : invoiceVos) {
                ni = BigDecimalUtil.add(ni.doubleValue(),invoiceVo.getAmount().doubleValue());
            }
        }
        vo1.setTotalInvoice(ni);
        vo1.setInvoiceVos(invoiceVos);
        vo1.setAmount(BigDecimalUtil.sub(ex.doubleValue(),ni.doubleValue()));
        return ResponseFactory.sucData(vo1);
    }

}
