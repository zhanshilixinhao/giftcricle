package com.chouchong.service.gift.item.vo;

import java.util.List;

/**
 * @author yy
 * @date 2018/6/29
 **/
public class FeatureValue {
    private String name;

    private List<ItemValueVo> itemValueVos;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ItemValueVo> getItemValueVos() {
        return itemValueVos;
    }

    public void setItemValueVos(List<ItemValueVo> itemValueVos) {
        this.itemValueVos = itemValueVos;
    }
}
