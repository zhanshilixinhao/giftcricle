package com.chouchong.entity.iwant.appUser;

import java.util.Date;

public class Event {
    private Integer id;

    private Integer userId;

    private String eventName;

    private Date created;

    private Date updated;

    public Event(Integer id, Integer userId, String eventName, Date created, Date updated) {
        this.id = id;
        this.userId = userId;
        this.eventName = eventName;
        this.created = created;
        this.updated = updated;
    }

    public Event() {
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

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName == null ? null : eventName.trim();
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
