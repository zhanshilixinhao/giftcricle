package com.chouchong.entity.gift.item;

import java.util.Date;

public class ItemFeature {
    private Integer id;

    private String name;

    private Integer sort;

    private String values;

    private Byte isSelect;

    private Byte status;

    private Date created;

    private Date updated;

    private Integer adminId;

    public ItemFeature(Integer id, String name, Integer sort, String values, Byte isSelect, Byte status, Date created, Date updated) {
        this.id = id;
        this.name = name;
        this.sort = sort;
        this.values = values;
        this.isSelect = isSelect;
        this.status = status;
        this.created = created;
        this.updated = updated;
    }

    public ItemFeature() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values == null ? null : values.trim();
    }

    public Byte getIsSelect() {
        return isSelect;
    }

    public void setIsSelect(Byte isSelect) {
        this.isSelect = isSelect;
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

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }
}
