package com.chouchong.controller.gift.label;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.entity.gift.label.LabelItem;
import com.chouchong.service.gift.label.LabelItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author linqin
 * @date 2019/1/15 11:25
 */
@RestController
@RequestMapping("manage/labelItem")
public class LabelItemController {

    @Autowired
    private LabelItemService labelItemService;

    /**
     * 获取标签商品列表
     *
     * @param pageQuery
     * @param search
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    @PostMapping("list")
    public Response getLabelItemList(PageQuery pageQuery, String search) {
        return labelItemService.getLabelItemList(pageQuery, search);
    }

    /**
     * 修改主题商品状态
     *
     * @param id     标签商品id
     * @param status 状态
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    @PostMapping("status")
    public Response changeStatus(Integer id, Integer status) {
        if (id == null || status == null) {
            return ResponseFactory.errMissingParameter();
        }
        return labelItemService.changeStatus(id, status);
    }

    /**
     * 删除商品
     *
     * @param id 标签商品id
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    @PostMapping("del")
    public Response delLabelItem(Integer id) {
        if (id == null) {
            return ResponseFactory.errMissingParameter();
        }
        return labelItemService.delLabelItem(id);
    }


    /**
     * 添加标签商品
     *
     * @param labelId 标签id
     * @param ids     商品id集合
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    @PostMapping("add")
    public Response addLabelItem(Integer labelId, String ids) {
        if (labelId == null || ids == null) {
            return ResponseFactory.errMissingParameter();
        }
        if (ids.trim().equals("")) {
            return ResponseFactory.errMissingParameter();
        }
        return labelItemService.addLabelItem(labelId, ids);
    }

    /**
     * 标签商品排序
     *
     * @param
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    @PostMapping("sort")
    public Response setLabelItemSort(LabelItem labelItem) {
        if (labelItem == null) {
            return ResponseFactory.errMissingParameter();
        }
        if (labelItem.getId() == null) {
            return ResponseFactory.errMissingParameter();
        }
        return labelItemService.setThemeItemSort(labelItem);
    }




}
