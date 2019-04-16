package com.chouchong.entity.iwant.withdraw;

import java.math.BigDecimal;
import java.util.Date;

public class WalletRecord {
    private Integer id;

    private Integer userId;

    private String explain;

    private BigDecimal amount;

    private Integer targetId;

    private Byte type;

    private Date updated;

    private Date created;

    public WalletRecord(Integer id, Integer userId, String explain, BigDecimal amount, Integer targetId, Byte type, Date updated, Date created) {
        this.id = id;
        this.userId = userId;
        this.explain = explain;
        this.amount = amount;
        this.targetId = targetId;
        this.type = type;
        this.updated = updated;
        this.created = created;
    }

    public WalletRecord() {
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

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain == null ? null : explain.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
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