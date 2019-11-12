package com.chouchong.service.v3;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.entity.v3.MemberEvent;

/**
 * @author linqin
 * @date 2019/11/8
 */

public interface CardEventService {

    /**
     * 获取活动列表
     * @param title 标题
     * @param type 类型
     * @param page
     * @return
     */
    Response getCardEventList(String title, Byte type, PageQuery page);

    /**
     * 添加活动
     * @param event
     * @return
     */
    Response addCardEvent(MemberEvent event);


    /**
     * 修改活动
     *
     * @param event
     * @return
     */
    Response updateCardEvent(MemberEvent event);

    /**
     * 删除活动
     * @param eventId 活动id
     * @return
     */
    Response deleteCardEvent(Integer eventId);

    /**
     * 活动详情
     * @param eventId 活动id
     * @return
     */
    Response detailCardEvent(Integer eventId);

     /**
     * 会员卡活动列表
     * @param cardId 会员卡id
     * @return
     */
    Response cardEvent(Integer cardId);
}
