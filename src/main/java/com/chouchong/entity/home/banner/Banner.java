package com.chouchong.entity.home.banner;

import java.util.Date;

public class Banner {
    private Integer id;

    private String targetId;

    private String cover;

    private String requestUrl;

    private String title;

    private Byte type;

    private Byte status;

    private Date created;

    private Date updated;

    private String richText;

    public Banner(Integer id, String targetId, String cover, String requestUrl, String title, Byte type, Byte status, Date created, Date updated) {
        this.id = id;
        this.targetId = targetId;
        this.cover = cover;
        this.requestUrl = requestUrl;
        this.title = title;
        this.type = type;
        this.status = status;
        this.created = created;
        this.updated = updated;
    }

    public Banner(Integer id, String targetId, String cover, String requestUrl, String title, Byte type, Byte status, Date created, Date updated, String richText) {
        this.id = id;
        this.targetId = targetId;
        this.cover = cover;
        this.requestUrl = requestUrl;
        this.title = title;
        this.type = type;
        this.status = status;
        this.created = created;
        this.updated = updated;
        this.richText = richText;
    }

    public Banner() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId == null ? null : targetId.trim();
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover == null ? null : cover.trim();
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl == null ? null : requestUrl.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
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

    public String getRichText() {
        return richText;
    }

    public void setRichText(String richText) {
        this.richText = richText == null ? null : richText.trim();
    }
}