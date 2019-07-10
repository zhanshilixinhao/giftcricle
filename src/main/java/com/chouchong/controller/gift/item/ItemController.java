package com.chouchong.controller.gift.item;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.service.gift.item.ItemService;
import com.chouchong.service.gift.item.vo.ItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yy
 * @date 2018/6/25
 **/

@RestController
@RequestMapping("manage/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    /**
     * 根据查询条件获得商品列表
     *
     * @param: [page, search 查询条件]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    @PostMapping("list")
    public Response getItemList(PageQuery page, String search) {
        return itemService.getItemList(page, search);
    }

    /**
     * 添加商品
     *
     * @param: [itemVo 商品信息]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    @PostMapping("add")
    public Response addItem(ItemVo itemVo) {
        if (itemVo == null) {
            return ResponseFactory.errMissingParameter();
        }
        if (itemVo.getCategoryId() == null || itemVo.getDetail() == null || itemVo.getCover() == null ||
                itemVo.getPictures() == null || itemVo.getPrice() == null || itemVo.getTitle() == null ||
                itemVo.getReGender() == null) {
            return ResponseFactory.errMissingParameter();
        }
        return itemService.addItem(itemVo);
    }

    /**
     * 修改商品
     *
     * @param: [itemVo 商品信息]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    @PostMapping("update")
    public Response updateItem(ItemVo itemVo) {
        if (itemVo == null) {
            return ResponseFactory.errMissingParameter();
        }
        if (itemVo.getCategoryId() == null || itemVo.getDetail() == null || itemVo.getCover() == null ||
                itemVo.getPictures() == null || itemVo.getPrice() == null || itemVo.getTitle() == null ||
                itemVo.getReGender() == null || itemVo.getId() == null) {
            return ResponseFactory.errMissingParameter();
        }
        return itemService.updateItem(itemVo);
    }

    /**
     *获得商品详情
     *
     * @param: [id 商品id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    @PostMapping("detail")
    public Response getItemDetail(Integer id) {
        if (id == null) {
            return ResponseFactory.errMissingParameter();
        }
        return itemService.getItemDetail(id);
    }

    /**
     * 删除商品
     *
     * @param: [id 商品id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    @PostMapping("del")
    public Response delItem(Integer id) {
        if (id == null) {
            return ResponseFactory.errMissingParameter();
        }
        return itemService.delItem(id);
    }

    /**
     * 修改商品启用禁用状态
     *
     * @param: [id 商品id, status 商品状态]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    @PostMapping("status")
    public Response changeStatus(Integer id, Integer status) {
        if (status == null || id == null) {
            return ResponseFactory.errMissingParameter();
        }
        return itemService.changeStatus(id, status);
    }

    /**
     * 修改商品热门状态
     *
     * @param: [id 商品id, status 热门状态]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    @PostMapping("hot")
    public Response changeHot(Integer id, Integer status) {
        if (status == null || id == null) {
            return ResponseFactory.errMissingParameter();
        }
        return itemService.changeHot(id, status);
    }

    /**
     * 修改商品精选状态
     *
     * @param: [id 商品id, status 精选状态]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    @PostMapping("choiceness")
    public Response changeChoiceness(Integer id, Integer status) {
        if (status == null || id == null) {
            return ResponseFactory.errMissingParameter();
        }
        return itemService.changeChoiceness(id, status);
    }

    /**
     * 设置商品排序值
     * @param id 商品id
     * @param sort 排序值
     * @return
     */
    @PostMapping("sort")
    public Response setSort(Integer id, Integer sort) {
        if (sort == null || id == null) {
            return ResponseFactory.errMissingParameter();
        }
        return itemService.setSort(id, sort);
    }


}
