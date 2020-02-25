package com.chouchong.service.v3.impl;

import com.chouchong.common.*;
import com.chouchong.dao.iwant.appUser.AppUserMapper;
import com.chouchong.dao.iwant.merchant.MerchantMapper;
import com.chouchong.dao.v3.*;
import com.chouchong.entity.iwant.appUser.AppUser;
import com.chouchong.entity.iwant.merchant.Merchant;
import com.chouchong.entity.v3.*;
import com.chouchong.exception.ServiceException;
import com.chouchong.service.v3.ElCouponService;
import com.chouchong.service.v3.UserCardService;
import com.chouchong.service.v3.vo.*;
import com.chouchong.service.webUser.vo.WebUserInfo;
import com.chouchong.utils.BigDecimalUtil;
import com.chouchong.utils.sms.SendUtil;
import com.chouchong.utils.sms.SmsSendResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.formula.functions.Now;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author linqin
 * @date 2019/11/11
 */
@Service
@Transactional(rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
public class UserCardServiceImpl implements UserCardService {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private UserMemberCardMapper userMemberCardMapper;

    @Autowired
    private MemberChargeRecordMapper memberChargeRecordMapper;

    @Autowired
    private MemberExpenseRecordMapper memberExpenseRecordMapper;

    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    private MembershipCardMapper membershipCardMapper;

    @Autowired
    private MemberEventMapper memberEventMapper;

    @Autowired
    private AppUserMapper appUserMapper;

    @Autowired
    private StoreMemberChargeMapper storeMemberChargeMapper;

    @Autowired
    private StoreMemberEventMapper storeMemberEventMapper;

    @Autowired
    private MerchantMapper merchantMapper;

    @Autowired
    private StoreTurnoverMapper storeTurnoverMapper;

    @Autowired
    private OrderHelper orderHelper;

    @Autowired
    private MemberCardMapper memberCardMapper;

    @Autowired
    private ElCouponService elCouponService;


    /**
     * 获取用户会员卡列表
     *
     * @param page
     * @param cardNo
     * @param phone  用户电话
     * @return
     */
    @Override
    public Response getUserCardList(PageQuery page, String cardNo, String phone, Byte type, String title, String storeName, Integer isExport) {
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        Integer adminId = null;
        if (webUserInfo.getRoleId() == 3) {
            adminId = webUserInfo.getSysAdmin().getId();
        }
        UserCardVos userCardVos = userMemberCardMapper.selectBySearchs1(cardNo, phone, adminId, type, title, storeName);
        if (userCardVos == null) {
            userCardVos = new UserCardVos();
        }
        if (isExport == null) {
            PageHelper.startPage(page.getPageNum(), page.getPageSize());
        }
        List<UserCardVo> list = userMemberCardMapper.selectBySearch(cardNo, phone, adminId, type, title, storeName);
        PageInfo pageInfo = new PageInfo<>(list);
        userCardVos.setUserCardVos(list);
        return ResponseFactory.page(userCardVos, pageInfo.getTotal(), pageInfo.getPages(),
                pageInfo.getPageNum(), pageInfo.getPageSize());
    }


    /**
     * 用户会员卡充值记录
     *
     * @param userId 用户id
     * @return
     */
    @Override
    public Response cardInfo(Integer userId, Integer cardId) {
        RecordVo record = new RecordVo();
        List<ChargeVo> chargeVos = memberChargeRecordMapper.selectByUserIdCardId(userId, cardId);
        List<ExpenseVo> expenseVos = memberExpenseRecordMapper.selectByUserIdCardId(userId, cardId);
        record.setCharges(chargeVos);
        record.setExpenses(expenseVos);
        return ResponseFactory.sucData(record);
    }


    /**
     * 分店获取用户会员卡列表（商家分店使用）
     *
     * @param page
     * @param cardNo 卡号
     * @param phone  用户电话
     * @return
     */
    @Override
    public Response getUserCardList1(PageQuery page, String cardNo, String phone, String title, String storeName, Integer isExport) {
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
//        分店adminId
        Integer adminId = webUserInfo.getSysAdmin().getId();
        // 创建者adminId(商家adminId)
        Integer createdAdminId = webUserInfo.getSysAdmin().getCreateAdminId();
        // 查询门店id
        Store store = storeMapper.selectByAdminId(adminId);
        if (store == null) {
            return ResponseFactory.suc();
        }
        List<Integer> list = new ArrayList<>();
        List<MembershipCard> cardList = membershipCardMapper.selectByAdminId(createdAdminId);
        if (!CollectionUtils.isEmpty(cardList)) {
            for (MembershipCard card : cardList) {
                String[] strings = card.getStoreIds().split(",");
                for (String string : strings) {
                    if (string.equals(store.getId().toString())) {
                        list.add(card.getId());
                    }
                }
            }
        }
        if (list.size() == 0) {
            return ResponseFactory.suc();
        }
        UserCardVos userCardVos = userMemberCardMapper.selectBySearchs(cardNo, phone, list, title, storeName);
        if (userCardVos == null) {
            userCardVos = new UserCardVos();
        }
        if (isExport == null) {
            PageHelper.startPage(page.getPageNum(), page.getPageSize());
        }
        List<UserCardVo> list1 = userMemberCardMapper.selectBySearch1(cardNo, phone, list, title, storeName);
        PageInfo pageInfo = new PageInfo<>(list1);
        userCardVos.setUserCardVos(list1);
        return ResponseFactory.page(userCardVos, pageInfo.getTotal(), pageInfo.getPages(),
                pageInfo.getPageNum(), pageInfo.getPageSize());

    }


    /**
     * 分店开卡
     *
     * @param card
     * @return
     */
    @Override
    public Response addUserCard(UserMemberCard card) throws IOException {
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        Integer adminId = webUserInfo.getSysAdmin().getId();
        Store store = storeMapper.selectByAdminId(adminId);
        AppUser appUser = appUserMapper.selectByPhone1(card.getPhone());
        if (appUser == null) {
            return ResponseFactory.err("需要先注册礼遇圈");
        }
        UserMemberCard userMemberCard = userMemberCardMapper.selectByUseridcardId(appUser.getId(), card.getMembershipCardId());
        if (userMemberCard != null) {
            return ResponseFactory.err("已经开过卡了，不能重复开卡");
        }
        UserMemberCard ca = new UserMemberCard();
        ca.setMembershipCardId(card.getMembershipCardId());
        ca.setUserId(appUser.getId());
        ca.setBalance(new BigDecimal("0"));
        ca.setTotalAmount(new BigDecimal("0"));
        ca.setConsumeAmount(new BigDecimal("0"));
        ca.setStatus((byte) 1);
        ca.setStoreId(store.getId());
        ca.setPhone(card.getPhone());
        ca.setAdminId(adminId);
        ca.setCardNo(orderHelper.genOrderNo(7, 9));
        String phone = card.getPhone();
        if (StringUtils.isEmpty(card.getPassword())) {
            ca.setPassword(Utils.toMD5(phone + Utils.toMD5(phone.substring(phone.length() - 6))));
        } else {
            ca.setPassword(Utils.toMD5(phone + card.getPassword()));
        }
        int insert = userMemberCardMapper.insert(ca);
        if (insert < 1) {
            return ResponseFactory.err("开卡失败");
        }
        // 给用户发送短信
        String content = "会员卡";
        MembershipCard membershipCard = membershipCardMapper.selectByPrimaryKey(card.getMembershipCardId());
        if (membershipCard != null) {
            content = membershipCard.getTitle();
        }
        SmsSendResult smsSendResult = SendUtil.smsSend(card.getPhone(), "【礼遇圈】尊敬的用户，您已成功开通" + content + "，如有问题请咨询客服人员。");
        if (smsSendResult.getCode() != 0) {
            return ResponseFactory.err(smsSendResult.getMsg());
        }
        return ResponseFactory.sucMsg("开卡成功");
    }


    /**
     * 分店会员卡充值
     *
     * @param userId   用户id
     * @param cardId   会员卡id
     * @param recharge 充值金额
     * @param explain  充值说明
     * @param send     赠送金额
     * @param eventId  活动id
     * @return
     */
    @Override
    public Response chargeCard(Integer userId, String phone, Integer cardId, BigDecimal recharge,
                               String explain, BigDecimal send, Integer eventId, String image) throws IOException {
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        Integer adminId = webUserInfo.getSysAdmin().getId();
        Store store = storeMapper.selectByAdminId(adminId);
        Integer storeId = null;
        String storeName = "门店";
        if (store != null && store.getId() != null) {
            storeId = store.getId();
            storeName = store.getName();
        }
        if (send == null) {
            send = new BigDecimal("0");
        }
        // 如果用户id为空
        if (userId == null && !StringUtils.isEmpty(phone)) {
            AppUser appUser = appUserMapper.selectByPhone1(phone);
            if (appUser == null) {
                return ResponseFactory.err("号码不对，没有查到该用户");
            }
            userId = appUser.getId();
        }
        // 判断活动
        if (eventId != null) {
            MemberEvent memberEvent = memberEventMapper.selectByPrimaryKey(eventId);
            if (memberEvent == null) {
                throw new ServiceException(ErrorCode.ERROR.getCode(), "该活动不存在，请选择其他活动");
            }
            if (memberEvent.getRechargeMoney().compareTo(recharge) != 0 || memberEvent.getSendMoney().compareTo(send) != 0) {
                throw new ServiceException(ErrorCode.ERROR.getCode(), "充值金额或赠送金额与活动金额不符");
            }
            // 判断该卡是否有此类活动
            MemberCard cardEvent = memberCardMapper.selectEventIdByCardId(cardId, eventId);
            if (cardEvent == null) {
                throw new ServiceException(ErrorCode.ERROR.getCode(), "该会员卡没用此类活动");
            }
            // 给用户添加优惠券
            if ((memberEvent.getType() == 2 || memberEvent.getType() == 5) && memberEvent.getTargetId() != null) {
                elCouponService.addCoupon(memberEvent.getTargetId(), memberEvent.getQuantity(),
                        store.getId(), userId, adminId);
            }
        }
        Merchant merchant = merchantMapper.selectByAdminId(webUserInfo.getSysAdmin().getCreateAdminId());
        // 更新余额
        UserMemberCard card = updateBalance(userId, cardId, (byte) 1, recharge, send);
        // 添加充值记录
        BigDecimal total = BigDecimalUtil.add(recharge.doubleValue(), send.doubleValue());
        MemberChargeRecord record = new MemberChargeRecord();
        record.setMembershipCardId(cardId);
        record.setUserId(userId);
        record.setMemberEventId(eventId);
        record.setRechargeMoney(recharge);
        record.setSendMoney(send);
        record.setType((byte) 2);
        record.setStoreId(storeId);
        record.setAdminId(adminId);
        if (StringUtils.isEmpty(explain)) {
            record.setExplain("余额充值");
        } else {
            record.setExplain(explain);
        }
        record.setOrderNo(orderHelper.genOrderNo(7, 10));
        record.setBeforeMoney(BigDecimalUtil.sub(card.getBalance().doubleValue(), total.doubleValue()));
        record.setImage(image);
        int insert = memberChargeRecordMapper.insert(record);
        if (insert < 1) {
            return ResponseFactory.err("充值失败");
        }
        String content = "会员卡";
        // 判断是否是活动卡
        // 添加详细记录
        MembershipCard membershipCard = membershipCardMapper.selectByPrimaryKey(cardId);
        if (membershipCard != null) {
            if (membershipCard.getType() == 11) {
                // 活动卡
                if (eventId == null) {
                    return ResponseFactory.err("活动卡充值必须选择活动");
                }
                StoreMemberEvent ev = new StoreMemberEvent();
                ev.setUserId(userId);
                ev.setMembershipCardId(cardId);
                ev.setStoreId(storeId);
                ev.setMemberEventId(eventId);
                ev.setOrderNo(record.getOrderNo());
                ev.setCapitalMoney(recharge);
                ev.setSendMoney(send);
                ev.setTotalMoney(total);
                ev.setExplain(explain);
                ev.setType((byte) 1);
                ev.setCapitalBalance(recharge);
                ev.setSendBalance(send);
                ev.setCapitalStatus((byte) 1);
                ev.setSendStatus((byte) 1);
                ev.setMerchantId(merchant.getId());
                detailChargeEvent(ev);
            } else {
                // 普通储值卡
                Float scale = null;
                scale = BigDecimalUtil.div(send.doubleValue(), total.doubleValue()).floatValue();
                detailCharge(userId, merchant.getId(), storeId, recharge, cardId, send, new BigDecimal("0"), (byte) 1, explain,
                        total, scale, total, (byte) 1, eventId, record.getOrderNo());
            }
            content = membershipCard.getTitle();
        }
        // 给用户发送短信
        String time = time();
        SmsSendResult smsSendResult = SendUtil.smsSend(card.getPhone(), "【礼遇圈】尊敬的用户，您的会员卡" + content + "在" + storeName + "成功充值" + recharge + "元，" +
                "赠送" + send + "元，充值时间为" + time + "。如有问题请咨询客服人员。");
        if (smsSendResult.getCode() != 0) {
            return ResponseFactory.err(smsSendResult.getMsg());
        }
        return ResponseFactory.sucMsg("充值成功");
    }

    private String time() {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(now);//日期
    }

//    public static void main(String[] args) throws IOException, ParseException {
//
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//        Date parse = dateFormat.parse("2020-01-03T06:28:08.000+0000");  //时间戳
//        String format = dateFormat.format(parse);//日期
//        System.out.println(format);
//    }

    /**
     * 分店消费（线下消费）
     *
     * @param userId  用户id
     * @param cardId  会员卡id
     * @param expense 消费金额
     * @param explain 消费说明
     * @return
     */
    @Override
    public Response expenseCard(Integer userId, String phone, Integer cardId, BigDecimal expense, String explain, String password) throws IOException {
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        Integer adminId = webUserInfo.getSysAdmin().getId();
        Store store = storeMapper.selectByAdminId(adminId);
        Integer storeId = null;
        String storeName = "门店";
        if (store != null && store.getId() != null) {
            storeId = store.getId();
            storeName = store.getName();
        }
        Merchant merchant = merchantMapper.selectByAdminId(webUserInfo.getSysAdmin().getCreateAdminId());
        // 如果用户id为空
        if (userId == null && !StringUtils.isEmpty(phone)) {
            AppUser appUser = appUserMapper.selectByPhone1(phone);
            if (appUser == null) {
                throw new ServiceException(ErrorCode.ERROR.getCode(), "号码不对，没有查到该用户");
            }
            userId = appUser.getId();
        }
        // 校验密码
        UserMemberCard card1 = userMemberCardMapper.selectByUseridcardId(userId, cardId);
        if (card1 == null) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "该用户会员卡不存在");
        }
        if (!card1.getPassword().equals(Utils.toMD5(card1.getPhone() + password))) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "密码错误");
        }
        // 更新余额
        UserMemberCard card = updateBalance(userId, cardId, (byte) 2, expense, new BigDecimal("0"));
        // 添加消费记录
        MemberExpenseRecord re = new MemberExpenseRecord();
        re.setMembershipCardId(cardId);
        re.setUserId(userId);
        re.setExpenseMoney(expense);
        re.setType((byte) 2);
        re.setStoreId(storeId);
        re.setAdminId(adminId);
        if (StringUtils.isEmpty(explain)) {
            re.setExplain("线下消费");
        } else {
            re.setExplain(explain);
        }
        re.setOrderNo(orderHelper.genOrderNo(7, 11));
        re.setBeforeMoney(BigDecimalUtil.add(card.getBalance().doubleValue(), expense.doubleValue()));
        int insert = memberExpenseRecordMapper.insert(re);
        if (insert < 1) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "失败");
        }
        String content = "会员卡";
        // 判断是否是活动卡
        // 添加详细记录
        MembershipCard membershipCard = membershipCardMapper.selectByPrimaryKey(cardId);
        if (membershipCard != null) {
            if (membershipCard.getType() == 11) {
                // 查询该活动卡的活动
                MemberEvent events = memberCardMapper.selectEventByCardId(cardId);
                if (events == null || events.getScale() == null) {
                    throw new ServiceException(ErrorCode.ERROR.getCode(), "活动卡消费失败");
                }
                BigDecimal send = BigDecimalUtil.multi(expense.doubleValue(), events.getScale());
                BigDecimal capital = BigDecimalUtil.sub(expense.doubleValue(), send.doubleValue());
                // 添加详细记录
                StoreMemberEvent ev = new StoreMemberEvent();
                ev.setUserId(userId);
                ev.setMembershipCardId(cardId);
                ev.setStoreId(storeId);
                ev.setOrderNo(re.getOrderNo());
                ev.setCapitalMoney(capital);
                ev.setSendMoney(send);
                ev.setTotalMoney(expense);
                ev.setExplain(explain);
                ev.setType((byte) 2);
                ev.setCapitalBalance(new BigDecimal("0"));
                ev.setSendBalance(new BigDecimal("0"));
                ev.setCapitalStatus((byte) 4);
                ev.setSendStatus((byte) 4);
                ev.setMerchantId(merchant.getId());
                int storeMemberId = detailChargeEvent(ev);
                // 计算营业额
                turnoverEventCard(userId, cardId, storeMemberId, capital, storeId, send);
            } else {
                // 添加详细记录
                int storeMemberId = detailCharge(userId, merchant.getId(), storeId, new BigDecimal("0"), cardId, new BigDecimal("0"), expense, (byte) 2, explain, expense,
                        0f, new BigDecimal("0"), (byte) 4, null, re.getOrderNo());
                // 计算营业额
                turnover(userId, cardId, storeMemberId, expense, storeId);
            }
            content = membershipCard.getTitle();
        }
        // 给用户发送短信
        String time = time();
        SmsSendResult smsSendResult = SendUtil.smsSend(card.getPhone(), "【礼遇圈】尊敬的用户，您的" + content + "在" + storeName + "成功消费" + expense
                + "元，消费时间为" + time + "。如有问题请咨询客服人员。");
        if (smsSendResult.getCode() != 0) {
            return ResponseFactory.err(smsSendResult.getMsg());
        }
        return ResponseFactory.sucMsg("成功");
    }


    /**
     * 普通储蓄卡计算营业额
     */
    private void turnover(Integer userId, Integer cardId, Integer storeMemberId, BigDecimal expense, Integer storeId) {
        //取出之前充值和别人转赠的记录
        List<StoreMemberCharge> charges = storeMemberChargeMapper.selectByUserIdCardId(userId, cardId);
        if (!CollectionUtils.isEmpty(charges)) {
            BigDecimal expense1 = expense;
            for (StoreMemberCharge charge : charges) {
                //判断此次充值还剩余的余额
                if (charge.getBalance().compareTo(expense1) == 0) {
                    // 余额与消费金额相等,更新详细记录，添加消费营业额记录
                    BigDecimal sub = BigDecimalUtil.sub(charge.getBalance().doubleValue(), expense1.doubleValue());
                    updateDetailCharge(charge.getId(), sub, (byte) 3);
                    addStoreTurnover(storeMemberId, expense1, charge.getScale(), storeId, charge.getStoreId(), charge.getMemberEventId(), charge.getId());
                    break;
                } else if (charge.getBalance().compareTo(expense1) > 0) {
                    //余额大于消费金额，更新详细记录，添加消费营业额记录
                    BigDecimal sub = BigDecimalUtil.sub(charge.getBalance().doubleValue(), expense1.doubleValue());
                    updateDetailCharge(charge.getId(), sub, (byte) 2);
                    addStoreTurnover(storeMemberId, expense1, charge.getScale(), storeId, charge.getStoreId(), charge.getMemberEventId(), charge.getId());
                    break;
                } else {
                    //余额小于消费金额
                    //扣除第一次充值的余额后还不够的钱
                    BigDecimal sub = BigDecimalUtil.sub(expense1.doubleValue(), charge.getBalance().doubleValue());
                    updateDetailCharge(charge.getId(), new BigDecimal("0"), (byte) 3);
                    addStoreTurnover(storeMemberId, charge.getBalance(), charge.getScale(), storeId, charge.getStoreId(), charge.getMemberEventId(), charge.getId());
                    expense1 = sub;
                }
            }
        }
    }

    /**
     * 活动卡计算营业额
     */
    private void turnoverEventCard(Integer userId, Integer cardId, Integer storeMemberId, BigDecimal capital,
                                   Integer storeId, BigDecimal send) {
        //取出之前充值的本金记录
        List<StoreMemberEvent> events = storeMemberEventMapper.selectByUserIdCardId(userId, cardId);
        if (CollectionUtils.isEmpty(events)) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "充值余额不足!");
        }
        BigDecimal capital1 = capital;
//            BigDecimal send1 = send;
        BigDecimal balance = new BigDecimal("0");
        for (StoreMemberEvent event : events) {
            // 判断充值的余额是否足够
            balance = BigDecimalUtil.add(balance.doubleValue(), event.getCapitalBalance().doubleValue());
        }
        if (balance.compareTo(capital) < 0) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "充值余额不足");
        } else {
            for (StoreMemberEvent event : events) {
                //判断此次充值还剩余的本金
                if (event.getCapitalBalance().compareTo(capital1) == 0) {
                    updateDetailEvent(event.getId(), new BigDecimal("0"), (byte) 3, event.getSendBalance(), event.getSendStatus());
                    addStoreTurnoverEvent(storeMemberId, capital1, new BigDecimal("0"), storeId, event.getStoreId(), event.getMemberEventId(), event.getId());
                    break;
                } else if (event.getCapitalBalance().compareTo(capital1) > 0) {
                    //本金余额大于消费本金,
                    // 更新详细记录，添加消费营业额记录
                    BigDecimal ca = BigDecimalUtil.sub(event.getCapitalBalance().doubleValue(), capital1.doubleValue());
                    updateDetailEvent(event.getId(), ca, (byte) 2, event.getSendBalance(), event.getSendStatus());
                    addStoreTurnoverEvent(storeMemberId, capital1, new BigDecimal("0"), storeId, event.getStoreId(), event.getMemberEventId(), event.getId());
                    break;

                } else {
                    //本金余额小于消费本金,
                    // 更新详细记录，添加消费营业额记录
                    //扣除第一次充值的余额后还不够的
                    BigDecimal ca = BigDecimalUtil.sub(capital1.doubleValue(), event.getCapitalBalance().doubleValue());
                    updateDetailEvent(event.getId(), new BigDecimal("0"), (byte) 3, event.getSendBalance(), event.getSendStatus());
                    addStoreTurnoverEvent(storeMemberId, event.getCapitalBalance(), new BigDecimal("0"), storeId, event.getStoreId(), event.getMemberEventId(), event.getId());
                    capital1 = ca;
                }
            }
        }

        // 取出之前充值的赠送记录
        List<StoreMemberEvent> sends = storeMemberEventMapper.selectByUserIdCardId1(userId, cardId);
        if (!CollectionUtils.isEmpty(sends)) {
            BigDecimal send1 = send;
            for (StoreMemberEvent store : sends) {
                if (store.getSendBalance().compareTo(send1) == 0) {
                    // 赠送金额等于赠送金额剩余
                    // 更新详细记录，添加消费营业额记录
                    updateDetailEvent(store.getId(), store.getCapitalBalance(), store.getCapitalStatus(), new BigDecimal("0"), (byte) 3);
                    addStoreTurnoverEvent(storeMemberId, new BigDecimal("0"), send1, storeId, store.getStoreId(), store.getMemberEventId(), store.getId());
                    break;
                } else if (store.getSendBalance().compareTo(send1) > 0) {
                    // 赠送金额大于赠送金额剩余
                    // 更新详细记录，添加消费营业额记录
                    BigDecimal se = BigDecimalUtil.sub(store.getSendBalance().doubleValue(), send1.doubleValue());
                    updateDetailEvent(store.getId(), store.getCapitalBalance(), store.getCapitalStatus(), se, (byte) 2);
                    addStoreTurnoverEvent(storeMemberId, new BigDecimal("0"), send1, storeId, store.getStoreId(), store.getMemberEventId(), store.getId());
                    break;
                } else {
                    // 赠送金额小于赠送金额剩余
                    // 更新详细记录，添加消费营业额记录
                    //扣除第一次充值的余额后还不够的钱
                    BigDecimal se = BigDecimalUtil.sub(send1.doubleValue(), store.getSendBalance().doubleValue());
                    updateDetailEvent(store.getId(), store.getCapitalBalance(), store.getCapitalStatus(), new BigDecimal("0"), (byte) 3);
                    addStoreTurnoverEvent(storeMemberId, new BigDecimal("0"), store.getSendBalance(), storeId, store.getStoreId(), store.getMemberEventId(), store.getId());
                    send1 = se;
                }
            }
        }
    }


    /**
     * 更新余额
     *
     * @return
     */
    private UserMemberCard updateBalance(Integer userId, Integer cardId, Byte type, BigDecimal balance, BigDecimal send) {
        UserMemberCard card = userMemberCardMapper.selectByUseridcardId(userId, cardId);
        if (card == null) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "该用户会员卡不存在");
        }
        if (send == null) {
            send = new BigDecimal("0");
        }
        if (type == 1) {
            // 充值
            BigDecimal total = BigDecimalUtil.add(balance.doubleValue(), send.doubleValue());
            card.setBalance(BigDecimalUtil.add(card.getBalance().doubleValue(), total.doubleValue()));
            card.setTotalAmount(BigDecimalUtil.add(card.getTotalAmount().doubleValue(), total.doubleValue()));
        } else {
            // 消费
            if (card.getBalance().compareTo(balance) < 0) {
                throw new ServiceException(ErrorCode.ERROR.getCode(), "余额不足");
            }
            card.setBalance(BigDecimalUtil.sub(card.getBalance().doubleValue(), balance.doubleValue()));
            card.setConsumeAmount(BigDecimalUtil.add(card.getConsumeAmount().doubleValue(), balance.doubleValue()));
        }
        int i = userMemberCardMapper.updateByPrimaryKeySelective(card);
        if (i < 1) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "余额更新失败");
        }
        return card;
    }

    /**
     * 添加详细记录（普通储值卡）
     *
     * @return
     */
    private int detailCharge(Integer userId, Integer merchant, Integer storeId, BigDecimal re, Integer cardId, BigDecimal send, BigDecimal ex,
                             Byte type, String explain, BigDecimal total, Float scale, BigDecimal balance, Byte status, Integer eventId, Long orderNo) {
        StoreMemberCharge store = new StoreMemberCharge();
        store.setUserId(userId);
        store.setMerchantId(merchant);
        store.setStoreId(storeId);
        store.setRechargeMoney(re);
        store.setSendMoney(send);
        store.setExpenseMoney(ex);
        store.setTotalMoney(total);
        store.setType(type);
        store.setExplain(explain);
        store.setScale(scale);
        store.setMembershipCardId(cardId);
        store.setBalance(balance);
        store.setStatus(status);
        store.setMemberEventId(eventId);
        store.setOrderNo(orderNo);
        int insert = storeMemberChargeMapper.insert(store);
        if (insert < 1) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "失败");
        }
        return store.getId();
    }

    /**
     * 添加详细记录（活动卡）
     *
     * @return
     */
    private int detailChargeEvent(StoreMemberEvent event) {
        StoreMemberEvent ev = new StoreMemberEvent();
        ev.setUserId(event.getUserId());
        ev.setMembershipCardId(event.getMembershipCardId());
        ev.setStoreId(event.getStoreId());
        ev.setMemberEventId(event.getMemberEventId());
        ev.setOrderNo(event.getOrderNo());
        ev.setCapitalMoney(event.getCapitalMoney());
        ev.setSendMoney(event.getSendMoney());
        ev.setTotalMoney(event.getTotalMoney());
        ev.setExplain(event.getExplain());
        ev.setType(event.getType());
        ev.setCapitalBalance(event.getCapitalBalance());
        ev.setSendBalance(event.getSendBalance());
        ev.setCapitalStatus(event.getCapitalStatus());
        ev.setSendStatus(event.getSendStatus());
        ev.setMerchantId(event.getMerchantId());
        MemberEvent memberEvent = memberEventMapper.selectByPrimaryKey(event.getMemberEventId());
        if (memberEvent != null && memberEvent.getScale() != null) {
            ev.setScale(memberEvent.getScale());
        }
        int insert = storeMemberEventMapper.insert(ev);
        if (insert < 1) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "失败");
        }
        return ev.getId();
    }


    /**
     * 更新详细记录（普通储值卡）
     *
     * @param id      详细记录id
     * @param balance 余额
     * @param status  状态
     */
    private void updateDetailCharge(Integer id, BigDecimal balance, Byte status) {
        StoreMemberCharge store = storeMemberChargeMapper.selectByPrimaryKey(id);
        if (store == null) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "该记录不存在");
        }
        store.setBalance(balance);
        store.setStatus(status);
        int i = storeMemberChargeMapper.updateByPrimaryKeySelective(store);
        if (i < 1) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "失败");
        }
    }

    /**
     * 更新详细记录（活动卡）
     *
     * @param id 详细记录id
     */
    private void updateDetailEvent(Integer id, BigDecimal capitalBalance, Byte cStatus,
                                   BigDecimal sendBalance, Byte sendStatus) {
        StoreMemberEvent store = storeMemberEventMapper.selectByPrimaryKey(id);
        if (store == null) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "该记录不存在");
        }
        store.setCapitalBalance(capitalBalance);
        store.setCapitalStatus(cStatus);
        store.setSendBalance(sendBalance);
        store.setSendStatus(sendStatus);
        int i = storeMemberEventMapper.updateByPrimaryKeySelective(store);
        if (i < 1) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "失败");
        }
    }

    /**
     * 更新详细记录（普通储值卡）
     */
    private void addStoreTurnover(Integer storeMemberId, BigDecimal ex, Float scale, Integer storeId,
                                  Integer blagStoreId, Integer eventId, Integer storeChargeId) {
        //营业额

        BigDecimal multi = BigDecimalUtil.multi(ex.doubleValue(), scale);
        // 收入
        BigDecimal sub = BigDecimalUtil.sub(ex.doubleValue(), multi.doubleValue());
        StoreTurnover turnover = new StoreTurnover();
        turnover.setStoreMemberId(storeMemberId);
        turnover.setBlagMoney(sub);
        turnover.setTurnoverMoney(multi);
        turnover.setStoreId(storeId);
        turnover.setBlagStoreId(blagStoreId);
        if (eventId != null) {
            turnover.setEventId(eventId);
        }
        turnover.setStoreChargeId(storeChargeId);
        turnover.setType((byte) 1);
        int insert = storeTurnoverMapper.insert(turnover);
        if (insert < 1) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "失败");
        }
    }

    /**
     * 更新详细记录（活动卡）
     */
    private void addStoreTurnoverEvent(Integer storeMemberId, BigDecimal blag, BigDecimal turnoverMoney, Integer storeId,
                                       Integer blagStoreId, Integer eventId, Integer storeChargeId) {
        StoreTurnover turnover = new StoreTurnover();
        turnover.setStoreMemberId(storeMemberId);
        turnover.setBlagMoney(blag);
        turnover.setTurnoverMoney(turnoverMoney);
        turnover.setStoreId(storeId);
        turnover.setBlagStoreId(blagStoreId);
        if (eventId != null) {
            turnover.setEventId(eventId);
        }
        turnover.setStoreChargeId(storeChargeId);
        turnover.setType((byte) 2);
        int insert = storeTurnoverMapper.insert(turnover);
        if (insert < 1) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "失败");
        }
    }

    /**
     * 修改用户的会员卡等级
     *
     * @param userId  用户id
     * @param cardId  会员卡id
     * @param gradeId 会员卡等级id
     * @return
     */
    @Override
    public Response updateCardGrade(Integer userId, Integer cardId, Integer gradeId) {
        UserMemberCard userMemberCard = userMemberCardMapper.selectByUseridcardId(userId, cardId);
        if (userMemberCard == null) {
            return ResponseFactory.err("会员信息不存在");
        }
        userMemberCard.setGradeId(gradeId);
        int i = userMemberCardMapper.updateByPrimaryKeySelective(userMemberCard);
        if (i < 1) {
            return ResponseFactory.err("失败");
        }
        return ResponseFactory.sucMsg("成功");
    }


    /**
     * 查询活动卡充值赠送金额的余额
     *
     * @param userId 用户id
     * @param cardId 会员卡id
     * @return
     */
    @Override
    public Response getEventCardDetail(Integer userId, Integer cardId) {
        BigDecimal capital1 = new BigDecimal("0");
        BigDecimal send1 = new BigDecimal("0");
        byte caStatus = 1;
        byte seStatus = 1;
        List<StoreMemberEvent> capitals = storeMemberEventMapper.selectByUserIdCardId(userId, cardId);
        if (!CollectionUtils.isEmpty(capitals)) {
            for (StoreMemberEvent capital : capitals) {
                capital1 = BigDecimalUtil.add(capital1.doubleValue(), capital.getCapitalBalance().doubleValue());
            }
        }
        List<StoreMemberEvent> sends = storeMemberEventMapper.selectByUserIdCardId1(userId, cardId);
        if (!CollectionUtils.isEmpty(sends)) {
            for (StoreMemberEvent send : sends) {
                send1 = BigDecimalUtil.add(send1.doubleValue(), send.getSendBalance().doubleValue());
            }
        }
        if (capital1.compareTo(new BigDecimal("0")) == 0) {
            caStatus = 2;
        }
        if (send1.compareTo(new BigDecimal("0")) == 0) {
            seStatus = 2;
        }
        List<StoreMemberEvent> list = storeMemberEventMapper.selectByUserIdCardIds(userId, cardId);
        Map<String, Object> map = new HashMap<>();
        map.put("capital1", capital1);
        map.put("send1", send1);
        map.put("caStatus", caStatus);
        map.put("seStatus", seStatus);
        map.put("list", list);
        return ResponseFactory.sucData(map);
    }

    /**
     * 查询所有会员卡充值赠送金额的余额
     *
     * @param userId 用户id
     * @param cardId 会员卡id
     * @return
     */
    @Override
    public Response getCardDetail(Integer userId, Integer cardId) {
        BigDecimal capital1 = new BigDecimal("0");
        BigDecimal send1 = new BigDecimal("0");
        // 查询会员卡
        MembershipCard membershipCard = membershipCardMapper.selectByPrimaryKey(cardId);
        if (membershipCard == null) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "该会员卡不存在");
        }
        if (membershipCard.getType() == 1) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "礼遇圈卡不能退卡");
        } else if (membershipCard.getType() == 11) {
            //活动卡
            Response response = getEventCardDetail(userId, cardId);
            if (response.getData() != null) {
                capital1 = (BigDecimal) ((Map) response.getData()).get("capital1");
                send1 = (BigDecimal) ((Map) response.getData()).get("send1");
            }
        } else {
            // 普通储值卡
            List<StoreMemberCharge> charges = storeMemberChargeMapper.selectByUserIdCardId(userId, cardId);
            if (!CollectionUtils.isEmpty(charges)) {
                for (StoreMemberCharge charge : charges) {
                    BigDecimal multi = BigDecimalUtil.multi(charge.getBalance().doubleValue(), charge.getScale());
                    BigDecimal sub = BigDecimalUtil.sub(charge.getBalance().doubleValue(), multi.doubleValue());
                    send1 = BigDecimalUtil.add(send1.doubleValue(), multi.doubleValue());
                    capital1 = BigDecimalUtil.add(capital1.doubleValue(), sub.doubleValue());
                }
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("capital1", capital1);
        map.put("send1", send1);
        return ResponseFactory.sucData(map);
    }


    /**
     * 退卡
     *
     * @param userId 用户id
     * @param cardId 会员卡id
     * @return
     */
    @Override
    public Response backCard(Integer userId, Integer cardId, BigDecimal capital, BigDecimal send) {
        UserMemberCard user = userMemberCardMapper.selectByUseridcardId(userId, cardId);
        if (user == null) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "该用户会员卡不存在");
        }
        BigDecimal add = BigDecimalUtil.add(capital.doubleValue(), send.doubleValue());
        if (user.getBalance().compareTo(add) != 0) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "退款金额不正确");
        }
        // 删除充值记录
        List<ChargeVo> chargeVos = memberChargeRecordMapper.selectByUserIdCardId(userId, cardId);
        if (!CollectionUtils.isEmpty(chargeVos)) {
            for (ChargeVo chargeVo : chargeVos) {
                memberChargeRecordMapper.selectByKey(chargeVo.getId());
                memberChargeRecordMapper.deleteByPrimaryKey(chargeVo.getId());
            }
        }
        // 删除消费记录
        List<ExpenseVo> expenseVos = memberExpenseRecordMapper.selectByUserIdCardId(userId, cardId);
        if (!CollectionUtils.isEmpty(expenseVos)){
            for (ExpenseVo expenseVo : expenseVos) {
                memberExpenseRecordMapper.selectByKey(expenseVo.getId());
                memberExpenseRecordMapper.deleteByPrimaryKey(expenseVo.getId());
            }
        }
        user.setBalance(new BigDecimal("0"));
        user.setStatus((byte) -1);
        int i = userMemberCardMapper.updateByPrimaryKeySelective(user);
        if (i < 1) {
            return ResponseFactory.err("退卡失败");
        }
        return ResponseFactory.sucMsg("退卡成功");
    }

    /**
     * 改变查询活动卡充值赠送金额状态
     *
     * @param storeMemberEventId 用户id
     * @return
     */
    @Override
    public Response getEventCardStatus(Integer storeMemberEventId) {
        StoreMemberEvent storeMemberEvent = storeMemberEventMapper.selectByPrimaryKey(storeMemberEventId);
        if (storeMemberEvent != null && storeMemberEvent.getSendStatus() == 3) {
            // 已返现
            storeMemberEvent.setSendStatus((byte) 5);
            int i = storeMemberEventMapper.updateByPrimaryKeySelective(storeMemberEvent);
            if (i == 1) {
                return ResponseFactory.suc();
            }
        }
        return ResponseFactory.err("");
    }


    /******************************************************小程序管理端*****************************************************/


    /**
     * 分店获取用户会员卡列表(小程序端)
     *
     * @param page
     * @param phone 用户电话
     * @return
     */
    @Override
    public Response getUserCardListManage(PageQuery page, String phone) {
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
//        分店adminId
        Integer adminId = webUserInfo.getSysAdmin().getId();
        // 创建者adminId(商家adminId)
        Integer createdAdminId = webUserInfo.getSysAdmin().getCreateAdminId();
        // 查询门店id
        Store store = storeMapper.selectByAdminId(adminId);
        if (store == null) {
            return ResponseFactory.suc();
        }
        List<Integer> list = new ArrayList<>();
        List<MembershipCard> cardList = membershipCardMapper.selectByAdminId(createdAdminId);
        if (!CollectionUtils.isEmpty(cardList)) {
            for (MembershipCard card : cardList) {
                String[] strings = card.getStoreIds().split(",");
                for (String string : strings) {
                    if (string.equals(store.getId().toString())) {
                        list.add(card.getId());
                    }
                }
            }
        }
        if (list.size() == 0) {
            return ResponseFactory.suc();
        }
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<UserCardVo1> list1 = userMemberCardMapper.selectUserCard(phone, list);
        if (!CollectionUtils.isEmpty(list1)) {
            for (UserCardVo1 vo : list1) {
                if (!StringUtils.isEmpty(vo.getAvatar()) && !vo.getAvatar().startsWith("http")) {
                    vo.setAvatar("https://liyuquan.cn/static" + vo.getAvatar());
                }
            }
        }
        PageInfo pageInfo = new PageInfo<>(list1);
        return ResponseFactory.page(list1, pageInfo.getTotal(), pageInfo.getPages(),
                pageInfo.getPageNum(), pageInfo.getPageSize());
    }

    /**
     * 小程序管理端会员详情
     *
     * @return
     */
    @Override
    public Response userCardDetail(Integer userId) {
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
//        分店adminId
        Integer adminId = webUserInfo.getSysAdmin().getId();
        // 创建者adminId(商家adminId)
        Integer createdAdminId = webUserInfo.getSysAdmin().getCreateAdminId();
        // 查询门店id
        Store store = storeMapper.selectByAdminId(adminId);
        if (store == null) {
            return ResponseFactory.suc();
        }
        List<Integer> list = new ArrayList<>();
        List<MembershipCard> cardList = membershipCardMapper.selectByAdminId(createdAdminId);
        if (!CollectionUtils.isEmpty(cardList)) {
            for (MembershipCard card : cardList) {
                String[] strings = card.getStoreIds().split(",");
                for (String string : strings) {
                    if (string.equals(store.getId().toString())) {
                        list.add(card.getId());
                    }
                }
            }
        }
        if (list.size() == 0) {
            return ResponseFactory.suc();
        }
        List<UserCardVo2> userCard = userMemberCardMapper.selectByUserId(userId, list);
        if (!CollectionUtils.isEmpty(userCard)) {
            for (UserCardVo2 vo : userCard) {
                if (!vo.getAvatar().startsWith("http")) {
                    vo.setAvatar("https://liyuquan.cn/static" + vo.getAvatar());
                }
                if (vo.getType() == 11) {
                    List<EventCardVo> list1 = storeMemberEventMapper.selectAll(userId, vo.getCardId());
                    vo.setEventCards(list1);
                    Response response = getEventCardDetail(userId, vo.getCardId());
                    if (response.getErrCode() == 0) {
                        Map data = (Map) response.getData();
                        vo.setCapital(new BigDecimal(data.get("capital1").toString()));
                        vo.setSend(new BigDecimal(data.get("send1").toString()));
                    }
                }
            }
        }
        return ResponseFactory.sucData(userCard);
    }


}
