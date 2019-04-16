package com.chouchong.controller.gift.virItem;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.service.gift.virItem.VirItemCateService;
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
@RequestMapping("manage/virItemCate")
public class VirItemCateController {
    @Autowired
    private VirItemCateService virItemCateService;

    /**
     * 获得虚拟商品分类列表
     *
     * @param: [page 分页信息, name 虚拟商品分类名称]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/2
     */
    @PostMapping("list")
    public Response getVirItemCateList(PageQuery page, String name) {
        return virItemCateService.getVirItemCateList(page, name);
    }

    /**
     * 删除虚拟商品分类
     *
     * @param: [id 虚拟商品分类id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/2
     */
    @PostMapping("del")
    public Response delVirItemCate(Integer id) {
        return virItemCateService.delVirItemCate(id);
    }

    /**
     * 修改虚拟商品分类状态
     *
     * @param: [id 分类id, status 分类状态]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/2
     */
    @PostMapping("status")
    public Response changeStatus(Integer id, Integer status) {
        return virItemCateService.changeStatus(id, status);
    }

    /**
     * 添加虚拟商品分类
     *
     * @param: [name 分类名称]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    @PostMapping("add")
    public Response addVirItemCate(String name) {
        if (StringUtils.isAnyBlank(name)) {
            ResponseFactory.err("参数错误!");
        }
        return virItemCateService.addVirItemCate(name);
    }

    /**
     * 修改虚拟商品分类
     *
     * @param: [id 分类id, name 分类名称, sort 排序值]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    @PostMapping("update")
    public Response updateVirItemCate(Integer id, String name, Integer sort) {
        if (id == null || name == null || sort == null) {
            ResponseFactory.err("参数错误!");
        }
        return virItemCateService.updateVirItemCate(id, name, sort);
    }

    /**
     * 查询全部的虚拟商品分类
     *
     * @param: []
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    @PostMapping("all")
    public Response getAllVirItemCate() {
        return virItemCateService.getAllVirItemCate();
    }
}
