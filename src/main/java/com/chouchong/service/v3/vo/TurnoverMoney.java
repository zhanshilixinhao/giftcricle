package com.chouchong.service.v3.vo;

import java.math.BigDecimal;

/**
 * @author linqin
 * @date 2019/12/16
 */

public class TurnoverMoney {

    /**
     * 总消费金额
     */
    private BigDecimal totalMoney = new BigDecimal("0") ;

    /**
     * /总收入
     */
    private BigDecimal totalBlagMoney = new BigDecimal("0") ;

    /**
     * /总营业额
     */
    private BigDecimal totalTurnoverMoney = new BigDecimal("0") ;

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public BigDecimal getTotalBlagMoney() {
        return totalBlagMoney;
    }

    public void setTotalBlagMoney(BigDecimal totalBlagMoney) {
        this.totalBlagMoney = totalBlagMoney;
    }

    public BigDecimal getTotalTurnoverMoney() {
        return totalTurnoverMoney;
    }

    public void setTotalTurnoverMoney(BigDecimal totalTurnoverMoney) {
        this.totalTurnoverMoney = totalTurnoverMoney;
    }
}
