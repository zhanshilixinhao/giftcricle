package com.chouchong.entity.v3;

import java.math.BigDecimal;
import java.util.Date;

public class CardRebate {
    private Integer id;

    private Integer userId;

    private Integer membershipCardId;

    private String explain;

    private Byte status;

    private Integer recordId;

    private BigDecimal money;

    private Long orderNo;

    private Integer adminId;

    private Date updated;

    private Date created;

    private Byte type;

    public CardRebate(Integer id, Integer userId, Integer membershipCardId, String explain, Byte status, Integer recordId, BigDecimal money, Long orderNo, Integer adminId, Date updated, Date created) {
        this.id = id;
        this.userId = userId;
        this.membershipCardId = membershipCardId;
        this.explain = explain;
        this.status = status;
        this.recordId = recordId;
        this.money = money;
        this.orderNo = orderNo;
        this.adminId = adminId;
        this.updated = updated;
        this.created = created;
    }

    public CardRebate() {
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

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain == null ? null : explain.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
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
