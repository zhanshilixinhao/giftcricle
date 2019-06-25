package com.chouchong.controller.gift.item;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.service.gift.item.RecommendService;
import org.apache.commons.lang3.StringUtils;
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
    public Response getRecommendList(PageQuery pageQuery, String name, Long day) {
        return recommendService.getRecommendList(pageQuery, name, day);
    }


    /**
     * 添加今日推荐商品
     *
     * @param day 日期
     * @param ids 商品id s
     * @return
     * @author linqin
     * @date 2019/6/24
     */
    @PostMapping("add")
    public Response addRecommendItem(Long day, String ids) {
        if (day == null || StringUtils.isBlank(ids)) {
            return ResponseFactory.errMissingParameter();
        }
        if (ids.trim().equals("")) {
            return ResponseFactory.errMissingParameter();
        }
        return recommendService.addRecommendItem(day, ids);
    }



    /**
     * 删除今日推荐商品
     *
     * @param id 今日推荐商品id
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    @PostMapping("del")
    public Response delRecommendItem(Integer id) {
        if (id == null) {
            return ResponseFactory.errMissingParameter();
        }
        return recommendService.delRecommendItem(id);
    }


    /**
     * 今日推荐商品排序
     *
     * @param id 今日推荐商品id
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    @PostMapping("sort")
    public Response sortRecommendItem(Integer id,Integer sort) {
        if (id == null || sort == null) {
            return ResponseFactory.errMissingParameter();
        }
        return recommendService.sortRecommendItem(id,sort);
    }


}
