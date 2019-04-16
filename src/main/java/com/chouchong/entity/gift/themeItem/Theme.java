package com.chouchong.entity.gift.themeItem;

import java.util.Date;

public class Theme {
    private Integer id;

    private String name;

    private String cover;

    private Integer sort;

    private Byte status;

    private Date created;

    private Date updated;

    public Theme(Integer id, String name, String cover, Integer sort, Byte status, Date created, Date updated) {
        this.id = id;
        this.name = name;
        this.cover = cover;
        this.sort = sort;
        this.status = status;
        this.created = created;
        this.updated = updated;
    }

    public Theme() {
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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover == null ? null : cover.trim();
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