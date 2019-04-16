package com.chouchong.entity.home.memo;

import java.util.Date;

public class MemoAffair {
    private Integer id;

    private Integer userId;

    private Date targetTime;

    private String title;

    private String detail;

    private String users;

    private Date created;

    private Date updated;

    private Integer count;

    private Byte isCirculation;

    private Integer eventTypeId;

    public MemoAffair(Integer id, Integer userId, Date targetTime, String title, String detail, String users, Date created, Date updated, Integer count) {
        this.id = id;
        this.userId = userId;
        this.targetTime = targetTime;
        this.title = title;
        this.detail = detail;
        this.users = users;
        this.created = created;
        this.updated = updated;
        this.count = count;
    }

    public MemoAffair() {
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

    public Date getTargetTime() {
        return targetTime;
    }

    public void setTargetTime(Date targetTime) {
        this.targetTime = targetTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users == null ? null : users.trim();
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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Byte getIsCirculation() {
        return isCirculation;
    }

    public void setIsCirculation(Byte isCirculation) {
        this.isCirculation = isCirculation;
    }

    public Integer getEventTypeId() {
        return eventTypeId;
    }

    public void setEventTypeId(Integer eventTypeId) {
        this.eventTypeId = eventTypeId;
    }
}
