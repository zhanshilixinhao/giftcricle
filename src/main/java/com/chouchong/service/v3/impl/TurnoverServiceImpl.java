package com.chouchong.service.v3.impl;

import com.chouchong.common.*;
import com.chouchong.dao.iwant.appUser.AppUserMapper;
import com.chouchong.dao.iwant.merchant.MerchantMapper;
import com.chouchong.dao.v3.*;
import com.chouchong.dao.v4.ActivityMapper;
import com.chouchong.dao.webUser.SysAdminMapper;
import com.chouchong.entity.iwant.appUser.AppUser;
import com.chouchong.entity.iwant.merchant.Merchant;
import com.chouchong.entity.v3.*;
import com.chouchong.exception.ServiceException;
import com.chouchong.redis.MRedisTemplate;
import com.chouchong.service.v3.TurnoverService;
import com.chouchong.service.v3.vo.*;
import com.chouchong.service.webUser.vo.WebUserInfo;
import com.chouchong.utils.BigDecimalUtil;
import com.chouchong.utils.TimeUtils;
import com.chouchong.utils.sms.SentUtil2;
import com.chouchong.utils.sms.VerifyCode;
import com.chouchong.utils.sms.VerifyCodeRepository;
import com.gexin.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yunpian.sdk.model.SmsSingleSend;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
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
@Slf4j
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
    private TransferSendMapper transferSendMapper;

    @Autowired
    private UserMemberCardMapper userMemberCardMapper;

    @Autowired
    private MembershipCardMapper membershipCardMapper;

    @Autowired
    private VerifyCodeRepository verifyCodeRepository;

    @Autowired
    private MemberEventMapper memberEventMapper;

    @Autowired
    private MRedisTemplate mRedisTemplate;

    @Autowired
    private ElUserCouponMapper elUserCouponMapper;

    @Resource
    private ActivityMapper activityMapper;

    @Resource
    private AppUserMapper appUserMapper;

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
        TurnoverVos vos = new TurnoverVos();
        TurnoverMoney money = new TurnoverMoney();
        List<TurnoverMoney> turnoverVo1 = storeTurnoverMapper.selectBySearch1(eventId, title, startTime, endTime, storeId, merchantId, phone, storeName);
        if (!CollectionUtils.isEmpty(turnoverVo1)) {
            for (TurnoverMoney turnoverMoney : turnoverVo1) {
                if (turnoverMoney != null) {
                    if (turnoverMoney.getTotalTurnoverMoney() == null) {
                        turnoverMoney.setTotalTurnoverMoney(new BigDecimal("0"));
                    }
                    if (turnoverMoney.getTotalBlagMoney() == null) {
                        turnoverMoney.setTotalBlagMoney(new BigDecimal("0"));
                    }
                    money.setTotalTurnoverMoney(BigDecimalUtil.add(money.getTotalTurnoverMoney().doubleValue(), turnoverMoney.getTotalTurnoverMoney().doubleValue()));
                    money.setTotalBlagMoney(BigDecimalUtil.add(money.getTotalBlagMoney().doubleValue(), turnoverMoney.getTotalBlagMoney().doubleValue()));
                }
            }
        }
        money.setTotalMoney(BigDecimalUtil.add(money.getTotalBlagMoney().doubleValue(), money.getTotalTurnoverMoney().doubleValue()));
        if (isExport == null) {
            PageHelper.startPage(page.getPageNum(), page.getPageSize());
        }
        List<TurnoverVo> turnoverVos = storeTurnoverMapper.selectBySearch(eventId, title, startTime, endTime, storeId, merchantId, phone, storeName);
        if (!CollectionUtils.isEmpty(turnoverVos)) {
            for (TurnoverVo turnoverVo : turnoverVos) {
                turnoverVo.setTotalMoney(BigDecimalUtil.add(turnoverVo.getBlagMoney().doubleValue(), turnoverVo.getTurnoverMoney().doubleValue()));
            }
        }
        PageInfo pageInfo = new PageInfo<>(turnoverVos);
        vos.setTurnoverVo(turnoverVos);
        vos.setMoney(money);
        return ResponseFactory.page(vos, pageInfo.getTotal(), pageInfo.getPages(),
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
    public Response getChargeRecord(PageQuery page, String keywords, String phone, String storeName, Long cardNo, Long startTime, Long endTime, Integer isExport) throws ParseException {
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
            ChargeReVos chargeRes1 = memberChargeRecordMapper.selectBySearch1s(phone, keywords, storeName, cardNo, startTime, endTime, list);
            if (chargeRes1 == null) {
                chargeRes1 = new ChargeReVos();
            }
            if (isExport == null) {
                PageHelper.startPage(page.getPageNum(), page.getPageSize());
            }
            List<ChargeReVo> chargeRes = memberChargeRecordMapper.selectBySearch1(phone, keywords, storeName, cardNo, startTime, endTime, list);
            if (!CollectionUtils.isEmpty(chargeRes)) {
//                for (ChargeReVo chargeRe : chargeRes) {
//                    if (StringUtils.isNotBlank(chargeRe.getImage())){
//                        chargeRe.setImage("https://liyuquan.cn/static"+chargeRe.getImage());
//                    }
//                }
            }
            PageInfo pageInfo = new PageInfo<>(chargeRes);
            chargeRes1.setChargeReVo(chargeRes);
            return ResponseFactory.page(chargeRes1, pageInfo.getTotal(), pageInfo.getPages(),
                    pageInfo.getPageNum(), pageInfo.getPageSize());

        } else if (webUserInfo.getRoleId() == 5) {
            adminId = webUserInfo.getSysAdmin().getId();
        }
        ChargeReVos chargeRes1 = memberChargeRecordMapper.selectBySearchs(phone, keywords, storeName, cardNo, startTime, endTime, adminId);
        if (chargeRes1 == null) {
            chargeRes1 = new ChargeReVos();
        }
        if (isExport == null) {
            PageHelper.startPage(page.getPageNum(), page.getPageSize());
        }
        List<ChargeReVo> chargeRes = memberChargeRecordMapper.selectBySearch(phone, keywords, storeName, cardNo, startTime, endTime, adminId);
        if (!CollectionUtils.isEmpty(chargeRes)) {
//            for (ChargeReVo chargeRe : chargeRes) {
//                if (StringUtils.isNotBlank(chargeRe.getImage())){
//                    chargeRe.setImage("https://liyuquan.cn/static"+chargeRe.getImage());
//                }
//            }
        }
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
    public Response getExpenseRecord(PageQuery page, String phone, String storeName, Long cardNo, Long orderNo, Long startTime, Long endTime, Integer isExport) throws ParseException {
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
            ExpenseReVos expenseRes1 = memberExpenseRecordMapper.selectBySearch1s(phone, storeName, cardNo, orderNo, startTime, endTime, list);
            if (expenseRes1 == null) {
                expenseRes1 = new ExpenseReVos();
            }
            if (isExport == null) {
                PageHelper.startPage(page.getPageNum(), page.getPageSize());
            }
            List<ExpenseReVo> expenseRes = memberExpenseRecordMapper.selectBySearch1(phone, storeName, cardNo, orderNo, startTime, endTime, list);
            PageInfo pageInfo = new PageInfo<>(expenseRes);
            expenseRes1.setExpenseReVo(expenseRes);
            return ResponseFactory.page(expenseRes1, pageInfo.getTotal(), pageInfo.getPages(),
                    pageInfo.getPageNum(), pageInfo.getPageSize());
        } else if (webUserInfo.getRoleId() == 5) {
            adminId = webUserInfo.getSysAdmin().getId();
        }
        ExpenseReVos expenseRes1 = memberExpenseRecordMapper.selectBySearchs(phone, storeName, cardNo, orderNo, startTime, endTime, adminId);
        if (expenseRes1 == null) {
            expenseRes1 = new ExpenseReVos();
        }
        if (isExport == null) {
            PageHelper.startPage(page.getPageNum(), page.getPageSize());
        }
        List<ExpenseReVo> expenseRes = memberExpenseRecordMapper.selectBySearch(phone, storeName, cardNo, orderNo, startTime, endTime, adminId);
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
    public Response getRefundExpense(PageQuery page, String phone, String storeName, Long cardNo, Long startTime, Long endTime, Integer isExport) throws ParseException {
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
            if (isExport == null) {
                PageHelper.startPage(page.getPageNum(), page.getPageSize());
            }
            List<RefundVo> refundVos = cardRebateMapper.selectBySearch1(phone, storeName, cardNo, startTime, endTime, list);
            PageInfo pageInfo = new PageInfo<>(refundVos);
            return ResponseFactory.page(refundVos, pageInfo.getTotal(), pageInfo.getPages(),
                    pageInfo.getPageNum(), pageInfo.getPageSize());
        } else if (webUserInfo.getRoleId() == 5) {
            adminId = webUserInfo.getSysAdmin().getId();
        }
        if (isExport == null) {
            PageHelper.startPage(page.getPageNum(), page.getPageSize());
        }
        List<RefundVo> refundVos = cardRebateMapper.selectBySearch(phone, storeName, cardNo, startTime, endTime, adminId);
        PageInfo pageInfo = new PageInfo<>(refundVos);
        return ResponseFactory.page(refundVos, pageInfo.getTotal(), pageInfo.getPages(),
                pageInfo.getPageNum(), pageInfo.getPageSize());
    }


    /**
     * 转赠记录
     *
     * @param page
     * @param nickname  昵称
     * @param title     卡标题
     * @param status    状态
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param isExport  是否导出
     * @return
     * @throws ParseException
     * @throws
     */
    @Override
    public Response getTransferSend(PageQuery page, String nickname, String title, Byte status, Long startTime, Long endTime, Integer isExport) throws ParseException {
        if (startTime != null) {
            startTime = TimeUtils.time(startTime);
        }
        if (endTime != null) {
            endTime = TimeUtils.timeEnd(endTime);
        }
        // 角色
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        List<Integer> list = new ArrayList<>();
        // 商家
        if (webUserInfo.getRoleId() == 3) {
            //查询商家创建的所有会员卡
            List<MembershipCard> cards = membershipCardMapper.selectByAdminId1(webUserInfo.getSysAdmin().getId());
            if (!CollectionUtils.isEmpty(cards)) {
                for (MembershipCard card : cards) {
                    list.add(card.getId());
                }
            }
            if (list.size() == 0) {
                List<TransferVo> refundVos = new ArrayList<>();
                PageInfo pageInfo = new PageInfo<>(refundVos);
                return ResponseFactory.page(refundVos, pageInfo.getTotal(), pageInfo.getPages(),
                        pageInfo.getPageNum(), pageInfo.getPageSize());
            }
        } else if (webUserInfo.getRoleId() == 5) {
            //        分店adminId
            Integer adminId = webUserInfo.getSysAdmin().getId();
            // 创建者adminId(商家adminId)
            Integer createdAdminId = webUserInfo.getSysAdmin().getCreateAdminId();
            // 查询门店id
            Store store = storeMapper.selectByAdminId(adminId);
            if (store == null) {
                return ResponseFactory.suc();
            }
            List<MembershipCard> cardList = membershipCardMapper.selectByAdminId1(createdAdminId);
            if (!CollectionUtils.isEmpty(cardList)) {
                for (MembershipCard card : cardList) {
                    // 分店卡
                    String[] strings = card.getStoreIds().split(",");
                    for (String string : strings) {
                        if (string.equals(store.getId().toString())) {
                            list.add(card.getId());
                        }
                    }
                }
            }
            if (list.size() == 0) {
                List<TransferVo> refundVos = new ArrayList<>();
                PageInfo pageInfo = new PageInfo<>(refundVos);
                return ResponseFactory.page(refundVos, pageInfo.getTotal(), pageInfo.getPages(),
                        pageInfo.getPageNum(), pageInfo.getPageSize());
            }
        }
        if (isExport == null) {
            PageHelper.startPage(page.getPageNum(), page.getPageSize());
        }
        List<TransferVo> refundVos = transferSendMapper.selectBySearch(nickname, title, status, startTime, endTime, list);
        PageInfo pageInfo = new PageInfo<>(refundVos);
        return ResponseFactory.page(refundVos, pageInfo.getTotal(), pageInfo.getPages(),
                pageInfo.getPageNum(), pageInfo.getPageSize());
    }

    /**
     * 退回扣款
     *
     * @param orderNo
     * @return
     */
    @Override
    public Response refundExpense(Long orderNo, String phone, String code, String explain, Integer type1) {
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        String traceId = (String) httpServletRequest.getAttribute("traceId");
        Integer adminId = webUserInfo.getSysAdmin().getId();
        Store store = storeMapper.selectByAdminId(webUserInfo.getSysAdmin().getId());
        if (store == null) {
            return ResponseFactory.err("校验失败");
        }
        if (type1 == null) {
            if (StringUtils.isBlank(code)) {
                throw new ServiceException(ErrorCode.ERROR.getCode(), "请输入验证码");
            }
            String s = verifyCodeRepository.get(store.getPhone(), 5);
            if (StringUtils.isBlank(s) || !StringUtils.equals(s, code)) {
                return ResponseFactory.err("验证码不存在或已过期");
            }
            verifyCodeRepository.remove(store.getPhone(), 5);
        }
        MemberExpenseRecord record = memberExpenseRecordMapper.selectByOrderNo(orderNo);
        if (record == null) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "记录不存在或订单号输入错误");
        }
        log.info("traceId:{}, 消费记录:{}", traceId, JSON.toJSONString(record));
        // 校验手机号和用户
//        AppUser appUser = appUserMapper.selectByPhone1(phone);
//        if (appUser == null || !appUser.getId().equals(record.getUserId())){
//            throw new ServiceException(ErrorCode.ERROR.getCode(), "号码或订单号输入错误");
//        }
        // 查询会员卡类型
        Byte type = membershipCardMapper.selectTypeById(record.getMembershipCardId());
        // 退款
        // 退回门店金额详情表消费,并删除，删除营业额记录
        if (type == 11) {
            // 活动卡
            rebateStoreMemberEvent(record.getMembershipCardId(), record.getUserId(), orderNo);
        } else {
            rebateStoreMemberCharge(record.getMembershipCardId(), record.getUserId(), orderNo);
        }
        // 退回用户余额
        UserMemberCard user = userMemberCardMapper.selectByUseridcardId(record.getUserId(), record.getMembershipCardId());
        if (user != null) {
            log.info("traceId:{}, 用户余额退回前:{}", traceId, JSON.toJSONString(user));
            user.setBalance(BigDecimalUtil.add(user.getBalance().doubleValue(), record.getExpenseMoney().doubleValue()));
            user.setConsumeAmount(BigDecimalUtil.add(user.getConsumeAmount().doubleValue(), record.getExpenseMoney().doubleValue()));
            int i1 = userMemberCardMapper.updateByPrimaryKeySelective(user);
            if (i1 < 1) {
                throw new ServiceException(ErrorCode.ERROR.getCode(), "退回用户余额失败");
            }
            log.info("traceId:{}, 用户余额退回后:{}", traceId, JSON.toJSONString(user));
        }
        // 添加退款记录
        CardRebate rebate = new CardRebate();
        rebate.setUserId(record.getUserId());
        rebate.setMembershipCardId(record.getMembershipCardId());
        if (StringUtils.isBlank(explain)) {
            rebate.setExplain("门店消费退款");
        } else {
            rebate.setExplain(explain);
        }
        rebate.setRecordId(record.getId());
        rebate.setMoney(record.getExpenseMoney());
        rebate.setOrderNo(orderNo);
        rebate.setStatus((byte) 1);
        rebate.setAdminId(adminId);
        rebate.setType((byte) 2);
        addCardRebate(rebate);
        AppUser appUser = appUserMapper.selectByPrimaryKey(rebate.getUserId());
        SmsSingleSend smsSingleSend = SentUtil2.testSendSms(appUser.getPhone(), "【礼遇圈】您本次的退款金额是" + record.getExpenseMoney()+ "元，如有疑问，请联系门店收银人员。");
        System.out.println(smsSingleSend.toString());
        log.info("traceId:{}, 消费退款记录短信发送成功", traceId);
        log.info("traceId:{},退款记录:{}", traceId, JSON.toJSONString(rebate));
        // 删除消费记录
        int i = memberExpenseRecordMapper.deleteByPrimaryKey(record.getId());
        memberExpenseRecordMapper.selectByKey(record.getId());
        if (i < 1) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "删除消费记录失败");
        }
        log.info("traceId:{},删除消费记录成功", traceId);
        return ResponseFactory.sucMsg("退款成功");
    }


//    public Response refundExpense(CardRebate rebate, String password) {
//        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
//        Integer adminId = webUserInfo.getSysAdmin().getId();
//        // 查询门店信息
//        Store store = storeMapper.selectByAdminId(adminId);
//        if (store == null) {
//            return ResponseFactory.err("操作失败！");
//        }
//        if (StringUtils.isBlank(store.getPassword())) {
//            return ResponseFactory.err("未设置密码，请联系公司设置密码！");
//        }
//        // 校验密码
//        String s = Utils.toMD5(password + Constants.STOREPWD);
//        if (!store.getPassword().equals(s)) {
//            return ResponseFactory.err("密码错误！");
//        }
//        // 查询会员卡类型
//        Byte type = membershipCardMapper.selectTypeById(rebate.getMembershipCardId());
//        // 退款
//        // 退回门店金额详情表消费,并删除，删除营业额记录
//        if (type == 11) {
//            // 活动卡
//            rebateStoreMemberEvent(rebate.getMembershipCardId(), rebate.getUserId(), rebate.getOrderNo());
//        } else {
//            rebateStoreMemberCharge(rebate.getMembershipCardId(), rebate.getUserId(), rebate.getOrderNo());
//        }
//        // 删除消费记录
//        int i = memberExpenseRecordMapper.deleteByPrimaryKey(rebate.getExpenseRecordId());
//        if (i < 1) {
//            throw new ServiceException(ErrorCode.ERROR.getCode(), "删除消费记录失败");
//        }
//        // 退回用户余额
//        UserMemberCard user = userMemberCardMapper.selectByUseridcardId(rebate.getUserId(), rebate.getMembershipCardId());
//        if (user != null) {
//            user.setBalance(BigDecimalUtil.add(user.getBalance().doubleValue(), rebate.getMoney().doubleValue()));
//            int i1 = userMemberCardMapper.updateByPrimaryKeySelective(user);
//            if (i1 < 1) {
//                throw new ServiceException(ErrorCode.ERROR.getCode(), "退回用户余额失败");
//            }
//        }
//        // 添加退款记录
//        rebate.setStatus((byte) 1);
//        rebate.setAdminId(adminId);
//        addCardRebate(rebate);
//        return ResponseFactory.sucMsg("退款成功");
//    }


    /**
     * 充值退回扣款
     *
     * @param orderNo
     * @param phone
     * @return
     */
    @Override
    public Response refundCharge(Long orderNo, String phone, String code, String explain) {
        String traceId = (String) httpServletRequest.getAttribute("traceId");
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        Integer adminId = webUserInfo.getSysAdmin().getId();
        Store store = storeMapper.selectByAdminId(webUserInfo.getSysAdmin().getId());
        if (store == null) {
            return ResponseFactory.err("校验失败");
        }
        String s = verifyCodeRepository.get(store.getPhone(), 5);
        if (StringUtils.isBlank(s) || !StringUtils.equals(s, code)) {
            return ResponseFactory.err("验证码不存在或已过期");
        }
        verifyCodeRepository.remove(store.getPhone(), 5);
        MemberChargeRecord record = memberChargeRecordMapper.selectByOrderNo(orderNo);
        if (record == null) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "充值记录不存在");
        }
        log.info("traceId:{}, 充值记录:{}", traceId, JSON.toJSONString(record));
        BigDecimal add = BigDecimalUtil.add(record.getRechargeMoney().doubleValue(), record.getSendMoney().doubleValue());
        log.info("traceId:{}, 总退款:{}", traceId, add);
        // 查询会员卡类型
        Byte type = membershipCardMapper.selectTypeById(record.getMembershipCardId());
        // 退款
        if (type == 11) {
            // 活动卡
            StoreMemberEvent event = storeMemberEventMapper.selectByUserIdCardIdOrderNo1(record.getMembershipCardId(), record.getUserId(), orderNo);
            if (event == null) {
                throw new ServiceException(ErrorCode.ERROR.getCode(), "金额详情记录不存在1");
            }
            log.info("traceId:{}, 充值记录详情:{}", traceId, JSON.toJSONString(event));
            if (event.getCapitalStatus() != 1 || event.getSendStatus() != 1) {
                throw new ServiceException(ErrorCode.ERROR.getCode(), "您已消费过无法再退1");
            }
            storeMemberEventMapper.selectByKey(event.getId());
            storeMemberEventMapper.deleteByPrimaryKey(event.getId());
            log.info("traceId:{}, 充值记录详情删除成功", traceId);
        } else {
            StoreMemberCharge charge = storeMemberChargeMapper.selectByUserIdCardIdOrderNo1(record.getMembershipCardId(), record.getUserId(), orderNo);
            if (charge == null) {
                throw new ServiceException(ErrorCode.ERROR.getCode(), "金额详情记录不存在2");
            }
            log.info("traceId:{}, 充值记录详情:{}", traceId, JSON.toJSONString(charge));
            if (charge.getStatus() != 1) {
                throw new ServiceException(ErrorCode.ERROR.getCode(), "您已消费过无法再退");
            }
            // 查看是否赠送优惠券
            check(record);
            storeMemberChargeMapper.selectByKey(charge.getId());
            storeMemberChargeMapper.deleteByPrimaryKey(charge.getId());
            log.info("traceId:{}, 充值记录详情删除成功", traceId);
        }
        // 扣除用户余额
        UserMemberCard user = userMemberCardMapper.selectByUseridcardId(record.getUserId(), record.getMembershipCardId());
        if (user == null) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "用户余额不存在");
        }
        log.info("traceId:{}, 扣除前用户余额:{}", traceId, JSON.toJSONString(user));
        user.setBalance(BigDecimalUtil.sub(user.getBalance().doubleValue(), add.doubleValue()));
        user.setTotalAmount(BigDecimalUtil.sub(user.getTotalAmount().doubleValue(), add.doubleValue()));
        int i1 = userMemberCardMapper.updateByPrimaryKeySelective(user);
        if (i1 < 1) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "退回用户余额失败");
        }
        log.info("traceId:{}, 扣除后用户余额:{}", traceId, JSON.toJSONString(user));
        // 添加退款记录
        CardRebate rebate = new CardRebate();
        rebate.setUserId(record.getUserId());
        rebate.setMembershipCardId(record.getMembershipCardId());
        if (StringUtils.isBlank(explain)) {
            rebate.setExplain("门店充值退款");
        } else {
            rebate.setExplain(explain);
        }
        rebate.setRecordId(record.getId());
        rebate.setMoney(add);
        rebate.setOrderNo(orderNo);
        rebate.setStatus((byte) 1);
        rebate.setAdminId(adminId);
        rebate.setType((byte) 1);
        addCardRebate(rebate);
        log.info("traceId:{}, 退款记录:{}", traceId, JSON.toJSONString(rebate));
        // 删除充值记录
        memberChargeRecordMapper.selectByKey(record.getId());
        int i = memberChargeRecordMapper.deleteByPrimaryKey(record.getId());
        if (i < 1) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "删除记录失败");
        }

        AppUser appUser = appUserMapper.selectByPrimaryKey(rebate.getUserId());
        SmsSingleSend smsSingleSend = SentUtil2.testSendSms(appUser.getPhone(), "【礼遇圈】您本次的退款金额是" + add + "元，如有疑问，请联系门店收银人员。");
        System.out.println(smsSingleSend.toString());
        log.info("traceId:{}, 充值退款记录短信发送成功", traceId);
        log.info("traceId:{}, 删除充值记录成功", traceId);
        return ResponseFactory.sucMsg("退款成功");
    }

    private void check(MemberChargeRecord record) {
        String traceId = (String) httpServletRequest.getAttribute("traceId");
        if (record.getMemberEventId() != null) {
            String key = "add_coupon" + record.getUserId();
            MemberEvent memberEvent = activityMapper.selectByPrimaryKey(record.getMemberEventId());
           // MemberEvent memberEvent = memberEventMapper.selectByPrimaryKey(record.getMemberEventId());
            if (memberEvent == null) {
                throw new ServiceException(ErrorCode.ERROR.getCode(), "会员卡活动出错");
            }
            log.info("traceId:{}, 活动:{}", traceId, JSON.toJSONString(memberEvent));
            if ((memberEvent.getType() == 2 || memberEvent.getType() == 5) && memberEvent.getTargetId() != null) {
                // 赠送优惠券
                String id = mRedisTemplate.getString(key);
                if (!StringUtils.isBlank(id)) {
                    ElUserCoupon userCoupon = elUserCouponMapper.selectByKey(Long.parseLong(id));
                    if (userCoupon == null) {
                        throw new ServiceException(ErrorCode.ERROR.getCode(), "赠送的优惠券出错");
                    }
                    if (!userCoupon.getQuantity().equals(memberEvent.getQuantity())) {
                        throw new ServiceException(ErrorCode.ERROR.getCode(), "优惠券已使用过，无法再退");
                    }
                    log.info("traceId:{}, 赠送优惠券:{}", traceId, JSON.toJSONString(userCoupon));
                    // 删除优惠券
                    elUserCouponMapper.deleteById(Long.parseLong(id));
                    log.info("traceId:{}, 删除优惠券成功", traceId);
                }
            }
            mRedisTemplate.del(key);
            log.info("traceId:{}, 删除优惠券缓存成功", traceId);
        }
    }

    /**
     * 退回门店金额详情表消费（储值卡）
     *
     * @param cardId  会员卡id
     * @param userId  用户id
     * @param orderNo 订单号
     */
    private void rebateStoreMemberCharge(Integer cardId, Integer userId, Long orderNo) {
        String traceId = (String) httpServletRequest.getAttribute("traceId");
        // 查询消费详情记录
        StoreMemberCharge charge = storeMemberChargeMapper.selectByUserIdCardIdOrderNo(cardId, userId, orderNo);
        if (charge == null) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "金额详情记录不存在");
        }
        log.info("traceId:{}, 消费详情记录:{}", traceId, JSON.toJSONString(charge));
        List<StoreTurnover> lists = storeTurnoverMapper.selectByStoreMId(charge.getId(), (byte) 1);
        if (!CollectionUtils.isEmpty(lists)) {
            log.info("traceId:{}, 营业额记录:{}", traceId, JSON.toJSONString(lists));
            for (StoreTurnover list : lists) {
                log.info("traceId:{}, 营业额更新前记录:{}", traceId, JSON.toJSONString(list));
                // 总扣款金额
                BigDecimal add = BigDecimalUtil.add(list.getBlagMoney().doubleValue(), list.getTurnoverMoney().doubleValue());
                log.info("traceId:{}, 总扣款金额:{}", traceId, add);
                // 查询被扣款的记录并更新记录
                StoreMemberCharge charge1 = storeMemberChargeMapper.selectByPrimaryKey(list.getStoreChargeId());
                log.info("traceId:{}, 被扣款的记录前:{}", traceId, JSON.toJSONString(charge1));
                if (charge1 != null) {
                    // 退回余额
                    BigDecimal balance = BigDecimalUtil.add(charge1.getBalance().doubleValue(), add.doubleValue());
                    log.info("traceId:{}, 退回余额:{}", traceId, balance);
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
                    log.info("traceId:{}, 被扣款的记录更新后:{}", traceId, JSON.toJSONString(charge1));
                }
                // 更新营业额记录状态
                list.setStatus((byte) -1);
                int i = storeTurnoverMapper.updateByPrimaryKeySelective(list);
                if (i < 1) {
                    throw new ServiceException(ErrorCode.ERROR.getCode(), "删除营业额记录失败");
                }
                log.info("traceId:{}, 营业额更新后记录:{}", traceId, JSON.toJSONString(list));
            }
        }
        //删除门店详情扣款记录
        storeMemberChargeMapper.selectByKey(charge.getId());
        int i = storeMemberChargeMapper.deleteByPrimaryKey(charge.getId());
        if (i < 1) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "删除门店详情扣款记录失败");
        }
        log.info("traceId:{}, 删除删除门店详情扣款记录成功", traceId);
    }

    /**
     * 退回门店金额详情表消费（活动卡）
     *
     * @param cardId  会员卡id
     * @param userId  用户id
     * @param orderNo 订单号
     */
    private void rebateStoreMemberEvent(Integer cardId, Integer userId, Long orderNo) {
        String traceId = (String) httpServletRequest.getAttribute("traceId");
        // 查询消费详情记录
        StoreMemberEvent event = storeMemberEventMapper.selectByUserIdCardIdOrderNo(cardId, userId, orderNo);
        if (event == null) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "金额详情记录不存在");
        }
        log.info("traceId:{}, 消费详情记录:{}", traceId, JSON.toJSONString(event));
        List<StoreTurnover> lists = storeTurnoverMapper.selectByStoreMId(event.getId(), (byte) 2);
        if (!CollectionUtils.isEmpty(lists)) {
            log.info("traceId:{}, 营业额记录:{}", traceId, JSON.toJSONString(lists));
            for (StoreTurnover list : lists) {
                log.info("traceId:{}, 营业额更新前记录:{}", traceId, JSON.toJSONString(list));
                // 查询被扣款的记录并更新记录
                StoreMemberEvent event1 = storeMemberEventMapper.selectByPrimaryKey(list.getStoreChargeId());
                log.info("traceId:{}, 被扣款的记录:{}", traceId, JSON.toJSONString(event1));
                if (event1 != null) {
                    // 退回余额
                    BigDecimal ca = BigDecimalUtil.add(list.getBlagMoney().doubleValue(), event1.getCapitalBalance().doubleValue());
                    BigDecimal se = BigDecimalUtil.add(list.getTurnoverMoney().doubleValue(), event1.getSendBalance().doubleValue());
                    log.info("traceId:{}, 退回余额本金:{}， 退回余额赠送:{}", traceId, ca, se);
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
                    log.info("traceId:{}, 被扣款的记录更新后:{}", traceId, JSON.toJSONString(event1));
                }
                // 更新营业额记录状态
                list.setStatus((byte) -1);
                int i = storeTurnoverMapper.updateByPrimaryKeySelective(list);
                if (i < 1) {
                    throw new ServiceException(ErrorCode.ERROR.getCode(), "删除营业额记录失败");
                }
                log.info("traceId:{}, 营业额更新后记录:{}", traceId, JSON.toJSONString(list));
            }
        }
        //删除门店详情扣款记录
        storeMemberEventMapper.selectByKey(event.getId());
        int i = storeMemberEventMapper.deleteByPrimaryKey(event.getId());
        if (i < 1) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "删除门店详情扣款记录失败");
        }
        log.info("traceId:{}, 删除删除门店详情扣款记录成功", traceId);
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
