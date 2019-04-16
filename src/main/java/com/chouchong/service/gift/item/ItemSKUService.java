package com.chouchong.service.gift.item;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.entity.gift.item.ItemSku;

public interface ItemSKUService {
    /**
     * 添加组合商品
     *
     * @param: [productSKU 组合商品信息, productValue 商品属性值信息, productId 商品id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/29
     */
    Response addSku(String productSKU, String productValue, Integer productId);

    /**
     * 查询是否组合了商品
     *
     * @param: [id 商品id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/29
     */
    Response isGroup(Integer id);

    /**
     * 获得商品组合列表
     *
     * @param: [page, search 查询条件]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/29
     */
    Response getItemSkuList(PageQuery page, String search);

    /**
     * 获得组合商品sku详情
     *
     * @param: [id sku的id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/29
     */
    Response getItemSkuDetail(Integer id);

    /**
     * 修改组合商品sku
     *
     * @param: [itemSku 组合商品信息]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/29
     */
    Response updateItemSku(ItemSku itemSku);

    /**
     * 修改组合商品状态
     *
     * @param: [id, status 商品状态]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/29
     */
    Response changeItemSkuStatus(Integer id, Integer status);

    /**
     * 获得商品属性值
     *
     * @param: [id 商品id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/29
     */
    Response getItemSkuValue(Integer id);

    /**
     * 修改商品属性值
     *
     * @param: [id 属性值id, value 属性值]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/29
     */
    Response editFeatureValue(Integer id, String value);

    /**
     * 根据商品id 删除所有sku
     * @param itemId 商品id
     * @return
     */
    Response deleteSkuAll(Integer itemId);
}
