package com.chouchong.service.v3.impl;

import com.chouchong.common.*;
import com.chouchong.dao.iwant.appUser.AppUserMapper;
import com.chouchong.dao.iwant.merchant.MerchantMapper;
import com.chouchong.dao.v3.*;
import com.chouchong.entity.iwant.appUser.AppUser;
import com.chouchong.entity.iwant.merchant.Merchant;
import com.chouchong.entity.v3.*;
import com.chouchong.exception.ServiceException;
import com.chouchong.service.v3.UserCardService;
import com.chouchong.service.v3.vo.ChargeVo;
import com.chouchong.service.v3.vo.ExpenseVo;
import com.chouchong.service.v3.vo.RecordVo;
import com.chouchong.service.v3.vo.UserCardVo;
import com.chouchong.service.webUser.vo.WebUserInfo;
import com.chouchong.utils.BigDecimalUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
    private AppUserMapper appUserMapper;

    @Autowired
    private StoreMemberChargeMapper storeMemberChargeMapper;

    @Autowired
    private MerchantMapper merchantMapper;

    @Autowired
    private StoreTurnoverMapper storeTurnoverMapper;

    @Autowired
    private OrderHelper orderHelper;


    /**
     * 获取用户会员卡列表
     *
     * @param page
     * @param cardNo
     * @param phone  用户电话
     * @return
     */
    @Override
    public Response getUserCardList(PageQuery page, String cardNo, String phone, Byte type,String title) {
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        Integer adminId = null;
        if (webUserInfo.getRoleId() == 3) {
            adminId = webUserInfo.getSysAdmin().getId();
        }
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<UserCardVo> list = userMemberCardMapper.selectBySearch(cardNo, phone, adminId, type,title);
        PageInfo pageInfo = new PageInfo<>(list);
        return ResponseFactory.page(list, pageInfo.getTotal(), pageInfo.getPages(),
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
    public Response getUserCardList1(PageQuery page, String cardNo, String phone,String title) {
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
        List<UserCardVo> list1 = userMemberCardMapper.selectBySearch1(cardNo, phone, list,title);
        PageInfo pageInfo = new PageInfo<>(list1);
        return ResponseFactory.page(list1, pageInfo.getTotal(), pageInfo.getPages(),
                pageInfo.getPageNum(), pageInfo.getPageSize());

    }


    /**
     * 分店开卡
     *
     * @param card
     * @return
     */
    @Override
    public Response addUserCard(UserMemberCard card) {
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
        int insert = userMemberCardMapper.insert(ca);
        if (insert < 1) {
            return ResponseFactory.err("开卡失败");
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
                               String explain, BigDecimal send, Integer eventId) {
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        Integer adminId = webUserInfo.getSysAdmin().getId();
        Store store = storeMapper.selectByAdminId(adminId);
        Integer storeId = null;
        if (store != null && store.getId() != null) {
            storeId = store.getId();
        }
        if (send == null) {
            send = new BigDecimal("0");
        }
        // 如果用户id为空
        if(userId == null && !StringUtils.isEmpty(phone)){
            AppUser appUser = appUserMapper.selectByPhone1(phone);
            if (appUser == null){
                return ResponseFactory.err("号码不对，没有查到该用户");
            }
            userId = appUser.getId();
        }
        Merchant merchant = merchantMapper.selectByAdminId(webUserInfo.getSysAdmin().getCreateAdminId());
        // 更新余额
        UserMemberCard card = updateBalance(userId, cardId, (byte) 1, recharge, send);
        // 添加充值记录
        BigDecimal total = recharge;
        total = BigDecimalUtil.add(recharge.doubleValue(), send.doubleValue());
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
        int insert = memberChargeRecordMapper.insert(record);
        if (insert < 1) {
            return ResponseFactory.err("充值失败");
        }
        // 添加详细记录
        Float scale = null;
        scale = BigDecimalUtil.div(send.doubleValue(), total.doubleValue()).floatValue();
        detailCharge(userId, merchant.getId(), storeId, recharge, cardId, send, new BigDecimal("0"), (byte) 1, explain,
                total, scale, total, (byte) 1, eventId,record.getOrderNo());
        return ResponseFactory.sucMsg("充值成功");
    }


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
    public Response expenseCard(Integer userId,String phone,  Integer cardId, BigDecimal expense, String explain) {
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        Integer adminId = webUserInfo.getSysAdmin().getId();
        Store store = storeMapper.selectByAdminId(adminId);
        Integer storeId = null;
        if (store != null && store.getId() != null) {
            storeId = store.getId();
        }
        Merchant merchant = merchantMapper.selectByAdminId(webUserInfo.getSysAdmin().getCreateAdminId());
        // 如果用户id为空
        if(userId == null && !StringUtils.isEmpty(phone)){
            AppUser appUser = appUserMapper.selectByPhone1(phone);
            if (appUser == null){
                return ResponseFactory.err("号码不对，没有查到该用户");
            }
            userId = appUser.getId();
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
            return ResponseFactory.err("失败");
        }
        // 添加详细记录
        int storeMemberId = detailCharge(userId, merchant.getId(), storeId, new BigDecimal("0"), cardId, new BigDecimal("0"), expense, (byte) 2, explain, expense,
                0f, new BigDecimal("0"), (byte) 4, null,re.getOrderNo());
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
                    addStoreTurnover(storeMemberId, expense1, charge.getScale(), storeId, charge.getStoreId(), charge.getMemberEventId());
                    break;
                } else if (charge.getBalance().compareTo(expense1) > 0) {
                    //余额大于消费金额，更新详细记录，添加消费营业额记录
                    BigDecimal sub = BigDecimalUtil.sub(charge.getBalance().doubleValue(), expense1.doubleValue());
                    updateDetailCharge(charge.getId(), sub, (byte) 2);
                    addStoreTurnover(storeMemberId, expense1, charge.getScale(), storeId, charge.getStoreId(), charge.getMemberEventId());
                    break;
                } else {
                    //余额小于消费金额
                    //扣除第一次充值的余额后还不够的钱
                    BigDecimal sub = BigDecimalUtil.sub(expense1.doubleValue(), charge.getBalance().doubleValue());
                    updateDetailCharge(charge.getId(), new BigDecimal("0"), (byte) 3);
                    addStoreTurnover(storeMemberId, charge.getBalance(), charge.getScale(), storeId, charge.getStoreId(), charge.getMemberEventId());
                    expense1 = sub;
                }
            }
        }
        return ResponseFactory.sucMsg("成功");
    }


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


    private int detailCharge(Integer userId, Integer merchant, Integer storeId, BigDecimal re, Integer cardId, BigDecimal send, BigDecimal ex,
                             Byte type, String explain, BigDecimal total, Float scale, BigDecimal balance, Byte status, Integer eventId,Long orderNo) {
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
     * 更新详细记录
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

    private void addStoreTurnover(Integer storeMemberId, BigDecimal ex, Float scale, Integer storeId,
                                  Integer blagStoreId, Integer eventId) {
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
}
