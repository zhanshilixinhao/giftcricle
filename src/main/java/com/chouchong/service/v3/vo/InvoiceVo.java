package com.chouchong.service.v3.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author linqin
 * @date 2020/2/13 17:45
 */
@Data
public class InvoiceVo {
    private Integer id;

    private BigDecimal amount;

    private String summary;

    private Integer storeId;

    private String storeName;

    private Date created;

}
