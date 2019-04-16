package com.chouchong.entity.iwant.withdraw;

import java.math.BigDecimal;
import java.util.Date;

public class UserWithdraw {
    private Integer id;

    private Integer userId;

    private BigDecimal amount;

    private Integer userBankcardId;

    private Byte status;

    private String describe;

    private Date created;

    private Date updated;

    public UserWithdraw(Integer id, Integer userId, BigDecimal amount, Integer userBankcardId, Byte status, String describe, Date created, Date updated) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.userBankcardId = userBankcardId;
        this.status = status;
        this.describe = describe;
        this.created = created;
        this.updated = updated;
    }

    public UserWithdraw() {
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

    public Integer getUserBankcardId() {
        return userBankcardId;
    }

    public void setUserBankcardId(Integer userBankcardId) {
        this.userBankcardId = userBankcardId;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe == null ? null : describe.trim();
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