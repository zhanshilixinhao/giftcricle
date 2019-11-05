package com.chouchong.entity.v3;

import java.math.BigDecimal;
import java.util.Date;

public class MemberChargeOrder {
    private Long orderNo;

    private Integer membershipCardId;

    private Integer userId;

    private BigDecimal rechargeMoney;

    private BigDecimal sendMoney;

    private Byte status;

    private Byte payWay;

    private Date updated;

    private Date created;

    public MemberChargeOrder(Long orderNo, Integer membershipCardId, Integer userId, BigDecimal rechargeMoney, BigDecimal sendMoney, Byte status, Byte payWay, Date updated, Date created) {
        this.orderNo = orderNo;
        this.membershipCardId = membershipCardId;
        this.userId = userId;
        this.rechargeMoney = rechargeMoney;
        this.sendMoney = sendMoney;
        this.status = status;
        this.payWay = payWay;
        this.updated = updated;
        this.created = created;
    }

    public MemberChargeOrder() {
        super();
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getMembershipCardId() {
        return membershipCardId;
    }

    public void setMembershipCardId(Integer membershipCardId) {
        this.membershipCardId = membershipCardId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getRechargeMoney() {
        return rechargeMoney;
    }

    public void setRechargeMoney(BigDecimal rechargeMoney) {
        this.rechargeMoney = rechargeMoney;
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

    public Byte getPayWay() {
        return payWay;
    }

    public void setPayWay(Byte payWay) {
        this.payWay = payWay;
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
