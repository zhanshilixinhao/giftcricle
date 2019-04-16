package com.chouchong.entity.webUser;

import java.math.BigDecimal;
import java.util.Date;

public class Discounting {
    private Integer id;

    private Integer userId;

    private Long bpId;

    private BigDecimal itemPrice;

    private BigDecimal discountPrice;

    private String explain;

    private Byte status;

    private Byte type;

    private Date created;

    private Date updated;

    public Discounting(Integer id, Integer userId, Long bpId, BigDecimal itemPrice, BigDecimal discountPrice, String explain, Byte status, Byte type, Date created, Date updated) {
        this.id = id;
        this.userId = userId;
        this.bpId = bpId;
        this.itemPrice = itemPrice;
        this.discountPrice = discountPrice;
        this.explain = explain;
        this.status = status;
        this.type = type;
        this.created = created;
        this.updated = updated;
    }

    public Discounting() {
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

    public Long getBpId() {
        return bpId;
    }

    public void setBpId(Long bpId) {
        this.bpId = bpId;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain == null ? null : explain.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
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