package com.chouchong.service.gift.item;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;

public interface ItemCateService {
    /**
     * 根据商品分类名称获得商品分类列表
     *
     * @param: [page, name 分类名称]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    Response getItemCateList(PageQuery page, String name);

    /**
     * 删除商品分类
     *
     * @param: [id 分类id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    Response delItemCate(Integer id);

    /**
     * 修改商品分类状态
     *
     * @param: [id 分类id, status 分类状态]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    Response changeStatus(Integer id, Integer status);

    /**
     * 添加商品分类
     *
     * @param: [name 分类名称]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    Response addItemCate(String name);

    /**
     * 修改商品分类
     *
     * @param: [id 分类id, name 分类名称, sort 排序值]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    Response updateItemCate(Integer id, String name, Integer sort);

    /**
     * 查询全部的商品分类
     *
     * @param: []
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    Response getAllItemCate();

    /**
     * 根据父级id获得商品分类列表
     *
     * @param: [page, name 分类名称]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    Response getItemCateListByPid(PageQuery page, Integer pid);
    /**
     * 根据父级id添加商品分类
     *
     * @param: [name 分类名称]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    Response addItemCateByPid(String name, Integer pid);
}
