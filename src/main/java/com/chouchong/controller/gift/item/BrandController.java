package com.chouchong.controller.gift.item;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.service.gift.item.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yy
 * @date 2018/6/25
 **/

@RestController
@RequestMapping("manage/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 根据品牌名称查询品牌列表
     *
     * @param: [page, name 品牌名称]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    @PostMapping("list")
    public Response getBrandList(PageQuery page, String name) {
        return brandService.getBrandList(page, name);
    }

    /**
     * 添加品牌
     *
     * @param: [name 品牌名称, logo 品牌logo]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    @PostMapping("add")
    public Response addBrand(String name, String logo) {
        if (name == null) {
            return ResponseFactory.errMissingParameter();
        }
        return brandService.addBrand(name, logo);
    }

    /**
     * 修改品牌
     *
     * @param: [id 品牌id, name 品牌名称, logo 品牌logo]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    @PostMapping("update")
    public Response updateBrand(Integer id, String name, String logo) {
        if (id == null || name == null) {
            return ResponseFactory.errMissingParameter();
        }
        return brandService.updateBrand(id, name, logo);
    }

    /**
     * 删除品牌
     *
     * @param: [id 品牌id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    @PostMapping("del")
    public Response delBrand(Integer id) {
        if (id == null) {
            return ResponseFactory.errMissingParameter();
        }
        return brandService.delBrand(id);
    }

    /**
     * 获得全部的商品品牌
     *
     * @param: []
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    @PostMapping("all")
    public Response getAllBrand() {
        return brandService.getAllBrand();
    }
}
