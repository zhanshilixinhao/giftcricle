package com.chouchong.entity.webUser;

import java.math.BigDecimal;
import java.util.Date;

public class SysAdminWallet {
    private Integer id;

    private Integer adminId;

    private BigDecimal totalAmount;

    private Date created;

    private Date updated;

    public SysAdminWallet(Integer id, Integer adminId, BigDecimal totalAmount, Date created, Date updated) {
        this.id = id;
        this.adminId = adminId;
        this.totalAmount = totalAmount;
        this.created = created;
        this.updated = updated;
    }

    public SysAdminWallet() {
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

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
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