package com.chouchong.entity.webUser;

import java.math.BigDecimal;
import java.util.Date;

public class ConsignmentOrder {
    private Integer id;

    private Long orderNo;

    private Integer userId;

    private Integer sellUserId;

    private Integer consignmentId;

    private Integer quantity;

    private BigDecimal price;

    private Byte status;

    private Date updated;

    private Date created;

    public ConsignmentOrder(Integer id, Long orderNo, Integer userId, Integer sellUserId, Integer consignmentId, Integer quantity, BigDecimal price, Byte status, Date updated, Date created) {
        this.id = id;
        this.orderNo = orderNo;
        this.userId = userId;
        this.sellUserId = sellUserId;
        this.consignmentId = consignmentId;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
        this.updated = updated;
        this.created = created;
    }

    public ConsignmentOrder() {
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

    public Integer getSellUserId() {
        return sellUserId;
    }

    public void setSellUserId(Integer sellUserId) {
        this.sellUserId = sellUserId;
    }

    public Integer getConsignmentId() {
        return consignmentId;
    }

    public void setConsignmentId(Integer consignmentId) {
        this.consignmentId = consignmentId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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