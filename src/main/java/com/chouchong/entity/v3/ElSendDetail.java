package com.chouchong.entity.v3;

import java.util.Date;

public class ElSendDetail {
    private Integer id;

    private Integer userId;

    private Integer couponSendId;

    private Long num;

    private Integer quantity;

    private Byte status;

    private Date updated;

    private Date created;

    public ElSendDetail(Integer id, Integer userId, Integer couponSendId, Long num, Integer quantity, Byte status, Date updated, Date created) {
        this.id = id;
        this.userId = userId;
        this.couponSendId = couponSendId;
        this.num = num;
        this.quantity = quantity;
        this.status = status;
        this.updated = updated;
        this.created = created;
    }

    public ElSendDetail() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCouponSendId() {
        return couponSendId;
    }

    public void setCouponSendId(Integer couponSendId) {
        this.couponSendId = couponSendId;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
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