package com.chouchong.controller.v3;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.entity.v3.UserMemberCard;
import com.chouchong.service.v3.UserCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * @param page
     * @param cardNo 卡号
     * @param phone 用户电话
     * @return
     */
    @PostMapping("list")
    public Response getUserCardList(PageQuery page, String cardNo, String phone){
        return userCardService.getUserCardList(page,cardNo,phone);
    }

    /**
     * 用户会员卡充值记录和消费记录
     * @param userId 用户id
     * @return
     */
    @PostMapping("info")
    public Response cardInfo(Integer userId,Integer cardId){
        if ( userId == null || cardId == null){
            return ResponseFactory.errMissingParameter();
        }
        return userCardService.cardInfo(userId,cardId);
    }

    /**
     * 分店获取用户会员卡列表
     * @param page
     * @param cardNo 卡号
     * @param phone 用户电话
     * @return
     */
    @PostMapping("list_store")
    public Response getUserCardList1(PageQuery page, String cardNo, String phone){
        return userCardService.getUserCardList1(page,cardNo,phone);
    }


    /**
     * 分店开卡
     * @param card
     * @return
     */
    @PostMapping("add")
    public Response addUserCard(UserMemberCard card){
        if (card.getMembershipCardId() == null ||card.getPhone() == null){
            return ResponseFactory.errMissingParameter();
        }
        return userCardService.addUserCard(card);
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
    @PostMapping("charge")
    public Response chargeCard(Integer userId, Integer cardId, BigDecimal recharge,String explain,
                               BigDecimal send,Integer eventId){
        if ( userId == null || cardId == null||recharge == null){
            return ResponseFactory.errMissingParameter();
        }
        return userCardService.chargeCard(userId,cardId,recharge,explain,send,eventId);
    }

    /**
     * 分店消费（线下消费）
     * @param userId 用户id
     * @param cardId 会员卡id
     * @param expense 消费金额
     * @param explain 消费说明
     * @return
     */
    @PostMapping("expense")
    public Response expenseCard(Integer userId, Integer cardId, BigDecimal expense,String explain){
        if ( userId == null || cardId == null||expense == null){
            return ResponseFactory.errMissingParameter();
        }
        return userCardService.expenseCard(userId,cardId,expense,explain);
    }



}