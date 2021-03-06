package com.chouchong.entity.gift.coupon;

import java.util.Date;

public class CouponUseRecord {
    private Integer id;

    private Integer merchantId;

    private Integer adminId;

    private Integer couponId;

    private Integer userId;

    private String title;

    private String cover;

    private Long bpId;

    private Date updated;

    private Date created;

    public CouponUseRecord(Integer id, Integer merchantId, Integer adminId, Integer couponId, Integer userId, String title, String cover, Long bpId, Date updated, Date created) {
        this.id = id;
        this.merchantId = merchantId;
        this.adminId = adminId;
        this.couponId = couponId;
        this.userId = userId;
        this.title = title;
        this.cover = cover;
        this.bpId = bpId;
        this.updated = updated;
        this.created = created;
    }

    public CouponUseRecord() {
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

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public Long getBpId() {
        return bpId;
    }

    public void setBpId(Long bpId) {
        this.bpId = bpId;
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
