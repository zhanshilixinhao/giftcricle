package com.chouchong.service.gift.item;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;

public interface BrandService {
    /**
     * 根据品牌名称查询品牌列表
     *
     * @param: [page, name 品牌名称]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    Response getBrandList(PageQuery page, String name);

    /**
     * 添加品牌
     *
     * @param: [name 品牌名称, logo 品牌logo]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    Response addBrand(String name, String logo);

    /**
     * 修改品牌
     *
     * @param: [id 品牌id, name 品牌名称, logo 品牌logo]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    Response updateBrand(Integer id, String name, String logo);

    /**
     * 删除品牌
     *
     * @param: [id 品牌id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    Response delBrand(Integer id);

    /**
     * 获得全部的商品品牌
     *
     * @param: []
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    Response getAllBrand();
}
