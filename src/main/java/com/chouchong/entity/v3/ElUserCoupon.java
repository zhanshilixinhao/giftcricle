package com.chouchong.entity.v3;

import java.util.Date;

public class ElUserCoupon {
    private Long id;

    private Integer couponId;

    private Integer userId;

    private Integer totalQuantity;

    private Integer quantity;

    private Byte status;

    private String code;

    private Integer storeId;

    private Integer adminId;

    private Date updated;

    private Date created;

    public ElUserCoupon(Long id, Integer couponId, Integer userId, Integer totalQuantity, Integer quantity, Byte status, String code, Integer storeId, Integer adminId, Date updated, Date created) {
        this.id = id;
        this.couponId = couponId;
        this.userId = userId;
        this.totalQuantity = totalQuantity;
        this.quantity = quantity;
        this.status = status;
        this.code = code;
        this.storeId = storeId;
        this.adminId = adminId;
        this.updated = updated;
        this.created = created;
    }

    public ElUserCoupon() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
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