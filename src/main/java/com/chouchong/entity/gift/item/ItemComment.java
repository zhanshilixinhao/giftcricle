package com.chouchong.entity.gift.item;

import java.util.Date;

public class ItemComment {
    private Integer id;

    private Integer skuId;

    private Integer itemId;

    private Long orderNo;

    private Integer userId;

    private Integer star;

    private String content;

    private String pictures;

    private Date created;

    private Date updated;

    public ItemComment(Integer id, Integer skuId, Integer itemId, Long orderNo, Integer userId, Integer star, String content, String pictures, Date created, Date updated) {
        this.id = id;
        this.skuId = skuId;
        this.itemId = itemId;
        this.orderNo = orderNo;
        this.userId = userId;
        this.star = star;
        this.content = content;
        this.pictures = pictures;
        this.created = created;
        this.updated = updated;
    }

    public ItemComment() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getPictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures == null ? null : pictures.trim();
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