package com.chouchong.service.v3.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author linqin
 * @date 2020/2/13 17:46
 */
@Data
public class InvoiceVo1 {
// 总充值
    private BigDecimal totalCharge;
    // 总开票
    private BigDecimal totalInvoice;
    // 剩余
    private BigDecimal amount;

    private List<InvoiceVo> invoiceVos;

}
