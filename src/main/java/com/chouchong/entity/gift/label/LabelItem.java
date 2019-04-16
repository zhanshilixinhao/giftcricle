package com.chouchong.entity.gift.label;

import java.util.Date;

public class LabelItem {
    private Integer id;

    private Integer itemId;

    private Integer labelId;

    private Integer sort;

    private Byte status;

    private Date created;

    private Date updated;

    public LabelItem(Integer id, Integer itemId, Integer labelId, Integer sort, Byte status, Date created, Date updated) {
        this.id = id;
        this.itemId = itemId;
        this.labelId = labelId;
        this.sort = sort;
        this.status = status;
        this.created = created;
        this.updated = updated;
    }

    public LabelItem() {
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

    public Integer getLabelId() {
        return labelId;
    }

    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
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
