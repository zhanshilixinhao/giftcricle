package com.chouchong.entity.home;

import java.util.Date;

public class WelfareRecord {
    private Integer id;

    private Integer welfareId;

    private Integer userId;

    private Date created;

    private Date updated;

    public WelfareRecord(Integer id, Integer welfareId, Integer userId, Date created, Date updated) {
        this.id = id;
        this.welfareId = welfareId;
        this.userId = userId;
        this.created = created;
        this.updated = updated;
    }

    public WelfareRecord() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWelfareId() {
        return welfareId;
    }

    public void setWelfareId(Integer welfareId) {
        this.welfareId = welfareId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
