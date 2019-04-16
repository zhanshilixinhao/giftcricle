package com.chouchong.entity.gift.virItem;

import java.util.Date;

public class VirtualItemBrand {
    private Integer id;

    private String name;

    private Byte status;

    private Date updated;

    private Date created;

    public VirtualItemBrand(Integer id, String name, Byte status, Date updated, Date created) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.updated = updated;
        this.created = created;
    }

    public VirtualItemBrand() {
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

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}