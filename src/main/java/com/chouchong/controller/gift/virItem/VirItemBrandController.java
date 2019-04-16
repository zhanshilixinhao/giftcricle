package com.chouchong.controller.gift.virItem;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.service.gift.virItem.VirItemBrandService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yy
 * @date 2018/7/2
 **/

@RestController
@RequestMapping("manage/virItemBrand")
public class VirItemBrandController {
    @Autowired
    private VirItemBrandService virItemBrandService;

    /**
     * 获得虚拟商品品牌列表
     *
     * @param: [page 分页信息, name 品牌名称]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/2
     */
    @PostMapping("list")
    public Response getVirItemBrandList(PageQuery page, String name) {
        return virItemBrandService.getVirItemBrandList(page, name);
    }

    /**
     * 删除虚拟商品品牌
     *
     * @param: [id 虚拟商品品牌id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/2
     */
    @PostMapping("del")
    public Response delVirItemBrand(Integer id) {
        return virItemBrandService.delVirItemBrand(id);
    }

    /**
     * 添加虚拟商品品牌
     *
     * @param: [name 品牌名称]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/2
     */
    @PostMapping("add")
    public Response addVirItemBrand(String name) {
        if (StringUtils.isAnyBlank(name)) {
            ResponseFactory.err("参数错误!");
        }
        return virItemBrandService.addVirItemBrand(name);
    }

    /**
     * 修改虚拟商品品牌
     *
     * @param: [id 品牌id, name 品牌名称, sort 排序值]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/2
     */
    @PostMapping("update")
    public Response updateVirItemBrand(Integer id, String name) {
        if (id == null || name == null) {
            ResponseFactory.err("参数错误!");
        }
        return virItemBrandService.updateVirItemBrand(id, name);
    }

    /**
     * 查询全部的虚拟商品品牌
     *
     * @param: []
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/2
     */
    @PostMapping("all")
    public Response getAllVirItemBrand() {
        return virItemBrandService.getAllVirItemBrand();
    }
}
