package com.chouchong.entity.gift.article;

import java.util.Date;

public class ArticleFestival {
    private Integer id;

    private String title;

    private Date created;

    private Date updated;

    public ArticleFestival(Integer id, String title, Date created, Date updated) {
        this.id = id;
        this.title = title;
        this.created = created;
        this.updated = updated;
    }

    public ArticleFestival() {
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
