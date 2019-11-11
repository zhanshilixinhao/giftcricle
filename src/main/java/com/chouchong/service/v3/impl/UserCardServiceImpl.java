package com.chouchong.service.v3.impl;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.iwant.appUser.AppUserMapper;
import com.chouchong.dao.v3.*;
import com.chouchong.entity.iwant.appUser.AppUser;
import com.chouchong.entity.v3.MemberChargeRecord;
import com.chouchong.entity.v3.MembershipCard;
import com.chouchong.entity.v3.Store;
import com.chouchong.entity.v3.UserMemberCard;
import com.chouchong.service.v3.UserCardService;
import com.chouchong.service.v3.vo.ChargeVo;
import com.chouchong.service.v3.vo.ExpenseVo;
import com.chouchong.service.v3.vo.RecordVo;
import com.chouchong.service.v3.vo.UserCardVo;
import com.chouchong.service.webUser.vo.WebUserInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author linqin
 * @date 2019/11/11
 */
@Service
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
        if (!CollectionUtils.isEmpty(cardList)){
            for (MembershipCard card : cardList) {
                if (card.getStoreIds().indexOf(store.getId()) != -1){
                    list.add(card.getId());
                }
            }
        }
        List<UserCardVo> list1 = userMemberCardMapper.selectBySearch1(cardNo, phone, list);
        PageInfo pageInfo = new PageInfo<>(list1);
        return ResponseFactory.page(list1, pageInfo.getTotal(), pageInfo.getPages(),
                pageInfo.getPageNum(), pageInfo.getPageSize());

    }



    /**
     * 分店开卡
     * @param card
     * @return
     */
    @Override
    public Response addUserCard(UserMemberCard card) {
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        Integer adminId = webUserInfo.getSysAdmin().getId();
        Store store = storeMapper.selectByAdminId(adminId);
        AppUser appUser = appUserMapper.selectByPhone1(card.getPhone());
        if (appUser == null){
            return ResponseFactory.err("需要先注册礼遇圈");
        }
        UserMemberCard ca = new UserMemberCard();
        ca.setMembershipCardId(card.getMembershipCardId());
        ca.setUserId(appUser.getId());
        ca.setBalance(new BigDecimal("0"));
        ca.setTotalAmount(new BigDecimal("0"));
        ca.setConsumeAmount(new BigDecimal("0"));
        ca.setStatus((byte)1);
        ca.setStoreId(store.getId());
        ca.setPhone(card.getPhone());
        ca.setAdminId(adminId);
        int insert = userMemberCardMapper.insert(ca);
        if (insert < 1){
            return ResponseFactory.err("开卡失败");
        }
        return ResponseFactory.sucMsg("开卡成功");
    }

    /**
     * 分店会员卡充值
     * @param userId 用户id
     * @param cardId 会员卡id
     * @param recharge 充值金额
     * @param explain 充值说明
     * @param send 赠送金额
     * @param eventId 活动id
     * @return
     */
    @Override
    public Response chargeCard(Integer userId, Integer cardId, BigDecimal recharge,
                               String explain, BigDecimal send, Integer eventId) {
        MemberChargeRecord record = memberChargeRecordMapper.selectByUserIdCardId(userId,cardId);
    }


}
