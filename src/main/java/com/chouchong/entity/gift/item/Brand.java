package com.chouchong.entity.gift.item;

import java.util.Date;

public class Brand {
    private Integer id;

    private String name;

    private String logo;

    private Byte status;

    private Date created;

    private Date updated;

    public Brand(Integer id, String name, String logo, Byte status, Date created, Date updated) {
        this.id = id;
        this.name = name;
        this.logo = logo;
        this.status = status;
        this.created = created;
        this.updated = updated;
    }

    public Brand() {
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
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