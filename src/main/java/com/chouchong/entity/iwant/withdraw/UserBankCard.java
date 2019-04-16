package com.chouchong.entity.iwant.withdraw;

import java.util.Date;

public class UserBankCard {
    private Integer id;

    private Integer userId;

    private Integer bankId;

    private String depositBank;

    private String cardHolder;

    private String cardNo;

    private Byte status;

    private Date created;

    private Date updated;

    public UserBankCard(Integer id, Integer userId, Integer bankId, String depositBank, String cardHolder, String cardNo, Byte status, Date created, Date updated) {
        this.id = id;
        this.userId = userId;
        this.bankId = bankId;
        this.depositBank = depositBank;
        this.cardHolder = cardHolder;
        this.cardNo = cardNo;
        this.status = status;
        this.created = created;
        this.updated = updated;
    }

    public UserBankCard() {
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

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public String getDepositBank() {
        return depositBank;
    }

    public void setDepositBank(String depositBank) {
        this.depositBank = depositBank == null ? null : depositBank.trim();
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder == null ? null : cardHolder.trim();
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo == null ? null : cardNo.trim();
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