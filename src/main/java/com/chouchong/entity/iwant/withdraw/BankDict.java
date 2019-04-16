package com.chouchong.entity.iwant.withdraw;

import java.util.Date;

public class BankDict {
    private Integer id;

    private String bankName;

    private String logo;

    private Byte status;

    private Date created;

    private Date updated;

    public BankDict(Integer id, String bankName, String logo, Byte status, Date created, Date updated) {
        this.id = id;
        this.bankName = bankName;
        this.logo = logo;
        this.status = status;
        this.created = created;
        this.updated = updated;
    }

    public BankDict() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
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