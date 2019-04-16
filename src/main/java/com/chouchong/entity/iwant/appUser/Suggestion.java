package com.chouchong.entity.iwant.appUser;

import java.util.Date;

public class Suggestion {
    private Integer id;

    private Integer userId;

    private Byte type;

    private String feedback;

    private String contactWay;

    private Date created;

    private Date updated;

    public Suggestion(Integer id, Integer userId, Byte type, String feedback, String contactWay, Date created, Date updated) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.feedback = feedback;
        this.contactWay = contactWay;
        this.created = created;
        this.updated = updated;
    }

    public Suggestion() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback == null ? null : feedback.trim();
    }

    public String getContactWay() {
        return contactWay;
    }

    public void setContactWay(String contactWay) {
        this.contactWay = contactWay == null ? null : contactWay.trim();
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