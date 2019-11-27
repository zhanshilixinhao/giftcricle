package com.chouchong.entity.v3;

import java.math.BigDecimal;
import java.util.Date;

public class MemberChargeRecord {
    private Integer id;

    private Integer membershipCardId;

    private Integer userId;

    private Integer memberEventId;

    private BigDecimal rechargeMoney;

    private BigDecimal sendMoney;

    private Byte type;

    private Integer storeId;

    private Integer adminId;

    private String explain;

    private Date updated;

    private Date created;

    private Long orderNo;

    private BigDecimal beforeMoney;

    public MemberChargeRecord(Integer id, Integer membershipCardId, Integer userId, Integer memberEventId, BigDecimal rechargeMoney, BigDecimal sendMoney, Byte type, Integer storeId, Integer adminId, String explain, Date updated, Date created,Long orderNo) {
        this.id = id;
        this.membershipCardId = membershipCardId;
        this.userId = userId;
        this.memberEventId = memberEventId;
        this.rechargeMoney = rechargeMoney;
        this.sendMoney = sendMoney;
        this.type = type;
        this.storeId = storeId;
        this.adminId = adminId;
        this.explain = explain;
        this.updated = updated;
        this.created = created;
        this.orderNo = orderNo;
    }

    public BigDecimal getBeforeMoney() {
        return beforeMoney;
    }

    public void setBeforeMoney(BigDecimal beforeMoney) {
        this.beforeMoney = beforeMoney;
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public MemberChargeRecord() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getMemberEventId() {
        return memberEventId;
    }

    public void setMemberEventId(Integer memberEventId) {
        this.memberEventId = memberEventId;
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

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain == null ? null : explain.trim();
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
