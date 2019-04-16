package com.chouchong.service.gift.virItem;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;

public interface VirItemCateService {
    /**
     * 获得虚拟商品分类列表
     *
     * @param: [page 分页信息, name 虚拟商品分类名称]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/2
     */
    Response getVirItemCateList(PageQuery page, String name);

    /**
     * 删除虚拟商品分类
     *
     * @param: [id 虚拟商品分类id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/2
     */
    Response delVirItemCate(Integer id);

    /**
     * 修改虚拟商品分类状态
     *
     * @param: [id 分类id, status 分类状态]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/2
     */
    Response changeStatus(Integer id, Integer status);

    /**
     * 添加虚拟商品分类
     *
     * @param: [name 分类名称]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    Response addVirItemCate(String name);

    /**
     * 修改虚拟商品分类
     *
     * @param: [id 分类id, name 分类名称, sort 排序值]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    Response updateVirItemCate(Integer id, String name, Integer sort);

    /**
     * 查询全部的虚拟商品分类
     *
     * @param: []
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    Response getAllVirItemCate();
}
