package com.chouchong.entity.v3;

import java.util.Date;

public class CardGrade {
    private Integer id;

    private String title;

    private String summary;

    private Integer grade;

    private Integer adminId;

    private Date updated;

    private Date created;

    public CardGrade(Integer id, String title, String summary, Integer grade, Integer adminId, Date updated, Date created) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.grade = grade;
        this.adminId = adminId;
        this.updated = updated;
        this.created = created;
    }

    public CardGrade() {
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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
