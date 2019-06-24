package com.chouchong.service.gift.item;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;

import javax.xml.crypto.Data;

/**
 * @author linqin
 * @date 2019/6/24
 */

public interface RecommendService {

    /**
     * 获取今日推荐商品列表
     *
     * @param pageQuery
     * @param name      商品名称
     * @param day       日期
     * @return
     * @author linqin
     * @date 2019/6/24
     */
    Response getRecommendList(PageQuery pageQuery, String name, Data day);
}
