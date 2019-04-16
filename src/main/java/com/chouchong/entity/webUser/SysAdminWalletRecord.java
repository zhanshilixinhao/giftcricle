package com.chouchong.entity.webUser;

import java.math.BigDecimal;
import java.util.Date;

public class SysAdminWalletRecord {
    private Integer id;

    private Integer adminId;

    private String explain;

    private BigDecimal amount;

    private Long targetId;

    private Byte type;

    private Date updated;

    private Date created;

    public SysAdminWalletRecord(Integer id, Integer adminId, String explain, BigDecimal amount, Long targetId, Byte type, Date updated, Date created) {
        this.id = id;
        this.adminId = adminId;
        this.explain = explain;
        this.amount = amount;
        this.targetId = targetId;
        this.type = type;
        this.updated = updated;
        this.created = created;
    }

    public SysAdminWalletRecord() {
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

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain == null ? null : explain.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
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