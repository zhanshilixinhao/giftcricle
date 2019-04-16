package com.chouchong.entity;


import java.math.BigDecimal;
import java.util.List;

/**
 * @author linqin
 * @date 2018/6/18
 */
public class SkuListVo {

    private Integer skuId;

    private Integer itemId;

    private String title;

    private String cover;

    private BigDecimal price;

    private Integer stock;

    private Integer sales;

    private List<SkuValueVo> values;

    private Integer isDefault = 2;// 是否为默认 1-是 2 不是

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public List<SkuValueVo> getValues() {
        return values;
    }

    public void setValues(List<SkuValueVo> values) {
        this.values = values;
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }
}
