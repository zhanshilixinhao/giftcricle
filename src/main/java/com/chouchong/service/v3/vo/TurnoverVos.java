package com.chouchong.service.v3.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author linqin
 * @date 2019/11/25
 */

public class TurnoverVos {

   private List<TurnoverVo> turnoverVo;

   private TurnoverMoney money;

    public List<TurnoverVo> getTurnoverVo() {
        return turnoverVo;
    }

    public void setTurnoverVo(List<TurnoverVo> turnoverVo) {
        this.turnoverVo = turnoverVo;
    }

    public TurnoverMoney getMoney() {
        return money;
    }

    public void setMoney(TurnoverMoney money) {
        this.money = money;
    }
}
