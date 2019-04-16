package com.chouchong.entity.iwant.withdraw;

import java.util.Date;

public class District {
    private Integer adcode;

    private Integer pAdcode;

    private String name;

    private Byte type;

    private String level;

    private Date created;

    private Date updated;

    public District(Integer adcode, Integer pAdcode, String name, Byte type, String level, Date created, Date updated) {
        this.adcode = adcode;
        this.pAdcode = pAdcode;
        this.name = name;
        this.type = type;
        this.level = level;
        this.created = created;
        this.updated = updated;
    }

    public District() {
        super();
    }

    public Integer getAdcode() {
        return adcode;
    }

    public void setAdcode(Integer adcode) {
        this.adcode = adcode;
    }

    public Integer getpAdcode() {
        return pAdcode;
    }

    public void setpAdcode(Integer pAdcode) {
        this.pAdcode = pAdcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
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