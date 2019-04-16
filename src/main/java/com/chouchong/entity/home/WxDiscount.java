package com.chouchong.entity.home;

import java.math.BigDecimal;
import java.util.Date;

public class WxDiscount {
    private Integer id; //微信折现id

    private Integer bankId; //银行id

    private Integer userId; //用户id

    private Long bpId; //背包id

    private String cardNo;

    private Byte type;

    private String depositBank;

    private String cardHolder;

    private String phone;

    private Date created;

    private Date updated;

    private Byte status;

    private BigDecimal amount;

    private BigDecimal price;

    public WxDiscount(Integer id, Integer bankId, Integer userId, Long bpId, String cardNo, Byte type, String depositBank, String cardHolder, String phone, Date created, Date updated, Byte status, BigDecimal amount, BigDecimal price) {
        this.id = id;
        this.bankId = bankId;
        this.userId = userId;
        this.bpId = bpId;
        this.cardNo = cardNo;
        this.type = type;
        this.depositBank = depositBank;
        this.cardHolder = cardHolder;
        this.phone = phone;
        this.created = created;
        this.updated = updated;
        this.status = status;
        this.amount = amount;
        this.price = price;
    }

    public WxDiscount() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getBpId() {
        return bpId;
    }

    public void setBpId(Long bpId) {
        this.bpId = bpId;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo == null ? null : cardNo.trim();
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
