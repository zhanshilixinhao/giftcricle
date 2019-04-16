package com.chouchong.entity.iwant.appUser;

import java.util.Date;

public class UserTagDict {
    private Integer id;

    private String tag;

    private Byte type;

    private Byte status;

    private Date created;

    private Date updated;

    public UserTagDict(Integer id, String tag, Byte type, Byte status, Date created, Date updated) {
        this.id = id;
        this.tag = tag;
        this.type = type;
        this.status = status;
        this.created = created;
        this.updated = updated;
    }

    public UserTagDict() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
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