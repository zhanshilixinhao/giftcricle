package com.chouchong.controller.gift.item;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.entity.gift.item.ItemSku;
import com.chouchong.service.gift.item.ItemSKUService;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yy
 * @date 2018/6/28
 **/

@RestController
@RequestMapping("manage/itemSku")
public class ItemSKUController {
    @Autowired
    private ItemSKUService itemSKUService;

    /**
     * 添加组合商品
     *
     * @param: [productSKU 组合商品信息, productValue 商品属性值信息, productId 商品id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/29
     */
    @PostMapping("add")
    public Response addSku(String productSKU, String productValue, Integer productId) {
        return itemSKUService.addSku(productSKU, productValue, productId);
    }

    /**
     * 查询是否组合了商品
     *
     * @param: [id 商品id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/29
     */
    @PostMapping("isGroup")
    public Response isGroup(Integer id) {
        if (id == null) {
            return ResponseFactory.errMissingParameter();
        }
        return itemSKUService.isGroup(id);
    }

    /**
     * 获得商品组合列表
     *
     * @param: [page, search 查询条件]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/29
     */
    @PostMapping("list")
    public Response getItemSkuList(PageQuery page, String search) {
        return itemSKUService.getItemSkuList(page, search);
    }

    /**
     * 获得组合商品sku详情
     *
     * @param: [id sku的id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/29
     */
    @PostMapping("detail")
    public Response getItemSkuDetail(Integer id) {
        if (id == null) {
            return ResponseFactory.errMissingParameter();
        }
        return itemSKUService.getItemSkuDetail(id);
    }

    /**
     * 修改组合商品sku
     *
     * @param: [itemSku 组合商品信息]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/29
     */
    @PostMapping("update")
    public Response updateItemSku(ItemSku itemSku) {
        if (itemSku == null) {
            return ResponseFactory.errMissingParameter();
        }
        if (itemSku.getCover() == null || itemSku.getPictures() == null || itemSku.getPrice() == null ||
                itemSku.getStock() == null || itemSku.getTitle() == null || itemSku.getId() == null) {
            return ResponseFactory.errMissingParameter();
        }
        return itemSKUService.updateItemSku(itemSku);
    }

    /**
     * 修改组合商品状态
     *
     * @param: [id, status 商品状态]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/29
     */
    @PostMapping("status")
    public Response changeItemSkuStatus(Integer id, Integer status) {
        if (id == null || status == null) {
            return ResponseFactory.errMissingParameter();
        }
        return itemSKUService.changeItemSkuStatus(id, status);
    }

    /**
     * 获得商品sku的商品属性值
     *
     * @param: [id, status]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/29
     */
    @PostMapping("value")
    public Response getItemSkuValue(Integer id) {
        if (id == null) {
            return ResponseFactory.errMissingParameter();
        }
        return itemSKUService.getItemSkuValue(id);
    }

    /**
     * 修改商品属性值
     *
     * @param: [id 属性值id, value 属性值]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/29
     */
    @PostMapping("editValue")
    public Response editFeatureValue(Integer id, String value) {
        if (id == null || value == null) {
            return ResponseFactory.errMissingParameter();
        }
        return itemSKUService.editFeatureValue(id, value);
    }

    /**
     * 根据商品id 删除所有sku
     * @param itemId 商品id
     * @return
     */
    @PostMapping("delete_sku")
    public Response deleteSkuAll(Integer itemId){
        if (itemId == null){
            return ResponseFactory.errMissingParameter();
        }
        return itemSKUService.deleteSkuAll(itemId);
    }
}
