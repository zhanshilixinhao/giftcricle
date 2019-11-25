package com.chouchong.service.v3.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author linqin
 * @date 2019/11/25
 */

public class ExpenseReVos {

    private BigDecimal totalExpenseMoney;

    private List<ExpenseReVo> expenseReVo;

    public BigDecimal getTotalExpenseMoney() {
        return totalExpenseMoney;
    }

    public void setTotalExpenseMoney(BigDecimal totalExpenseMoney) {
        this.totalExpenseMoney = totalExpenseMoney;
    }

    public List<ExpenseReVo> getExpenseReVo() {
        return expenseReVo;
    }

    public void setExpenseReVo(List<ExpenseReVo> expenseReVo) {
        this.expenseReVo = expenseReVo;
    }
}
