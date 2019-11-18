package com.chouchong.service.v3.impl;

import com.chouchong.common.ErrorCode;
import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.iwant.appUser.AppUserMapper;
import com.chouchong.dao.iwant.merchant.MerchantMapper;
import com.chouchong.dao.v3.*;
import com.chouchong.entity.iwant.appUser.AppUser;
import com.chouchong.entity.iwant.merchant.Merchant;
import com.chouchong.entity.v3.*;
import com.chouchong.exception.ServiceException;
import com.chouchong.redis.MRedisTemplate;
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


    /**
     * 获取用户会员卡列表
     *
     * @param page
     * @param cardNo
     * @param phone  用户电话
     * @return
     */
    @Override
    public Response getUserCardList(PageQuery page, String cardNo, String phone) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        Integer adminId = null;
        if (webUserInfo.getRoleId() == 3) {
            adminId = webUserInfo.getSysAdmin().getId();
        }
        List<UserCardVo> list = userMemberCardMapper.selectBySearch(cardNo, phone, adminId);
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
    public Response getUserCardList1(PageQuery page, String cardNo, String phone) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
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
        List<UserCardVo> list1 = userMemberCardMapper.selectBySearch1(cardNo, phone, list);
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
        if (userMemberCard != null){
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
    public Response chargeCard(Integer userId, Integer cardId, BigDecimal recharge,
                               String explain, BigDecimal send, Integer eventId) {
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        Integer adminId = webUserInfo.getSysAdmin().getId();
        Store store = storeMapper.selectByAdminId(adminId);
        Integer storeId = null;
        if (store != null && store.getId() != null) {
            storeId = store.getId();
        }
        Merchant merchant = merchantMapper.selectByAdminId(webUserInfo.getSysAdmin().getCreateAdminId());
        // 添加充值记录
        MemberChargeRecord record = new MemberChargeRecord();
        record.setMembershipCardId(cardId);
        record.setUserId(userId);
        record.setMemberEventId(eventId);
        record.setRechargeMoney(recharge);
        record.setSendMoney(send);
        record.setType((byte) 2);
        record.setStoreId(storeId);
        record.setAdminId(adminId);
        if(StringUtils.isEmpty(explain)){
            record.setExplain("余额充值");
        }else {
            record.setExplain(explain);
        }
        int insert = memberChargeRecordMapper.insert(record);
        if (insert < 1) {
            return ResponseFactory.err("充值失败");
        }
        // 更新余额
        updateBalance(userId, cardId, (byte) 1, recharge, send);
        // 添加详细记录
        BigDecimal total = recharge;
        Float scale = null;
        if (send != null) {
            total = BigDecimalUtil.add(recharge.doubleValue(), send.doubleValue());
            scale = BigDecimalUtil.div(send.doubleValue(), total.doubleValue()).floatValue();
        }
        detailCharge(userId, merchant.getId(), storeId, recharge, cardId, send, new BigDecimal("0"), (byte) 1, explain, total, scale);
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
    public Response expenseCard(Integer userId, Integer cardId, BigDecimal expense, String explain) {
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        Integer adminId = webUserInfo.getSysAdmin().getId();
        Store store = storeMapper.selectByAdminId(adminId);
        Integer storeId = null;
        if (store != null && store.getId() != null) {
            storeId = store.getId();
        }
        Merchant merchant = merchantMapper.selectByAdminId(webUserInfo.getSysAdmin().getCreateAdminId());
        // 添加消费记录
        MemberExpenseRecord re = new MemberExpenseRecord();
        re.setMembershipCardId(cardId);
        re.setUserId(userId);
        re.setExpenseMoney(expense);
        re.setType((byte) 2);
        re.setStoreId(storeId);
        re.setAdminId(adminId);
        if(StringUtils.isEmpty(explain)){
            re.setExplain("线下消费");
        }else {
            re.setExplain(explain);
        }
        int insert = memberExpenseRecordMapper.insert(re);
        if (insert < 1) {
            return ResponseFactory.err("失败");
        }
        // 更新余额
        updateBalance(userId, cardId, (byte) 2, expense, new BigDecimal("0"));
        // 添加详细记录
        detailCharge(userId, merchant.getId(), storeId, new BigDecimal("0"), cardId, new BigDecimal("0"), expense, (byte) 2, explain, expense, 0f);
        return ResponseFactory.sucMsg("成功");
    }


    private void updateBalance(Integer userId, Integer cardId, Byte type, BigDecimal balance, BigDecimal send) {
        UserMemberCard card = userMemberCardMapper.selectByUseridcardId(userId, cardId);
        if (card == null) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "该用户会员卡不存在");
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
    }


    private void detailCharge(Integer userId, Integer merchant, Integer storeId, BigDecimal re, Integer cardId,
                              BigDecimal send, BigDecimal ex, Byte type, String explain, BigDecimal total, Float scale) {
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
        int insert = storeMemberChargeMapper.insert(store);
        if (insert < 1) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "失败");
        }
    }


}
