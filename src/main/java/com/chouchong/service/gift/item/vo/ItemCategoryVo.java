package com.chouchong.service.gift.item.vo;


import com.chouchong.entity.gift.item.ItemCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author linqin
 * @date 2019/4/10
 */

public class ItemCategoryVo extends ItemCategory {


    public List<ItemCategory> children;

    public ItemCategoryVo() {
        children = new ArrayList<>();
    }

}
