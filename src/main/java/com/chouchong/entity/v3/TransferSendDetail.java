package com.chouchong.entity.v3;

import java.math.BigDecimal;
import java.util.Date;

public class TransferSendDetail {
    private Integer id;

    private Integer userId;

    private Integer transferSendId;

    private Integer membershipCardId;

    private BigDecimal sendMoney;

    private Byte status;

    private Date updated;

    private Date created;

    public TransferSendDetail(Integer id, Integer userId, Integer transferSendId, Integer membershipCardId, BigDecimal sendMoney, Byte status, Date updated, Date created) {
        this.id = id;
        this.userId = userId;
        this.transferSendId = transferSendId;
        this.membershipCardId = membershipCardId;
        this.sendMoney = sendMoney;
        this.status = status;
        this.updated = updated;
        this.created = created;
    }

    public TransferSendDetail() {
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

    public Integer getTransferSendId() {
        return transferSendId;
    }

    public void setTransferSendId(Integer transferSendId) {
        this.transferSendId = transferSendId;
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