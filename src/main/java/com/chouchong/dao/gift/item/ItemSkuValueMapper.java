package com.chouchong.dao.gift.item;

import com.chouchong.entity.gift.item.ItemSkuValue;

public interface ItemSkuValueMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItemSkuValue record);

    int insertSelective(ItemSkuValue record);

    ItemSkuValue selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ItemSkuValue record);

    int updateByPrimaryKey(ItemSkuValue record);

    /**
     * 根据skuId删除item_sku_value里面的值
     * @param id
     * @return
     */
    int deleteBySkuId(Integer id);
}
