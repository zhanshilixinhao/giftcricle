package com.chouchong.entity.v3;

import java.math.BigDecimal;
import java.util.Date;

public class MemberExpenseRecord {
    private Integer id;

    private Integer membershipCardId;

    private Integer userId;

    private BigDecimal expenseMoney;

    private Byte type;

    private Integer storeId;

    private String targetId;

    private Integer adminId;

    private String explain;

    private Date updated;

    private Date created;

    public MemberExpenseRecord(Integer id, Integer membershipCardId, Integer userId, BigDecimal expenseMoney, Byte type, Integer storeId, String targetId, Integer adminId, String explain, Date updated, Date created) {
        this.id = id;
        this.membershipCardId = membershipCardId;
        this.userId = userId;
        this.expenseMoney = expenseMoney;
        this.type = type;
        this.storeId = storeId;
        this.targetId = targetId;
        this.adminId = adminId;
        this.explain = explain;
        this.updated = updated;
        this.created = created;
    }

    public MemberExpenseRecord() {
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

    public BigDecimal getExpenseMoney() {
        return expenseMoney;
    }

    public void setExpenseMoney(BigDecimal expenseMoney) {
        this.expenseMoney = expenseMoney;
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

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
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
