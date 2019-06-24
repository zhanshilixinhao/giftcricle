package com.chouchong.service.gift.item.impl;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.service.gift.item.RecommendService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;

/**
 * @author linqin
 * @date 2019/6/24
 */
@Service
public class RecommendServiceImpl implements RecommendService {


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
    @Override
    public Response getRecommendList(PageQuery pageQuery, String name, Data day) {
        PageHelper.startPage(pageQuery.getPageNum(),pageQuery.getPageSize());
//        List<LabelItemVo> labelItemVos = labelItemMapper.selectBySearch(map);
//        PageInfo pageInfo = new PageInfo<>(labelItemVos);
//        return ResponseFactory.page(labelItemVos, pageInfo.getTotal(),
//                pageInfo.getPages(), pageInfo.getPageNum(), pageInfo.getPageSize());
        return null;
    }



}
