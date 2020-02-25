package com.chouchong.service.v3;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.entity.v3.UserMemberCard;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * @author linqin
 * @date 2019/11/11
 */

public interface UserCardService {


    /**
     * 获取用户会员卡列表
     * @param page
     * @param nickname 用户昵称
     * @param phone 用户电话
     * @return
     */
    Response getUserCardList(PageQuery page, String nickname, String phone,Byte type,String title,String storeName,Integer isExport);


    /**
     * 用户会员卡详情
     * @param cardId
     * @param userId 用户id
     * @return
     */
    Response cardInfo( Integer userId,Integer cardId);


    /**
     * 分店获取用户会员卡列表
     * @param page
     * @param cardNo 卡号
     * @param phone 用户电话
     * @return
     */
    Response getUserCardList1(PageQuery page, String cardNo, String phone,String title,String storeName,
                              Integer isExport);

    /**
     * 分店开卡
     * @param card
     * @return
     */
    Response addUserCard(UserMemberCard card) throws IOException;

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
    Response chargeCard(Integer userId, String phone, Integer cardId, BigDecimal recharge,
                        String explain, BigDecimal send, Integer eventId,String image) throws IOException;
    /**
     * 分店消费（线下消费）
     * @param userId 用户id
     * @param cardId 会员卡id
     * @param expense 消费金额
     * @param explain 消费说明
     * @return
     */
    Response expenseCard(Integer userId,String phone,  Integer cardId, BigDecimal expense, String explain,String password) throws IOException;


    /**
     * 修改用户的会员卡等级
     * @param userId 用户id
     * @param cardId 会员卡id
     * @param gradeId 会员卡等级id
     * @return
     */
    Response updateCardGrade(Integer userId, Integer cardId, Integer gradeId);

    /**
     * 查询活动卡充值赠送金额的余额
     * @param userId 用户id
     * @param cardId 会员卡id
     * @return
     */
    Response getEventCardDetail(Integer userId, Integer cardId);

    /**
     * 改变查询活动卡充值赠送金额状态
     * @param storeMemberEventId 用户id
     * @return
     */
    Response getEventCardStatus(Integer storeMemberEventId);


    /******************************************************小程序管理端*****************************************************/

    /**
     * 分店获取用户会员卡列表(小程序端)
     * @param page
     * @param phone 用户电话
     * @return
     */
    Response getUserCardListManage(PageQuery page, String phone);


    /**
     * 小程序管理端会员详情
     *
     * @return
     */
    Response userCardDetail(Integer userId);


    /**
     * 查询所有会员卡充值赠送金额的余额
     *
     * @param userId 用户id
     * @param cardId 会员卡id
     * @return
     */
    Response getCardDetail(Integer userId, Integer cardId);

    /**
     * 退卡
     *
     * @param userId 用户id
     * @param cardId 会员卡id
     * @return
     */
    Response backCard(Integer userId, Integer cardId, BigDecimal capital,BigDecimal send);
}
