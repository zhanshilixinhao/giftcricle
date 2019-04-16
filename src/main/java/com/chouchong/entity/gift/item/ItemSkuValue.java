package com.chouchong.entity.gift.item;

import java.util.Date;

public class ItemSkuValue {
    private Integer id;

    private Integer skuId;

    private Integer valueId;

    private Byte status;

    private Date created;

    private Date updated;

    public ItemSkuValue(Integer id, Integer skuId, Integer valueId, Byte status, Date created, Date updated) {
        this.id = id;
        this.skuId = skuId;
        this.valueId = valueId;
        this.status = status;
        this.created = created;
        this.updated = updated;
    }

    public ItemSkuValue() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public Integer getValueId() {
        return valueId;
    }

    public void setValueId(Integer valueId) {
        this.valueId = valueId;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}