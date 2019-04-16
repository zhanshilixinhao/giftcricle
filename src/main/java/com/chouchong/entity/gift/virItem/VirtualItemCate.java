package com.chouchong.entity.gift.virItem;

import java.util.Date;

public class VirtualItemCate {
    private Integer id;

    private String name;

    private Byte status;

    private Integer sort;

    private Date created;

    private Date updated;

    public VirtualItemCate(Integer id, String name, Byte status, Integer sort, Date created, Date updated) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.sort = sort;
        this.created = created;
        this.updated = updated;
    }

    public VirtualItemCate() {
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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
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