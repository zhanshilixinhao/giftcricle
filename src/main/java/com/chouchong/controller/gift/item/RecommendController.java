package com.chouchong.controller.gift.item;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.service.gift.item.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.crypto.Data;

/**
 * @author linqin
 * @date 2019/6/24
 */
@RestController
@RequestMapping("manage/recommend")
public class RecommendController {

    @Autowired
    private RecommendService recommendService;


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
    @PostMapping("list")
    public Response getRecommendList(PageQuery pageQuery, String name, Data day) {
        return recommendService.getRecommendList(pageQuery,name,day);
    }




}
