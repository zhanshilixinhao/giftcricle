package com.chouchong.entity.v3;

import java.math.BigDecimal;
import java.util.Date;

public class StoreMemberEvent {
    private Integer id;

    private Integer userId;

    private Integer membershipCardId;

    private Integer storeId;

    private Integer memberEventId;

    private Long orderNo;

    private BigDecimal capitalMoney;

    private BigDecimal sendMoney;

    private BigDecimal totalMoney;

    private String explain;

    private Float scale;

    private Byte type;

    private BigDecimal capitalBalance;

    private Byte capitalStatus;

    private BigDecimal sendBalance;

    private Byte sendStatus;

    private Date updated;

    private Date created;

    public StoreMemberEvent(Integer id, Integer userId, Integer membershipCardId, Integer storeId, Integer memberEventId, Long orderNo, BigDecimal capitalMoney, BigDecimal sendMoney, BigDecimal totalMoney, String explain, Float scale, Byte type, BigDecimal capitalBalance, Byte capitalStatus, BigDecimal sendBalance, Byte sendStatus, Date updated, Date created) {
        this.id = id;
        this.userId = userId;
        this.membershipCardId = membershipCardId;
        this.storeId = storeId;
        this.memberEventId = memberEventId;
        this.orderNo = orderNo;
        this.capitalMoney = capitalMoney;
        this.sendMoney = sendMoney;
        this.totalMoney = totalMoney;
        this.explain = explain;
        this.scale = scale;
        this.type = type;
        this.capitalBalance = capitalBalance;
        this.capitalStatus = capitalStatus;
        this.sendBalance = sendBalance;
        this.sendStatus = sendStatus;
        this.updated = updated;
        this.created = created;
    }

    public StoreMemberEvent() {
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

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getMemberEventId() {
        return memberEventId;
    }

    public void setMemberEventId(Integer memberEventId) {
        this.memberEventId = memberEventId;
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public BigDecimal getCapitalMoney() {
        return capitalMoney;
    }

    public void setCapitalMoney(BigDecimal capitalMoney) {
        this.capitalMoney = capitalMoney;
    }

    public BigDecimal getSendMoney() {
        return sendMoney;
    }

    public void setSendMoney(BigDecimal sendMoney) {
        this.sendMoney = sendMoney;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain == null ? null : explain.trim();
    }

    public Float getScale() {
        return scale;
    }

    public void setScale(Float scale) {
        this.scale = scale;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public BigDecimal getCapitalBalance() {
        return capitalBalance;
    }

    public void setCapitalBalance(BigDecimal capitalBalance) {
        this.capitalBalance = capitalBalance;
    }

    public Byte getCapitalStatus() {
        return capitalStatus;
    }

    public void setCapitalStatus(Byte capitalStatus) {
        this.capitalStatus = capitalStatus;
    }

    public BigDecimal getSendBalance() {
        return sendBalance;
    }

    public void setSendBalance(BigDecimal sendBalance) {
        this.sendBalance = sendBalance;
    }

    public Byte getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(Byte sendStatus) {
        this.sendStatus = sendStatus;
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
