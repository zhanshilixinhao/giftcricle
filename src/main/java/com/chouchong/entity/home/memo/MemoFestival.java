package com.chouchong.entity.home.memo;


import java.util.Date;

public class MemoFestival {
    private Integer id;

    private Date targetTime;

    private String name;

    private String summary;

    private String cover;

    private String picture;

    private String title;

    private String detail;

    private Date created;

    private Date updated;

    public MemoFestival(Integer id, Date targetTime, String name, String summary, String cover, String picture, String title, String detail, Date created, Date updated) {
        this.id = id;
        this.targetTime = targetTime;
        this.name = name;
        this.summary = summary;
        this.cover = cover;
        this.picture = picture;
        this.title = title;
        this.detail = detail;
        this.created = created;
        this.updated = updated;
    }

    public MemoFestival() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTargetTime() {
        return targetTime;
    }

    public void setTargetTime(Date targetTime) {
        this.targetTime = targetTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover == null ? null : cover.trim();
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture == null ? null : picture.trim();
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
