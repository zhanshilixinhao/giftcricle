package com.chouchong.entity.webUser;

import java.math.BigDecimal;
import java.util.Date;

public class ReceiveItemOrder {
    private Integer id;

    private Integer userId;

    private Integer itemId;

    private Long bpId;

    private Integer skuId;

    private Long orderNo;

    private String title;

    private String description;

    private String cover;

    private BigDecimal price;

    private BigDecimal totalPrice;

    private Integer quantity;

    private String receiveInfo;

    private String logisticsInfo;

    private Byte status;

    private Date created;

    private Date updated;

    public ReceiveItemOrder(Integer id, Integer userId, Integer itemId, Long bpId, Integer skuId, Long orderNo, String title, String description, String cover, BigDecimal price, BigDecimal totalPrice, Integer quantity, String receiveInfo, String logisticsInfo, Byte status, Date created, Date updated) {
        this.id = id;
        this.userId = userId;
        this.itemId = itemId;
        this.bpId = bpId;
        this.skuId = skuId;
        this.orderNo = orderNo;
        this.title = title;
        this.description = description;
        this.cover = cover;
        this.price = price;
        this.totalPrice = totalPrice;
        this.quantity = quantity;
        this.receiveInfo = receiveInfo;
        this.logisticsInfo = logisticsInfo;
        this.status = status;
        this.created = created;
        this.updated = updated;
    }

    public ReceiveItemOrder() {
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

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Long getBpId() {
        return bpId;
    }

    public void setBpId(Long bpId) {
        this.bpId = bpId;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover == null ? null : cover.trim();
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getReceiveInfo() {
        return receiveInfo;
    }

    public void setReceiveInfo(String receiveInfo) {
        this.receiveInfo = receiveInfo == null ? null : receiveInfo.trim();
    }

    public String getLogisticsInfo() {
        return logisticsInfo;
    }

    public void setLogisticsInfo(String logisticsInfo) {
        this.logisticsInfo = logisticsInfo == null ? null : logisticsInfo.trim();
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