package com.chouchong.entity.webUser;

import java.math.BigDecimal;
import java.util.Date;

public class SysAdminWithdraw {
    private Integer id;

    private Integer adminId;

    private BigDecimal amount;

    private String bankName;

    private String depositBank;

    private String cardHolder;

    private String cardNo;

    private Byte status;

    private String describe;

    private Date created;

    private Date updated;

    public SysAdminWithdraw(Integer id, Integer adminId, BigDecimal amount, String bankName, String depositBank, String cardHolder, String cardNo, Byte status, String describe, Date created, Date updated) {
        this.id = id;
        this.adminId = adminId;
        this.amount = amount;
        this.bankName = bankName;
        this.depositBank = depositBank;
        this.cardHolder = cardHolder;
        this.cardNo = cardNo;
        this.status = status;
        this.describe = describe;
        this.created = created;
        this.updated = updated;
    }

    public SysAdminWithdraw() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
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