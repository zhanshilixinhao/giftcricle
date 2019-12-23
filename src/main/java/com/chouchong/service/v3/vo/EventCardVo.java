package com.chouchong.service.v3.vo;

import java.math.BigDecimal;

/**
 * @author linqin
 * @date 2019/12/23
 */

public class EventCardVo {
    private Integer storeMemberEventId;

    private BigDecimal capitalBalance;

    private BigDecimal sendBalance;

    private Byte status;

    public Integer getStoreMemberEventId() {
        return storeMemberEventId;
    }

    public void setStoreMemberEventId(Integer storeMemberEventId) {
        this.storeMemberEventId = storeMemberEventId;
    }

    public BigDecimal getCapitalBalance() {
        return capitalBalance;
    }

    public void setCapitalBalance(BigDecimal capitalBalance) {
        this.capitalBalance = capitalBalance;
    }

    public BigDecimal getSendBalance() {
        return sendBalance;
    }

    public void setSendBalance(BigDecimal sendBalance) {
        this.sendBalance = sendBalance;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}
