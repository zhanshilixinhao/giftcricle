package com.chouchong.service.gift.item.vo;

/**
 * sku列表的组合内容
 *
 * @author yy
 * @date 2018/6/29
 **/
public class SkuContent {
    // 商品属性id
    private Integer featureId;
    // 属性值名称
    private String value;

    public Integer getFeatureId() {
        return featureId;
    }

    public void setFeatureId(Integer featureId) {
        this.featureId = featureId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
