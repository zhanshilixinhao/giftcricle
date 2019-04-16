package com.chouchong.service.gift.virItem;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.entity.gift.virItem.VirtualItem;

public interface VirItemService {
    /**
     * 获得虚拟商品列表
     *
     * @param: [page 分页信息, search 查询条件]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/2
     */
    Response getVirItemList(PageQuery page, String search);

    /**
     * 修改虚拟商品状态
     *
     * @param: [id 虚拟商品id, status 虚拟商品状态]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/2
     */
    Response changeStatus(Integer id, Integer status);

    /**
     * 删除虚拟商品
     *
     * @param: [id 虚拟商品id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/2
     */
    Response delVirItemList(Integer id);

    /**
     * 添加虚拟商品
     *
     * @param: [virtualItem 虚拟商品信息]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/2
     */
    Response addVirItem(VirtualItem virtualItem);

    /**
     * 获得虚拟商品详情
     *
     * @param: [id 虚拟商品id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/2
     */
    Response getVirItemDetail(Integer id);

    /**
     * 修改虚拟商品
     *
     * @param: [virtualItem 虚拟商品信息]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/2
     */
    Response updateVirItem(VirtualItem virtualItem);
}
