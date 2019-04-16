package com.chouchong.entity.iwant.appUser;

import java.util.Date;

public class GiftPreDict {
    private Integer id;

    private String text;

    private Byte type;

    private Date created;

    private Date updated;

    public GiftPreDict(Integer id, String text, Byte type, Date created, Date updated) {
        this.id = id;
        this.text = text;
        this.type = type;
        this.created = created;
        this.updated = updated;
    }

    public GiftPreDict() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text == null ? null : text.trim();
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
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