package com.chouchong.entity.gift.article;

import java.util.Date;

public class ArticleItem {
    private Integer id;

    private Integer articleId;

    private Integer itemId;

    private Date created;

    private Date updated;

    public ArticleItem(Integer id, Integer articleId, Integer itemId, Date created, Date updated) {
        this.id = id;
        this.articleId = articleId;
        this.itemId = itemId;
        this.created = created;
        this.updated = updated;
    }

    public ArticleItem() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
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
