package com.chouchong.controller.gift.virItem;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.entity.gift.virItem.VirtualItem;
import com.chouchong.service.gift.virItem.VirItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yy
 * @date 2018/7/2
 **/

@RestController
@RequestMapping("manage/virItem")
public class VirItemController {
    @Autowired
    private VirItemService virItemService;

    /**
     * 获得虚拟商品列表
     *
     * @param: [page, search 查询条件]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/2
     */
    @PostMapping("list")
    public Response getVirItemList(PageQuery page, String search) {
        return virItemService.getVirItemList(page, search);
    }

    /**
     * 修改虚拟商品状态
     *
     * @param: [id 虚拟商品id, status 虚拟商品状态]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/2
     */
    @PostMapping("status")
    public Response changeStatus(Integer id, Integer status) {
        return virItemService.changeStatus(id, status);
    }

    /**
     * 删除虚拟商品
     *
     * @param: [id 虚拟商品id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/2
     */
    @PostMapping("del")
    public Response delVirItemList(Integer id) {
        if (id == null) {
            return ResponseFactory.errMissingParameter();
        }
        return virItemService.delVirItemList(id);
    }

    /**
     * 添加虚拟商品
     *
     * @param: [virtualItem 虚拟商品信息]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/2
     */
    @PostMapping("add")
    public Response addVirItem(VirtualItem virtualItem) {
        if (virtualItem == null) {
            return ResponseFactory.errMissingParameter();
        }
        if (virtualItem.getCateId() == null || virtualItem.getCover() == null ||
                virtualItem.getDescription() == null || virtualItem.getPrice() == null || virtualItem.getName() == null) {
            return ResponseFactory.errMissingParameter();
        }
        return virItemService.addVirItem(virtualItem);
    }

    /**
     * 修改虚拟商品
     *
     * @param: [virtualItem 虚拟商品信息]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/2
     */
    @PostMapping("update")
    public Response updateVirItem(VirtualItem virtualItem) {
        if (virtualItem == null) {
            return ResponseFactory.errMissingParameter();
        }
        if (virtualItem.getCateId() == null || virtualItem.getCover() == null || virtualItem.getId() == null ||
                virtualItem.getDescription() == null || virtualItem.getPrice() == null || virtualItem.getName() == null) {
            return ResponseFactory.errMissingParameter();
        }
        return virItemService.updateVirItem(virtualItem);
    }

    /**
     * 获得虚拟商品详情
     *
     * @param: [id 虚拟商品id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/2
     */
    @PostMapping("detail")
    public Response getVirItemDetail(Integer id) {
        if (id == null) {
            return ResponseFactory.errMissingParameter();
        }
        return virItemService.getVirItemDetail(id);
    }

}
