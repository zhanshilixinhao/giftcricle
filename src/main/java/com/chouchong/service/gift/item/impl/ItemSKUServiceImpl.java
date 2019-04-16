package com.chouchong.service.gift.item.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.gift.item.ItemSkuMapper;
import com.chouchong.dao.gift.item.ItemSkuValueMapper;
import com.chouchong.dao.gift.item.ItemValueMapper;
import com.chouchong.entity.SkuListVo;
import com.chouchong.entity.SkuValueVo;
import com.chouchong.entity.gift.item.ItemSku;
import com.chouchong.entity.gift.item.ItemSkuValue;
import com.chouchong.entity.gift.item.ItemValue;
import com.chouchong.service.gift.item.ItemSKUService;
import com.chouchong.service.gift.item.vo.FeatureValue;
import com.chouchong.service.gift.item.vo.ItemSkuVo;
import com.chouchong.service.gift.item.vo.ItemValueVo;
import com.chouchong.service.gift.item.vo.SkuContent;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.nio.channels.SelectableChannel;
import java.util.*;

/**
 * @author yy
 * @date 2018/6/28
 **/

@Service
@Transactional(rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
public class ItemSKUServiceImpl implements ItemSKUService {
    @Autowired
    private ItemSkuMapper itemSkuMapper;

    @Autowired
    private ItemSkuValueMapper itemSkuValueMapper;

    @Autowired
    private ItemValueMapper itemValueMapper;

    /**
     * 添加组合商品
     *
     * @param: [productSKU 组合商品信息, productValue 商品属性值信息, productId 商品id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/29
     */
    @Override
    public Response addSku(String productSKU, String productValue, Integer productId) {
        List list = new ArrayList();
        JSONArray productSKUs = JSON.parseArray(productSKU);
        JSONArray productValues = JSON.parseArray(productValue);
        for (int i = 0; i < productValues.size(); i++) {
            JSONObject jsonObject = productValues.getJSONObject(i);
            // 获得商品属性
            Integer featureId = jsonObject.getInteger("id");
            // System.out.println("featureId = " + featureId);
            // 获得商品属性值
            JSONArray values = jsonObject.getJSONArray("values");
            for (int j = 0; j < values.size(); j++) {
                Map map = new HashMap();
                JSONObject value = values.getJSONObject(j);
                // 获得该商品属性值的标识id
                Integer valueId = value.getInteger("id");
                System.out.println("valueId = " + valueId);
                // 获得该商品属性值的名称
                String valueName = value.getString("value");
                // System.out.println("valueName = " + valueName);
                map.put("valueId", valueId);
                ItemValue productvaluess = new ItemValue();
                productvaluess.setFeatureId(featureId);
                productvaluess.setItemId(productId);
                productvaluess.setCreated(new Date());
                productvaluess.setUpdated(new Date());
                productvaluess.setSort(0);
                productvaluess.setValue(valueName);
                itemValueMapper.insert(productvaluess);
                map.put("productvalueid", productvaluess.getId());
                map.put("value", productvaluess.getValue());
                list.add(map);
            }
        }
        List list1 = new ArrayList();
        for (int a = 0; a < productSKUs.size(); a++) {
            Map map1 = new HashMap();
            JSONObject valueObj = productSKUs.getJSONObject(a);
            ItemSku productsku = new ItemSku();
            JSONArray arrayImages = valueObj.getJSONArray("images");
            List<String> images = new ArrayList<String>();
            for (int e = 0; e < arrayImages.size(); e++) {
                images.add(arrayImages.getString(e));
            }
            productsku.setCover(images.get(0));
            productsku.setPictures(JSON.toJSONString(images));
            // JSON.toJSONString(contestVo.getPictures())
            productsku.setCreated(new Date());
            productsku.setTitle(valueObj.getString("name"));
            productsku.setPrice(valueObj.getBigDecimal("price"));
            productsku.setItemId(productId);
            productsku.setSales(0);
            productsku.setStock(valueObj.getInteger("stock"));
            productsku.setStatus((byte) 2);
            productsku.setUpdated(new Date());
            itemSkuMapper.insert(productsku);
            Integer productskuid = productsku.getId();
            JSONArray groups = valueObj.getJSONArray("group");
            Integer arry[] = new Integer[groups.size()];
            for (int b = 0; b < groups.size(); b++) {
                JSONObject group = groups.getJSONObject(b);
                arry[b] = group.getInteger("id");
            }
            map1.put("productskuid", productskuid);
            map1.put("valueids", arry);
            list1.add(map1);
        }
        for (int x = 0; x < list1.size(); x++) {
            Map map = (Map) list1.get(x);
            Integer productskuid = (Integer) map.get("productskuid");
            Integer arry[] = (Integer[]) map.get("valueids");
            for (int y = 0; y < arry.length; y++) {
                Integer valueid = arry[y];
                for (int z = 0; z < list.size(); z++) {
                    Map map1 = (Map) list.get(z);
                    Integer valueid1 = (Integer) map1.get("valueId");
                    if (valueid == valueid1) {
                        Integer productvalueid = (Integer) map1.get("productvalueid");
                        // System.out.println("value = " + (String) map1.get("value"));
                        ItemSkuValue productskuv = new ItemSkuValue();
                        productskuv.setSkuId(productskuid);
                        productskuv.setValueId(productvalueid);
                        // productskuv.set((String) map1.get("value"));
                        productskuv.setCreated(new Date());
                        productskuv.setUpdated(new Date());
                        //productskuv.setSort(0);
                        productskuv.setStatus((byte) 0);
                        itemSkuValueMapper.insert(productskuv);
                    }

                }
            }
        }
        List<ItemSku> list11 = itemSkuMapper.selectAll();
        for (ItemSku itemSku : list11) {
            SkuListVo skuListVo = itemSkuMapper.selectDetailBySkuId(itemSku.getId());
            String linqin = genTitle(skuListVo);
            itemSkuMapper.updateBatch(itemSku.getId(), linqin);
        }
        return ResponseFactory.sucMsg("恭喜你添加成功!");
    }

    private String genTitle(SkuListVo skuListVo) {
        if (skuListVo == null) return "";
        StringBuilder sb = new StringBuilder();
        for (SkuValueVo value : skuListVo.getValues()) {
            sb.append(" ").append(value.getValue());
        }
        return sb.toString();
    }

    /**
     * 查询是否组合了商品
     *
     * @param: [id 商品id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/29
     */
    @Override
    public Response isGroup(Integer id) {
        List<ItemSku> itemSkus = itemSkuMapper.selectByItemId(id);
        return ResponseFactory.sucData(itemSkus.size());
    }

    /**
     * 获得商品组合列表
     *
     * @param: [page, search 查询条件]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/29
     */
    @Override
    public Response getItemSkuList(PageQuery page, String search) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        JSONObject jsonObject = JSON.parseObject(search);
        Map map = new HashMap();
        map.put("title", jsonObject.getString("title"));
        map.put("status", jsonObject.getInteger("status"));
        map.put("id", jsonObject.getInteger("id"));
        map.put("price", jsonObject.getInteger("price"));
        map.put("sales", jsonObject.getInteger("sales"));
        map.put("stock", jsonObject.getInteger("stock"));
        List<ItemSku> itemSkus = itemSkuMapper.selectBySearch(map);
        PageInfo pageInfo = new PageInfo<>(itemSkus);
        List<ItemSkuVo> itemSkuVos = new ArrayList<ItemSkuVo>();
        if (itemSkus.size() > 0) {
            for (ItemSku itemSku : itemSkus) {
                ItemSkuVo itemSkuVo = createVo(itemSku);
                List<SkuContent> skuContents = itemSkuMapper.selectGroupContent(itemSku.getId());
                itemSkuVo.setSkuContents(skuContents);
                itemSkuVos.add(itemSkuVo);
            }
        }
        return ResponseFactory.page(itemSkuVos, pageInfo.getTotal(),
                pageInfo.getPages(), pageInfo.getPageNum(), pageInfo.getPageSize());
    }

    /**
     * 获得组合商品sku详情
     *
     * @param: [id sku的id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/29
     */
    @Override
    public Response getItemSkuDetail(Integer id) {
        ItemSku itemSku = itemSkuMapper.selectByPrimaryKey(id);
        if (itemSku == null) {
            return ResponseFactory.err("无此商品");
        }
        return ResponseFactory.sucData(itemSku);
    }

    /**
     * 修改组合商品sku
     *
     * @param: [itemSkuVo 组合商品信息]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/29
     */
    @Override
    public Response updateItemSku(ItemSku itemSkuVo) {
        ItemSku itemSku = itemSkuMapper.selectByPrimaryKey(itemSkuVo.getId());
        if (itemSku == null) {
            return ResponseFactory.err("无此组合商品");
        }
        itemSku.setUpdated(new Date());
        itemSku.setStock(itemSkuVo.getStock());
        itemSku.setPrice(itemSkuVo.getPrice());
        itemSku.setPictures(itemSkuVo.getPictures());
        itemSku.setCover(itemSkuVo.getCover());
        itemSku.setTitle(itemSkuVo.getTitle());
        int count = itemSkuMapper.updateByPrimaryKey(itemSku);
        if (count == 1) {
            return ResponseFactory.sucMsg("修改成功");
        }
        return ResponseFactory.err("修改失败");
    }

    /**
     * 修改组合商品状态
     *
     * @param: [id, status 商品状态]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/29
     */
    @Override
    public Response changeItemSkuStatus(Integer id, Integer status) {
        ItemSku itemSku = itemSkuMapper.selectByPrimaryKey(id);
        if (itemSku == null) {
            return ResponseFactory.err("无此商品!");
        }
        itemSku.setStatus(status.byteValue());
        int count = itemSkuMapper.updateByPrimaryKey(itemSku);
        if (count == 1) {
            return ResponseFactory.sucMsg("设置成功");
        }
        return ResponseFactory.err("设置失败!");
    }

    /**
     * 获得商品sku的商品属性值
     *
     * @param: [id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/29
     */
    @Override
    public Response getItemSkuValue(Integer id) {
        List<FeatureValue> featureValues = itemValueMapper.selectByItemId(id);
        return ResponseFactory.sucData(featureValues);
    }

    /**
     * 修改商品属性值
     *
     * @param: [id 属性值id, value 属性值]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/29
     */
    @Override
    public Response editFeatureValue(Integer id, String value) {
        ItemValue itemValue = itemValueMapper.selectByPrimaryKey(id);
        if (itemValue == null) {
            return ResponseFactory.err("无此属性值");
        }
        itemValue.setUpdated(new Date());
        itemValue.setValue(value);
        int count = itemValueMapper.updateByPrimaryKey(itemValue);
        if (count == 1) {
            return ResponseFactory.sucMsg("修改成功");
        }
        return ResponseFactory.err("修改失败");
    }

    private ItemSkuVo createVo(ItemSku itemSku) {
        ItemSkuVo itemSkuVo = new ItemSkuVo();
        itemSkuVo.setCover(itemSku.getCover());
        itemSkuVo.setUpdated(itemSku.getUpdated());
        itemSkuVo.setTitle(itemSku.getTitle());
        itemSkuVo.setStock(itemSku.getStock());
        itemSkuVo.setStatus(itemSku.getStatus());
        itemSkuVo.setSales(itemSku.getSales());
        itemSkuVo.setPrice(itemSku.getPrice());
        itemSkuVo.setPictures(itemSku.getPictures());
        itemSkuVo.setItemId(itemSku.getItemId());
        itemSkuVo.setId(itemSku.getId());
        itemSkuVo.setCreated(itemSku.getCreated());
        return itemSkuVo;
    }


    /**
     * 根据商品id 删除所有sku
     *
     * @param itemId 商品id
     * @return
     */
    @Override
    public Response deleteSkuAll(Integer itemId) {
        List<ItemSku> itemSkus = itemSkuMapper.selectByItemId(itemId);
        if (itemSkus == null) {
            return ResponseFactory.err("sku不存在");
        }
//        取出skuId
        for (ItemSku skus : itemSkus) {
            // 根据skuId删除item_sku_value里面的值
            int i = itemSkuValueMapper.deleteBySkuId(skus.getId());
            if (i < 1){
                return ResponseFactory.err("删除itemSkuValue值失败");
            }
        }
        // 删除itemValue值
        int count = itemValueMapper.deleteByItemId(itemId);
        if (count < 1){
            return ResponseFactory.err("删除itemSkuValue值失败");
        }
        // 根据itemId删除sku值
        int i = itemSkuMapper.deleteByItemId(itemId);
        if (i < 1){
            return ResponseFactory.err("删除Sku值失败");
        }
        return ResponseFactory.sucMsg("删除成功");
    }


}
