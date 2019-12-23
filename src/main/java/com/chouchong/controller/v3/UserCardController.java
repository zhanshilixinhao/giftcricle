package com.chouchong.controller.v3;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.entity.v3.UserMemberCard;
import com.chouchong.service.v3.UserCardService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.http.POST;

import java.math.BigDecimal;

/**
 * @author linqin
 * @date 2019/11/11
 */
@RestController
@RequestMapping("manage/v3/userCard")
public class UserCardController {

    @Autowired
    private UserCardService userCardService;

    /**
     * 获取用户会员卡列表
     *
     * @param page
     * @param cardNo 卡号
     * @param phone  用户电话
     * @param title  卡标题
     * @return
     */
    @PostMapping("list")
    public Response getUserCardList(PageQuery page, String cardNo, String phone, Byte type, String title) {
        return userCardService.getUserCardList(page, cardNo, phone, type, title);
    }

    /**
     * 用户会员卡充值记录和消费记录
     *
     * @param userId 用户id
     * @return
     */
    @PostMapping("info")
    public Response cardInfo(Integer userId, Integer cardId) {
        if (userId == null || cardId == null) {
            return ResponseFactory.errMissingParameter();
        }
        return userCardService.cardInfo(userId, cardId);
    }

    /**
     * 分店获取用户会员卡列表
     *
     * @param page
     * @param cardNo 卡号
     * @param phone  用户电话
     * @param title  卡标题
     * @return
     */
    @PostMapping("list_store")
    public Response getUserCardList1(PageQuery page, String cardNo, String phone, String title) {
        return userCardService.getUserCardList1(page, cardNo, phone, title);
    }


    /**
     * 分店开卡
     *
     * @param card
     * @return
     */
    @PostMapping("add")
    public Response addUserCard(UserMemberCard card) {
        if (card.getMembershipCardId() == null || card.getPhone() == null) {
            return ResponseFactory.errMissingParameter();
        }
        return userCardService.addUserCard(card);
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
    @PostMapping("charge")
    public Response chargeCard(Integer userId, String phone, Integer cardId, BigDecimal recharge, String explain,
                               BigDecimal send, Integer eventId) {
        if (cardId == null || recharge == null) {
            return ResponseFactory.errMissingParameter();
        }
        if (userId == null && StringUtils.isBlank(phone)) {
            return ResponseFactory.errMissingParameter();
        }
        return userCardService.chargeCard(userId, phone, cardId, recharge, explain, send, eventId);
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
    @PostMapping("expense")
    public Response expenseCard(Integer userId, String phone, Integer cardId, BigDecimal expense, String explain) {
        if (cardId == null || expense == null) {
            return ResponseFactory.errMissingParameter();
        }
        if (userId == null && StringUtils.isBlank(phone)) {
            return ResponseFactory.errMissingParameter();
        }
        return userCardService.expenseCard(userId, phone, cardId, expense, explain);
    }

    /**
     * 修改用户的会员卡等级
     *
     * @param userId  用户id
     * @param cardId  会员卡id
     * @param gradeId 会员卡等级id
     * @return
     */
    @PostMapping("update_grade")
    public Response updateUserGrade(Integer userId, Integer cardId, Integer gradeId) {
        if (userId == null || cardId == null || gradeId == null) {
            return ResponseFactory.errMissingParameter();
        }
        return userCardService.updateCardGrade(userId, cardId, gradeId);
    }

    /**
     * 查询活动卡充值赠送金额的余额
     *
     * @param userId 用户id
     * @param cardId 会员卡id
     * @return
     */
    @PostMapping("card_detail")
    public Response getEventCardDetail(Integer userId, Integer cardId) {
        if (userId == null || cardId == null) {
            return ResponseFactory.errMissingParameter();
        }
        return userCardService.getEventCardDetail(userId, cardId);
    }

    /**
     * 改变活动卡充值赠送金额状态
     *
     * @param storeMemberEventId 会员卡id
     * @return
     */
    @PostMapping("card_status")
    public Response getEventCardStatus(Integer storeMemberEventId) {
        if (storeMemberEventId == null) {
            return ResponseFactory.errMissingParameter();
        }
        return userCardService.getEventCardStatus(storeMemberEventId);
    }


    /******************************************************小程序管理端*****************************************************/

    /**
     * 分店获取用户会员卡列表(小程序端)
     *
     * @param page
     * @param phone 用户电话
     * @return
     */
    @PostMapping("user_card")
    public Response getUserCardListManage(PageQuery page, String phone) {
        return userCardService.getUserCardListManage(page, phone);
    }

    /**
     * 小程序管理端会员详情
     *
     * @return
     */
    @PostMapping("user_card_detail")
    public Response userCardDetail(Integer userId) {
        if (userId == null) {
            return ResponseFactory.errMissingParameter();
        }
        return userCardService.userCardDetail(userId);
    }

}
