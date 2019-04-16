package com.chouchong.entity.webUser;

import java.math.BigDecimal;
import java.util.Date;

public class ChargeOrder {
    private Integer id;

    private Integer userId;

    private BigDecimal amount;

    private Long orderNo;

    private Byte status;

    private Byte payWay;

    private Date created;

    private Date updated;

    public ChargeOrder(Integer id, Integer userId, BigDecimal amount, Long orderNo, Byte status, Byte payWay, Date created, Date updated) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.orderNo = orderNo;
        this.status = status;
        this.payWay = payWay;
        this.created = created;
        this.updated = updated;
    }

    public ChargeOrder() {
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getPayWay() {
        return payWay;
    }

    public void setPayWay(Byte payWay) {
        this.payWay = payWay;
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