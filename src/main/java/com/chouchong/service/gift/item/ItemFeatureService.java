package com.chouchong.service.gift.item;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;

public interface ItemFeatureService {
    /**
     * 根据商品属性名称查询商品属性列表
     *
     * @param: [page, name 属性名称]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/29
     */
    Response getItemFeatureList(PageQuery page, String name);

    /**
     * 添加商品属性
     *
     * @param: [name 属性名称]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/29
     */
    Response addItemFeature(String name);

    /**
     * 修改商品属性
     *
     * @param: [id 属性id, name 属性名称, sort 排序值]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/29
     */
    Response updateItemFeature(Integer id, String name, Integer sort);

    /**
     * 删除商品属性
     *
     * @param: [id 属性id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/29
     */
    Response delItemFeature(Integer id);

    /**
     * 查询全部的商品属性
     *
     * @param: []
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/29
     */
    Response getAllItemFeature();
}
