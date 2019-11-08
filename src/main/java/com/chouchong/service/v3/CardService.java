package com.chouchong.service.v3;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.entity.v3.MembershipCard;

/**
 * @author linqin
 * @date 2019/11/6
 */

public interface CardService {


    /**
     * 获取会员卡列表
     * @param cardNo 卡号
     * @param title 标题
     * @param page
     * @return
     */
    Response getCardList(Long cardNo, String title, PageQuery page);


    /**
     * 添加会员卡
     * @param card
     * @return
     */
    Response addCard(MembershipCard card,String eventIds);


    /**
     * 修改会员卡
     * @param card
     * @return
     */
    Response updateCard(MembershipCard card,String eventIds);


    /**
     * 删除会员卡
     * @param cardId 会员卡id
     * @return
     */
    Response deleteCard(Integer cardId);


    /**
     * 会员卡详情
     * @param cardId 会员卡id
     * @return
     */
    Response detailCard(Integer cardId);

    /**
     * 获取所有可选门店
     * @return
     */
    Response allStoreList();

    /**
     * 获取自己创建的所有活动
     * @return
     */
    Response allEventList();
}
