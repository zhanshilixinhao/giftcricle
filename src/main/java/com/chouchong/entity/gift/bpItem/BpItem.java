package com.chouchong.entity.gift.bpItem;

import java.math.BigDecimal;
import java.util.Date;

public class BpItem {
    private Long id;

    private Integer userId;

    private Integer targetId;

    private Integer quantity;

    private BigDecimal price;

    private String from;

    private Byte type;

    private Date created;

    private Date updated;

    public BpItem(Long id, Integer userId, Integer targetId, Integer quantity, BigDecimal price, String from, Byte type, Date created, Date updated) {
        this.id = id;
        this.userId = userId;
        this.targetId = targetId;
        this.quantity = quantity;
        this.price = price;
        this.from = from;
        this.type = type;
        this.created = created;
        this.updated = updated;
    }

    public BpItem() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
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

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from == null ? null : from.trim();
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