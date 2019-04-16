package com.chouchong.controller.gift.item;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.service.gift.item.ItemFeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yy
 * @date 2018/6/28
 **/

@RestController
@RequestMapping("manage/itemFeature")
public class ItemFeatureController {
    @Autowired
    private ItemFeatureService itemFeatureService;

    /**
     * 根据商品属性名称查询商品属性列表
     *
     * @param: [page, name 属性名称]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/28
     */
    @PostMapping("list")
    private Response getItemFeatureList(PageQuery page, String name) {
        return itemFeatureService.getItemFeatureList(page, name);
    }

    /**
     * 添加商品属性
     *
     * @param: [name 属性名称]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/29
     */
    @PostMapping("add")
    private Response addItemFeature(String name) {
        if (name == null) {
            return ResponseFactory.errMissingParameter();
        }
        return itemFeatureService.addItemFeature(name);
    }

    /**
     * 修改商品属性
     *
     * @param: [id 属性id, name 属性名称, sort 排序值]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/29
     */
    @PostMapping("update")
    public Response updateItemFeature(Integer id, String name, Integer sort) {
        if (id == null || name == null || sort == null) {
            ResponseFactory.err("参数错误!");
        }
        return itemFeatureService.updateItemFeature(id, name, sort);
    }

    /**
     * 删除商品属性
     *
     * @param: [id 属性id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/29
     */
    @PostMapping("del")
    public Response delItemFeature(Integer id) {
        if (id == null) {
            ResponseFactory.err("参数错误!");
        }
        return itemFeatureService.delItemFeature(id);
    }

    /**
     * 查询全部的商品属性
     *
     * @param: []
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/29
     */
    @PostMapping("all")
    public Response getAllItemFeature() {
        return itemFeatureService.getAllItemFeature();
    }
}
