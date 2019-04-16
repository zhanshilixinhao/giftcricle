package com.chouchong.entity.gift.item;

import java.util.Date;

public class ItemCategory {
    private Integer id;

    private Integer pid;

    private String name;

    private Byte status;

    private Integer sort;

    private String icon;

    private Date created;

    private Date updated;

    public ItemCategory(Integer id, Integer pid, String name, Byte status, Integer sort, String icon, Date created, Date updated) {
        this.id = id;
        this.pid = pid;
        this.name = name;
        this.status = status;
        this.sort = sort;
        this.icon = icon;
        this.created = created;
        this.updated = updated;
    }

    public ItemCategory() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
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