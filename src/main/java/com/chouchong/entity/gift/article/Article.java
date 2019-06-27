package com.chouchong.entity.gift.article;

import java.util.Date;

public class Article {
    private Integer id;

    private String title;

    private String cover;

    private String summary;

    private Integer adminId;

    private Byte status;

    private Integer sort;

    private Date created;

    private Date updated;

    private String detail;

    private Byte type;

    private Date showTime;
    private Integer sceneId;

    private Integer labelId;
    private Integer festivalId;

    public Article(Integer id, String title, String cover, String summary, Integer adminId, Byte status, Integer sort, Date created, Date updated) {
        this.id = id;
        this.title = title;
        this.cover = cover;
        this.summary = summary;
        this.adminId = adminId;
        this.status = status;
        this.sort = sort;
        this.created = created;
        this.updated = updated;
    }

    public Article(Integer id, String title, String cover, String summary, Integer adminId, Byte status, Integer sort, Date created, Date updated, String detail) {
        this.id = id;
        this.title = title;
        this.cover = cover;
        this.summary = summary;
        this.adminId = adminId;
        this.status = status;
        this.sort = sort;
        this.created = created;
        this.updated = updated;
        this.detail = detail;
    }


    public Article() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover == null ? null : cover.trim();
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Date getShowTime() {
        return showTime;
    }

    public void setShowTime(Date showTime) {
        this.showTime = showTime;
    }

    public Integer getSceneId() {
        return sceneId;
    }

    public void setSceneId(Integer sceneId) {
        this.sceneId = sceneId;
    }

    public Integer getLabelId() {
        return labelId;
    }

    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
    }

    public Integer getFestivalId() {
        return festivalId;
    }

    public void setFestivalId(Integer festivalId) {
        this.festivalId = festivalId;
    }
}
