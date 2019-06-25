package com.chouchong.service.gift.item;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;

import javax.xml.crypto.Data;
import java.text.ParseException;

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
    Response getRecommendList(PageQuery pageQuery, String name, Long day) throws ParseException;


    /**
     * 添加今日推荐商品
     *
     * @param day 日期
     * @param ids 商品id s
     * @return
     * @author linqin
     * @date 2019/6/24
     */
    Response addRecommendItem(Long day, String ids) throws ParseException;



    /**
     * 删除今日推荐商品
     *
     * @param id 今日推荐商品id
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    Response delRecommendItem(Integer id);

    /**
     * 今日推荐商品排序
     *
     * @param id 今日推荐商品id
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    Response sortRecommendItem(Integer id,Integer sort);
}
