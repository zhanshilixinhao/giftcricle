package com.chouchong.service.v3.vo;

import java.util.List;

/**
 * @author linqin
 * @date 2019/11/11
 */

public class RecordVo {
    private List<ChargeVo> charges;

    private List<ExpenseVo> expenses;

    public List<ChargeVo> getCharges() {
        return charges;
    }

    public void setCharges(List<ChargeVo> charges) {
        this.charges = charges;
    }

    public List<ExpenseVo> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<ExpenseVo> expenses) {
        this.expenses = expenses;
    }
}
