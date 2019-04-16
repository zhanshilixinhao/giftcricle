package com.chouchong.entity.iwant.withdraw;

import java.math.BigDecimal;
import java.util.Date;

public class Wallet {
    private Integer userId;

    private BigDecimal balance;

    private BigDecimal totalAmount;

    private BigDecimal consumeAmount;

    private Date created;

    private Date updated;

    public Wallet(Integer userId, BigDecimal balance, BigDecimal totalAmount, BigDecimal consumeAmount, Date created, Date updated) {
        this.userId = userId;
        this.balance = balance;
        this.totalAmount = totalAmount;
        this.consumeAmount = consumeAmount;
        this.created = created;
        this.updated = updated;
    }

    public Wallet() {
        super();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getConsumeAmount() {
        return consumeAmount;
    }

    public void setConsumeAmount(BigDecimal consumeAmount) {
        this.consumeAmount = consumeAmount;
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