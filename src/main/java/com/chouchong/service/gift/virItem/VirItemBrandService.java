package com.chouchong.service.gift.virItem;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;

public interface VirItemBrandService {
    /**
     * 获得虚拟商品品牌列表
     *
     * @param: [page 分页信息, name 品牌名称]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/2
     */
    Response getVirItemBrandList(PageQuery page, String name);

    /**
     * 删除虚拟商品品牌
     *
     * @param: [id 虚拟商品品牌id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/2
     */
    Response delVirItemBrand(Integer id);

    /**
     * 添加虚拟商品品牌
     *
     * @param: [name 品牌名称]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/2
     */
    Response addVirItemBrand(String name);

    /**
     * 修改虚拟商品品牌
     *
     * @param: [id 品牌id, name 品牌名称, sort 排序值]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/2
     */
    Response updateVirItemBrand(Integer id, String name);

    /**
     * 查询全部的虚拟商品品牌
     *
     * @param: []
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/2
     */
    Response getAllVirItemBrand();
}
