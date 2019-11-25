package com.chouchong.service.v3.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author linqin
 * @date 2019/11/25
 */

public class TurnoverVos {

   private List<TurnoverVo> turnoverVo;

    /**
     * /总收入
     */
   private BigDecimal totalBlagMoney;

    /**
     * /总营业额
     */
   private BigDecimal totalTurnoverMoney;

    public List<TurnoverVo> getTurnoverVo() {
        return turnoverVo;
    }

    public void setTurnoverVo(List<TurnoverVo> turnoverVo) {
        this.turnoverVo = turnoverVo;
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
