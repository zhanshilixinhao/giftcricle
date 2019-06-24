package com.chouchong.entity.gift.item;

import java.util.Date;

public class RecommendedToday {
    private Integer id;

    private Integer itemId;

    private Integer sort;

    private Byte status;

    private Date day;

    private Date created;

    private Date updated;

    public RecommendedToday(Integer id, Integer itemId, Integer sort, Byte status, Date day, Date created, Date updated) {
        this.id = id;
        this.itemId = itemId;
        this.sort = sort;
        this.status = status;
        this.day = day;
        this.created = created;
        this.updated = updated;
    }

    public RecommendedToday() {
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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
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