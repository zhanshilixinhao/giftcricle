package com.chouchong.entity.webUser;

import java.math.BigDecimal;
import java.util.Date;

public class ItemOrder {
    private Integer id;

    private Integer userId;

    private Integer storeId;

    private Long orderNo;

    private BigDecimal totalPrice;

    private Integer quantity;

    private Byte status;

    private Date created;

    private Date updated;

    private Integer adminId;

    public ItemOrder(Integer id, Integer userId, Integer storeId, Long orderNo, BigDecimal totalPrice, Integer quantity, Byte status, Date created, Date updated) {
        this.id = id;
        this.userId = userId;
        this.storeId = storeId;
        this.orderNo = orderNo;
        this.totalPrice = totalPrice;
        this.quantity = quantity;
        this.status = status;
        this.created = created;
        this.updated = updated;
    }

    public ItemOrder() {
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

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
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

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }
}
