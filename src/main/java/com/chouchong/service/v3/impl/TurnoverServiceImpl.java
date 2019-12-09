package com.chouchong.service.v3.impl;

import com.chouchong.common.*;
import com.chouchong.dao.iwant.merchant.MerchantMapper;
import com.chouchong.dao.v3.*;
import com.chouchong.dao.webUser.SysAdminMapper;
import com.chouchong.entity.iwant.merchant.Merchant;
import com.chouchong.entity.v3.*;
import com.chouchong.exception.ServiceException;
import com.chouchong.service.v3.TurnoverService;
import com.chouchong.service.v3.vo.*;
import com.chouchong.service.webUser.vo.WebUserInfo;
import com.chouchong.utils.BigDecimalUtil;
import com.chouchong.utils.TimeUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author linqin
 * @date 2019/11/18
 */
@Service
@Transactional(rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
public class TurnoverServiceImpl implements TurnoverService {

    @Autowired
    private StoreTurnoverMapper storeTurnoverMapper;

    @Autowired
    private StoreMemberChargeMapper storeMemberChargeMapper;

    @Autowired
    private StoreMemberEventMapper storeMemberEventMapper;

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

    @Autowired
    private CardRebateMapper cardRebateMapper;

    @Autowired
    private UserMemberCardMapper userMemberCardMapper;

    @Autowired
    private MembershipCardMapper membershipCardMapper;

    /**
     * 获取营业额统计列表
     *
     * @param page
     * @param eventId   活动名称
     * @param title     卡标题
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    @Override
    public Response getTurnoverList(PageQuery page, Integer eventId, String title, Long startTime, Long endTime, String phone, String storeName, Integer isExport) throws ParseException {
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
        TurnoverVos turnoverVos1 = storeTurnoverMapper.selectBySearch1(eventId, title, startTime, endTime, storeId, merchantId, phone, storeName);
        if (turnoverVos1 == null) {
            turnoverVos1 = new TurnoverVos();
        }
        if (turnoverVos1.getTotalBlagMoney() == null) {
            turnoverVos1.setTotalBlagMoney(new BigDecimal("0"));
        }
        if (turnoverVos1.getTotalTurnoverMoney() == null) {
            turnoverVos1.setTotalTurnoverMoney(new BigDecimal("0"));
        }
        if (isExport == null){
            PageHelper.startPage(page.getPageNum(), page.getPageSize());
        }
        List<TurnoverVo> turnoverVos = storeTurnoverMapper.selectBySearch(eventId, title, startTime, endTime, storeId, merchantId, phone, storeName);
        PageInfo pageInfo = new PageInfo<>(turnoverVos);
        turnoverVos1.setTurnoverVo(turnoverVos);
        turnoverVos1.setTotalMoney(BigDecimalUtil.add(turnoverVos1.getTotalBlagMoney().doubleValue(), turnoverVos1.getTotalTurnoverMoney().doubleValue()));
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
    public Response getChargeRecord(PageQuery page, String phone, String storeName, Long cardNo, Long startTime, Long endTime, Integer isExport) throws ParseException {
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
            if (chargeRes1 == null) {
                chargeRes1 = new ChargeReVos();
            }
            if (isExport == null){
                PageHelper.startPage(page.getPageNum(), page.getPageSize());
            }
            List<ChargeReVo> chargeRes = memberChargeRecordMapper.selectBySearch1(phone, storeName, cardNo, startTime, endTime, list);
            PageInfo pageInfo = new PageInfo<>(chargeRes);
            chargeRes1.setChargeReVo(chargeRes);
            return ResponseFactory.page(chargeRes1, pageInfo.getTotal(), pageInfo.getPages(),
                    pageInfo.getPageNum(), pageInfo.getPageSize());

        } else if (webUserInfo.getRoleId() == 5) {
            adminId = webUserInfo.getSysAdmin().getId();
        }
        ChargeReVos chargeRes1 = memberChargeRecordMapper.selectBySearchs(phone, storeName, cardNo, startTime, endTime, adminId);
        if (chargeRes1 == null) {
            chargeRes1 = new ChargeReVos();
        }
        if (isExport == null){
            PageHelper.startPage(page.getPageNum(), page.getPageSize());
        }
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
    public Response getExpenseRecord(PageQuery page, String phone, String storeName, Long cardNo, Long startTime, Long endTime, Integer isExport) throws ParseException {
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
            if (expenseRes1 == null) {
                expenseRes1 = new ExpenseReVos();
            }
            if (isExport == null){
                PageHelper.startPage(page.getPageNum(), page.getPageSize());
            }
            List<ExpenseReVo> expenseRes = memberExpenseRecordMapper.selectBySearch1(phone, storeName, cardNo, startTime, endTime, list);
            PageInfo pageInfo = new PageInfo<>(expenseRes);
            expenseRes1.setExpenseReVo(expenseRes);
            return ResponseFactory.page(expenseRes1, pageInfo.getTotal(), pageInfo.getPages(),
                    pageInfo.getPageNum(), pageInfo.getPageSize());
        } else if (webUserInfo.getRoleId() == 5) {
            adminId = webUserInfo.getSysAdmin().getId();
        }
        ExpenseReVos expenseRes1 = memberExpenseRecordMapper.selectBySearchs(phone, storeName, cardNo, startTime, endTime, adminId);
        if (expenseRes1 == null) {
            expenseRes1 = new ExpenseReVos();
        }
        if (isExport == null){
            PageHelper.startPage(page.getPageNum(), page.getPageSize());
        }
        List<ExpenseReVo> expenseRes = memberExpenseRecordMapper.selectBySearch(phone, storeName, cardNo, startTime, endTime, adminId);
        PageInfo pageInfo = new PageInfo<>(expenseRes);
        expenseRes1.setExpenseReVo(expenseRes);
        return ResponseFactory.page(expenseRes1, pageInfo.getTotal(), pageInfo.getPages(),
                pageInfo.getPageNum(), pageInfo.getPageSize());
    }


    /**
     * 退款记录
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
    public Response getRefundExpense(PageQuery page, String phone, String storeName, Long cardNo, Long startTime, Long endTime,Integer isExport) throws ParseException {
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
            if (isExport == null){
                PageHelper.startPage(page.getPageNum(), page.getPageSize());
            }
            List<RefundVo> refundVos = cardRebateMapper.selectBySearch1(phone, storeName, cardNo, startTime, endTime, list);
            PageInfo pageInfo = new PageInfo<>(refundVos);
            return ResponseFactory.page(refundVos, pageInfo.getTotal(), pageInfo.getPages(),
                    pageInfo.getPageNum(), pageInfo.getPageSize());
        } else if (webUserInfo.getRoleId() == 5) {
            adminId = webUserInfo.getSysAdmin().getId();
        }
        if (isExport == null){
            PageHelper.startPage(page.getPageNum(), page.getPageSize());
        }
        List<RefundVo> refundVos = cardRebateMapper.selectBySearch(phone, storeName, cardNo, startTime, endTime, adminId);
        PageInfo pageInfo = new PageInfo<>(refundVos);
        return ResponseFactory.page(refundVos, pageInfo.getTotal(), pageInfo.getPages(),
                pageInfo.getPageNum(), pageInfo.getPageSize());
    }

    /**
     * 退回扣款
     *
     * @param rebate
     * @return
     */
    @Override
    public Response refundExpense(CardRebate rebate, String password) {
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        Integer adminId = webUserInfo.getSysAdmin().getId();
        // 查询门店信息
        Store store = storeMapper.selectByAdminId(adminId);
        if (store == null) {
            return ResponseFactory.err("操作失败！");
        }
        if (StringUtils.isBlank(store.getPassword())) {
            return ResponseFactory.err("未设置密码，请联系公司设置密码！");
        }
        // 校验密码
        String s = Utils.toMD5(password + Constants.STOREPWD);
        if (!store.getPassword().equals(s)) {
            return ResponseFactory.err("密码错误！");
        }
        // 查询会员卡类型
        Byte type = membershipCardMapper.selectTypeById(rebate.getMembershipCardId());
        // 退款
        // 退回门店金额详情表消费,并删除，删除营业额记录
        if (type == 11){
            // 活动卡
            rebateStoreMemberEvent(rebate.getMembershipCardId(),rebate.getUserId(),rebate.getOrderNo());
        }else {
            rebateStoreMemberCharge(rebate.getMembershipCardId(),rebate.getUserId(),rebate.getOrderNo());
        }
        // 删除消费记录
        int i = memberExpenseRecordMapper.deleteByPrimaryKey(rebate.getExpenseRecordId());
        if (i < 1) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "删除消费记录失败");
        }
        // 退回用户余额
        UserMemberCard user = userMemberCardMapper.selectByUseridcardId(rebate.getUserId(), rebate.getMembershipCardId());
        if (user != null){
            user.setBalance(BigDecimalUtil.add(user.getBalance().doubleValue(),rebate.getMoney().doubleValue()));
            int i1 = userMemberCardMapper.updateByPrimaryKeySelective(user);
            if (i1 < 1) {
                throw new ServiceException(ErrorCode.ERROR.getCode(), "退回用户余额失败");
            }
        }
        // 添加退款记录
        rebate.setStatus((byte) 1);
        rebate.setAdminId(adminId);
        addCardRebate(rebate);
        return ResponseFactory.sucMsg("退款成功");
    }

    /**
     * 退回门店金额详情表消费（储值卡）
     *
     * @param cardId  会员卡id
     * @param userId  用户id
     * @param orderNo 订单号
     */
    private void rebateStoreMemberCharge(Integer cardId, Integer userId, Long orderNo) {
        // 查询消费详情记录
        StoreMemberCharge charge = storeMemberChargeMapper.selectByUserIdCardIdOrderNo(cardId, userId, orderNo);
        if (charge == null) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "金额详情记录不存在");
        }
        List<StoreTurnover> lists = storeTurnoverMapper.selectByStoreMId(charge.getId(),(byte)1);
        if (!CollectionUtils.isEmpty(lists)) {
            for (StoreTurnover list : lists) {
                // 总扣款金额
                BigDecimal add = BigDecimalUtil.add(list.getBlagMoney().doubleValue(), list.getTurnoverMoney().doubleValue());
                // 查询被扣款的记录并更新记录
                StoreMemberCharge charge1 = storeMemberChargeMapper.selectByPrimaryKey(list.getStoreChargeId());
                if (charge1 != null) {
                    // 退回余额
                    BigDecimal balance = BigDecimalUtil.add(charge1.getBalance().doubleValue(), add.doubleValue());
                    charge1.setBalance(balance);
                    if (balance.compareTo(charge1.getTotalMoney()) == 0) {
                        charge1.setStatus((byte) 1);
                    } else {
                        charge1.setStatus((byte) 2);
                    }
                    int i = storeMemberChargeMapper.updateByPrimaryKeySelective(charge1);
                    if (i < 1) {
                        throw new ServiceException(ErrorCode.ERROR.getCode(), "更新失败");
                    }
                }
                // 删除营业额记录
                int i = storeTurnoverMapper.deleteByPrimaryKey(list.getId());
                if (i < 1) {
                    throw new ServiceException(ErrorCode.ERROR.getCode(), "删除营业额记录失败");
                }
            }
        }
        //删除门店详情扣款记录
        int i = storeMemberChargeMapper.deleteByPrimaryKey(charge.getId());
        if (i < 1) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "删除门店详情扣款记录失败");
        }
    }

    /**
     * 退回门店金额详情表消费（活动卡）
     *
     * @param cardId  会员卡id
     * @param userId  用户id
     * @param orderNo 订单号
     */
    private void rebateStoreMemberEvent(Integer cardId, Integer userId, Long orderNo) {
        // 查询消费详情记录
        StoreMemberEvent event = storeMemberEventMapper.selectByUserIdCardIdOrderNo(cardId, userId, orderNo);
        if (event == null) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "金额详情记录不存在");
        }
        List<StoreTurnover> lists = storeTurnoverMapper.selectByStoreMId(event.getId(),(byte)2);
        if (!CollectionUtils.isEmpty(lists)) {
            for (StoreTurnover list : lists) {
                // 查询被扣款的记录并更新记录
                StoreMemberEvent event1 = storeMemberEventMapper.selectByPrimaryKey(list.getStoreChargeId());
                if (event1 != null) {
                    // 退回余额
                    BigDecimal ca = BigDecimalUtil.add(list.getBlagMoney().doubleValue(), event1.getCapitalBalance().doubleValue());
                    BigDecimal se = BigDecimalUtil.add(list.getTurnoverMoney().doubleValue(), event1.getSendBalance().doubleValue());
                    event1.setCapitalBalance(ca);
                    event1.setSendBalance(se);
                    if (ca.compareTo(event1.getCapitalMoney()) == 0) {
                        event1.setCapitalStatus((byte) 1);
                    } else {
                        event1.setCapitalStatus((byte) 2);
                    }
                    if (se.compareTo(event1.getSendMoney()) == 0) {
                        event1.setSendStatus((byte) 1);
                    } else {
                        event1.setSendStatus((byte) 2);
                    }
                    int i = storeMemberEventMapper.updateByPrimaryKeySelective(event1);
                    if (i < 1) {
                        throw new ServiceException(ErrorCode.ERROR.getCode(), "更新失败");
                    }
                }
                // 删除营业额记录
                int i = storeTurnoverMapper.deleteByPrimaryKey(list.getId());
                if (i < 1) {
                    throw new ServiceException(ErrorCode.ERROR.getCode(), "删除营业额记录失败");
                }
            }
        }
        //删除门店详情扣款记录
        int i = storeMemberEventMapper.deleteByPrimaryKey(event.getId());
        if (i < 1) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "删除门店详情扣款记录失败");
        }
    }

    /**
     * 添加退款记录
     *
     * @param rebate
     */
    private void addCardRebate(CardRebate rebate) {
        int insert = cardRebateMapper.insert(rebate);
        if (insert < 1) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "添加退款记录失败");
        }
    }

}
