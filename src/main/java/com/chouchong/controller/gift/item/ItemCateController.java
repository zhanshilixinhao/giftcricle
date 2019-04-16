package com.chouchong.controller.gift.item;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.service.gift.item.ItemCateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yy
 * @date 2018/6/25
 **/

@RestController
@RequestMapping("manage/itemCate")
public class ItemCateController {
    @Autowired
    private ItemCateService itemCateService;

    /**
     * 根据商品分类名称获得商品分类列表
     *
     * @param: [page, name 分类名称]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    @PostMapping("list")
    public Response getItemCateList(PageQuery page, String name) {
        return itemCateService.getItemCateList(page, name);
    }

    /**
     * 根据父级id获得商品分类列表
     *
     * @param: [page, name 分类名称]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    @PostMapping("pid_list")
    public Response getItemCateListByPid(PageQuery page, Integer pid) {
        return itemCateService.getItemCateListByPid(page, pid);
    }


    /**
     * 删除商品分类
     *
     * @param: [id 分类id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    @PostMapping("del")
    public Response delItemCate(Integer id) {
        return itemCateService.delItemCate(id);
    }

    /**
     * 修改商品分类状态
     *
     * @param: [id 分类id, status 分类状态]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    @PostMapping("status")
    public Response changeStatus(Integer id, Integer status) {
        return itemCateService.changeStatus(id, status);
    }

    /**
     * 添加商品分类
     *
     * @param: [name 分类名称]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    @PostMapping("add")
    public Response addItemCate(String name) {
        if (StringUtils.isAnyBlank(name)) {
            ResponseFactory.err("参数错误!");
        }
        return itemCateService.addItemCate(name);
    }

    /**
     * 根据父级id添加商品分类
     *
     * @param: [name 分类名称]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    @PostMapping("pid_add")
    public Response addItemCateByPid(String name, Integer pid) {
        if (StringUtils.isAnyBlank(name) || pid == null) {
            ResponseFactory.err("参数错误!");
        }
        return itemCateService.addItemCateByPid(name,pid);
    }

    /**
     * 修改商品分类
     *
     * @param: [id 分类id, name 分类名称, sort 排序值]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    @PostMapping("update")
    public Response updateItemCate(Integer id, String name, Integer sort) {
        if (id == null || name == null || sort == null) {
            ResponseFactory.err("参数错误!");
        }
        return itemCateService.updateItemCate(id, name, sort);
    }

    /**
     * 查询全部的商品分类
     *
     * @param: []
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    @PostMapping("all")
    public Response getAllItemCate() {
        return itemCateService.getAllItemCate();
    }
}
