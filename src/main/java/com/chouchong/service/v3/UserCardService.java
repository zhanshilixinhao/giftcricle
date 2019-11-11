package com.chouchong.service.v3;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;

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
    Response getUserCardList(PageQuery page, String nickname, String phone);


    /**
     * 用户会员卡详情
     * @param cardId
     * @param userId 用户id
     * @return
     */
    Response cardInfo( Integer userId,Integer cardId);
}
