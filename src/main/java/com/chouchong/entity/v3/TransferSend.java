package com.chouchong.entity.v3;

import java.math.BigDecimal;
import java.util.Date;

public class TransferSend {
    private Integer id;

    private Integer userId;

    private Integer membershipCardId;

    private BigDecimal sendMoney;

    private Byte status;

    private Date updated;

    private Date created;

    public TransferSend(Integer id, Integer userId, Integer membershipCardId, BigDecimal sendMoney, Byte status, Date updated, Date created) {
        this.id = id;
        this.userId = userId;
        this.membershipCardId = membershipCardId;
        this.sendMoney = sendMoney;
        this.status = status;
        this.updated = updated;
        this.created = created;
    }

    public TransferSend() {
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

    public Integer getMembershipCardId() {
        return membershipCardId;
    }

    public void setMembershipCardId(Integer membershipCardId) {
        this.membershipCardId = membershipCardId;
    }

    public BigDecimal getSendMoney() {
        return sendMoney;
    }

    public void setSendMoney(BigDecimal sendMoney) {
        this.sendMoney = sendMoney;
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