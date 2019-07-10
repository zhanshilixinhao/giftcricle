package com.chouchong.service.gift.item;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.service.gift.item.vo.ItemVo;

public interface ItemService {

    /**
     * 根据查询条件获得商品列表
     *
     * @param: [page, search 查询条件]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    Response getItemList(PageQuery page, String search);

    /**
     * 修改商品启用禁用状态
     *
     * @param: [id 商品id, status 商品状态]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    Response changeStatus(Integer id, Integer status);

    /**
     * 修改商品热门状态
     *
     * @param: [id 商品id, status 热门状态]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    Response changeHot(Integer id, Integer status);

    /**
     * 修改商品精选状态
     *
     * @param: [id 商品id, status 精选状态]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    Response changeChoiceness(Integer id, Integer status);

    /**
     * 添加商品
     *
     * @param: [itemVo 商品信息]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    Response addItem(ItemVo itemVo);

    /**
     * 获得商品详情
     *
     * @param: [id 商品id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    Response getItemDetail(Integer id);

    /**
     * 修改商品
     *
     * @param: [itemVo 商品信息]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    Response updateItem(ItemVo itemVo);

    /**
     * 删除商品
     *
     * @param: [id 商品id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    Response delItem(Integer id);

    /**
     * 设置商品排序值
     * @param id 商品id
     * @param sort 排序值
     * @return
     */
    Response setSort(Integer id, Integer sort);
}
