package com.chouchong.entity.v3;

import java.math.BigDecimal;
import java.util.Date;

public class StoreMemberCharge {
    private Integer id;

    private Integer userId;

    private Integer merchantId;

    private Integer storeId;

    private BigDecimal rechargeMoney;

    private BigDecimal sendMoney;

    private BigDecimal expenseMoney;

    private Byte type;

    private String explain;

    private Date updated;

    private Date created;

    private BigDecimal totalMoney;

    private Float scale;

    public StoreMemberCharge(Integer id, Integer userId, Integer merchantId, Integer storeId, BigDecimal rechargeMoney, BigDecimal sendMoney, BigDecimal expenseMoney, Byte type, String explain, Date updated, Date created, BigDecimal totalMoney, Float scale) {
        this.id = id;
        this.userId = userId;
        this.merchantId = merchantId;
        this.storeId = storeId;
        this.rechargeMoney = rechargeMoney;
        this.sendMoney = sendMoney;
        this.expenseMoney = expenseMoney;
        this.type = type;
        this.explain = explain;
        this.updated = updated;
        this.created = created;
        this.totalMoney = totalMoney;
        this.scale = scale;
    }

    public StoreMemberCharge() {
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

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
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

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Float getScale() {
        return scale;
    }

    public void setScale(Float scale) {
        this.scale = scale;
    }
}
