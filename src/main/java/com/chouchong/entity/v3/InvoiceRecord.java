package com.chouchong.entity.v3;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class InvoiceRecord {
    private Integer id;

    private Integer cardId;

    private Integer userId;

    private BigDecimal amount;

    private String summary;

    private Integer adminId;

    private Integer storeId;

    private Date updated;

    private Date created;



}
