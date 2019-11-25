package com.chouchong.service.v3.impl;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.iwant.merchant.MerchantMapper;
import com.chouchong.dao.v3.MemberChargeRecordMapper;
import com.chouchong.dao.v3.MemberExpenseRecordMapper;
import com.chouchong.dao.v3.StoreMapper;
import com.chouchong.dao.v3.StoreTurnoverMapper;
import com.chouchong.dao.webUser.SysAdminMapper;
import com.chouchong.entity.iwant.merchant.Merchant;
import com.chouchong.entity.v3.Store;
import com.chouchong.service.v3.TurnoverService;
import com.chouchong.service.v3.vo.*;
import com.chouchong.service.webUser.vo.WebUserInfo;
import com.chouchong.utils.TimeUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author linqin
 * @date 2019/11/18
 */
@Service
public class TurnoverServiceImpl implements TurnoverService {

    @Autowired
    private StoreTurnoverMapper storeTurnoverMapper;

    @Autowired
    private MemberChargeRecordMapper memberChargeRecordMapper;

    @Autowired
    private MemberExpenseRecordMapper memberExpenseRecordMapper;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private SysAdminMapper sysAdminMapper;

    @Autowired
    private MerchantMapper merchantMapper;

    @Autowired
    private StoreMapper storeMapper;

    /**
     * 获取营业额统计列表
     *
     * @param page
     * @param eventName 活动名称
     * @param title     卡标题
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    @Override
    public Response getTurnoverList(PageQuery page, String eventName, String title, Long startTime, Long endTime) throws ParseException {
        if (startTime != null) {
            startTime = TimeUtils.time(startTime);
        }
        if (endTime != null) {
            endTime = TimeUtils.timeEnd(endTime);
        }
        // 角色
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        Integer storeId = null;
        Integer merchantId = null;
        // 商家
        if (webUserInfo.getRoleId() == 3) {
            Merchant merchant = merchantMapper.selectByAdminId(webUserInfo.getSysAdmin().getId());
            if (merchant != null) {
                merchantId = merchant.getId();
            }
        } else if (webUserInfo.getRoleId() == 5) {
            Store store = storeMapper.selectByAdminId(webUserInfo.getSysAdmin().getId());
            if (store != null) {
                storeId = store.getId();
            }
        }
        TurnoverVos turnoverVos1 = storeTurnoverMapper.selectBySearch1(eventName, title, startTime, endTime,storeId,merchantId);
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<TurnoverVo> turnoverVos = storeTurnoverMapper.selectBySearch(eventName, title, startTime, endTime,storeId,merchantId);
        PageInfo pageInfo = new PageInfo<>(turnoverVos);
        turnoverVos1.setTurnoverVo(turnoverVos);
        return ResponseFactory.page(turnoverVos1, pageInfo.getTotal(), pageInfo.getPages(),
                pageInfo.getPageNum(), pageInfo.getPageSize());
    }


    /**
     * 充值记录
     *
     * @param page
     * @param phone     电话号码
     * @param storeName 门店名称
     * @param cardNo    卡号
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    @Override
    public Response getChargeRecord(PageQuery page, String phone, String storeName, Long cardNo, Long startTime, Long endTime) throws ParseException {
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
            ChargeReVos chargeRes1 = memberChargeRecordMapper.selectBySearch1s(phone, storeName, cardNo, startTime, endTime, list);
            PageHelper.startPage(page.getPageNum(), page.getPageSize());
            List<ChargeReVo> chargeRes = memberChargeRecordMapper.selectBySearch1(phone, storeName, cardNo, startTime, endTime, list);
            PageInfo pageInfo = new PageInfo<>(chargeRes);
            chargeRes1.setChargeReVo(chargeRes);
            return ResponseFactory.page(chargeRes1, pageInfo.getTotal(), pageInfo.getPages(),
                    pageInfo.getPageNum(), pageInfo.getPageSize());

        } else if (webUserInfo.getRoleId() == 5) {
            adminId = webUserInfo.getSysAdmin().getId();
        }
        ChargeReVos chargeRes1 = memberChargeRecordMapper.selectBySearchs(phone, storeName, cardNo, startTime, endTime, adminId);
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<ChargeReVo> chargeRes = memberChargeRecordMapper.selectBySearch(phone, storeName, cardNo, startTime, endTime, adminId);
        PageInfo pageInfo = new PageInfo<>(chargeRes);
        chargeRes1.setChargeReVo(chargeRes);
        return ResponseFactory.page(chargeRes1, pageInfo.getTotal(), pageInfo.getPages(),
                pageInfo.getPageNum(), pageInfo.getPageSize());
    }


    /**
     * 扣款记录
     *
     * @param page
     * @param phone     电话号码
     * @param storeName 门店名称
     * @param cardNo    卡号
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    @Override
    public Response getExpenseRecord(PageQuery page, String phone, String storeName, Long cardNo, Long startTime, Long endTime) throws ParseException {
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
            ExpenseReVos expenseRes1 = memberExpenseRecordMapper.selectBySearch1s(phone, storeName, cardNo, startTime, endTime, list);
            PageHelper.startPage(page.getPageNum(), page.getPageSize());
            List<ExpenseReVo> expenseRes = memberExpenseRecordMapper.selectBySearch1(phone, storeName, cardNo, startTime, endTime, list);
            PageInfo pageInfo = new PageInfo<>(expenseRes);
            expenseRes1.setExpenseReVo(expenseRes);
            return ResponseFactory.page(expenseRes1, pageInfo.getTotal(), pageInfo.getPages(),
                    pageInfo.getPageNum(), pageInfo.getPageSize());
        } else if (webUserInfo.getRoleId() == 5) {
            adminId = webUserInfo.getSysAdmin().getId();
        }
        ExpenseReVos expenseRes1 = memberExpenseRecordMapper.selectBySearchs(phone, storeName, cardNo, startTime, endTime, adminId);
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<ExpenseReVo> expenseRes = memberExpenseRecordMapper.selectBySearch(phone, storeName, cardNo, startTime, endTime, adminId);
        PageInfo pageInfo = new PageInfo<>(expenseRes);
        expenseRes1.setExpenseReVo(expenseRes);
        return ResponseFactory.page(expenseRes1, pageInfo.getTotal(), pageInfo.getPages(),
                pageInfo.getPageNum(), pageInfo.getPageSize());
    }
}
