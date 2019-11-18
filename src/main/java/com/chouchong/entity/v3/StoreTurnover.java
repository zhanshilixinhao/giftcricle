package com.chouchong.entity.v3;

import java.math.BigDecimal;
import java.util.Date;

public class StoreTurnover {
    private Integer id;

    private Integer storeMemberId;

    private BigDecimal blagMoney;

    private BigDecimal turnoverMoney;

    private Integer storeId;

    private Integer blagStoreId;

    private Date updated;

    private Date created;

    public StoreTurnover(Integer id, Integer storeMemberId, BigDecimal blagMoney, BigDecimal turnoverMoney, Integer storeId, Integer blagStoreId, Date updated, Date created) {
        this.id = id;
        this.storeMemberId = storeMemberId;
        this.blagMoney = blagMoney;
        this.turnoverMoney = turnoverMoney;
        this.storeId = storeId;
        this.blagStoreId = blagStoreId;
        this.updated = updated;
        this.created = created;
    }

    public StoreTurnover() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStoreMemberId() {
        return storeMemberId;
    }

    public void setStoreMemberId(Integer storeMemberId) {
        this.storeMemberId = storeMemberId;
    }

    public BigDecimal getBlagMoney() {
        return blagMoney;
    }

    public void setBlagMoney(BigDecimal blagMoney) {
        this.blagMoney = blagMoney;
    }

    public BigDecimal getTurnoverMoney() {
        return turnoverMoney;
    }

    public void setTurnoverMoney(BigDecimal turnoverMoney) {
        this.turnoverMoney = turnoverMoney;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getBlagStoreId() {
        return blagStoreId;
    }

    public void setBlagStoreId(Integer blagStoreId) {
        this.blagStoreId = blagStoreId;
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
