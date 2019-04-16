package com.chouchong.entity.webUser;

import java.math.BigDecimal;
import java.util.Date;

public class VirtualItemOrder {
    private Integer id;

    private Long orderNo;

    private Integer userId;

    private Integer virtualItemId;

    private Integer quantity;

    private String name;

    private String cover;

    private String summary;

    private BigDecimal price;

    private BigDecimal totalPrice;

    private Byte status;

    private Date created;

    private Date updated;

    public VirtualItemOrder(Integer id, Long orderNo, Integer userId, Integer virtualItemId, Integer quantity, String name, String cover, String summary, BigDecimal price, BigDecimal totalPrice, Byte status, Date created, Date updated) {
        this.id = id;
        this.orderNo = orderNo;
        this.userId = userId;
        this.virtualItemId = virtualItemId;
        this.quantity = quantity;
        this.name = name;
        this.cover = cover;
        this.summary = summary;
        this.price = price;
        this.totalPrice = totalPrice;
        this.status = status;
        this.created = created;
        this.updated = updated;
    }

    public VirtualItemOrder() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getVirtualItemId() {
        return virtualItemId;
    }

    public void setVirtualItemId(Integer virtualItemId) {
        this.virtualItemId = virtualItemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover == null ? null : cover.trim();
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
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
}