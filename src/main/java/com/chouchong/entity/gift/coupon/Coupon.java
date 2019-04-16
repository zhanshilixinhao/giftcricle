package com.chouchong.entity.gift.coupon;

import java.util.Date;

public class Coupon {
    private Integer id;

    private Integer merchantId;

    private Integer adminId;

    private Integer partnerId;

    private String title;

    private String brandName;

    private String content;

    private String cover;

    private Byte status;

    private Date starting;

    private Date ending;

    private Date updated;

    private Date created;

    public Coupon(Integer id, Integer merchantId, Integer adminId, Integer partnerId, String title, String brandName, String content, String cover, Byte status, Date starting, Date ending, Date updated, Date created) {
        this.id = id;
        this.merchantId = merchantId;
        this.adminId = adminId;
        this.partnerId = partnerId;
        this.title = title;
        this.brandName = brandName;
        this.content = content;
        this.cover = cover;
        this.status = status;
        this.starting = starting;
        this.ending = ending;
        this.updated = updated;
        this.created = created;
    }

    public Coupon() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public Integer getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Integer partnerId) {
        this.partnerId = partnerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName == null ? null : brandName.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover == null ? null : cover.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getStarting() {
        return starting;
    }

    public void setStarting(Date starting) {
        this.starting = starting;
    }

    public Date getEnding() {
        return ending;
    }

    public void setEnding(Date ending) {
        this.ending = ending;
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