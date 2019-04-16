package com.chouchong.entity.home.message;

import java.util.Date;

public class AppMessageUser {
    private Integer id;

    private Integer userId;

    private Integer appMessageId;

    private Byte isRead;

    private Date created;

    private Date updated;

    public AppMessageUser(Integer id, Integer userId, Integer appMessageId, Byte isRead, Date created, Date updated) {
        this.id = id;
        this.userId = userId;
        this.appMessageId = appMessageId;
        this.isRead = isRead;
        this.created = created;
        this.updated = updated;
    }

    public AppMessageUser() {
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

    public Integer getAppMessageId() {
        return appMessageId;
    }

    public void setAppMessageId(Integer appMessageId) {
        this.appMessageId = appMessageId;
    }

    public Byte getIsRead() {
        return isRead;
    }

    public void setIsRead(Byte isRead) {
        this.isRead = isRead;
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