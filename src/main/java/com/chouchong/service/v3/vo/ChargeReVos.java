package com.chouchong.service.v3.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author linqin
 * @date 2019/11/25
 */

public class ChargeReVos {

    /**
     * 总充值金额
     */
    private BigDecimal totalChargeMoney;

    /**
     * 总赠送金额
     */
    private BigDecimal totalSendMoney;

    private List<ChargeReVo> chargeReVo;

    public BigDecimal getTotalChargeMoney() {
        return totalChargeMoney;
    }

    public void setTotalChargeMoney(BigDecimal totalChargeMoney) {
        this.totalChargeMoney = totalChargeMoney;
    }

    public BigDecimal getTotalSendMoney() {
        return totalSendMoney;
    }

    public void setTotalSendMoney(BigDecimal totalSendMoney) {
        this.totalSendMoney = totalSendMoney;
    }

    public List<ChargeReVo> getChargeReVo() {
        return chargeReVo;
    }

    public void setChargeReVo(List<ChargeReVo> chargeReVo) {
        this.chargeReVo = chargeReVo;
    }
}
