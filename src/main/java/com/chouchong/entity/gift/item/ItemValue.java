package com.chouchong.entity.gift.item;

import java.util.Date;

public class ItemValue {
    private Integer id;

    private Integer itemId;

    private Integer featureId;

    private String value;

    private Integer sort;

    private Date created;

    private Date updated;

    public ItemValue(Integer id, Integer itemId, Integer featureId, String value, Integer sort, Date created, Date updated) {
        this.id = id;
        this.itemId = itemId;
        this.featureId = featureId;
        this.value = value;
        this.sort = sort;
        this.created = created;
        this.updated = updated;
    }

    public ItemValue() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

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
        this.value = value == null ? null : value.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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