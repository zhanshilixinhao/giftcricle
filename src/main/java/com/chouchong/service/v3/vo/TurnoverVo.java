package com.chouchong.service.v3.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author linqin
 * @date 2019/11/18
 */

public class TurnoverVo {

    private Integer id;

    private Integer userId;

    private Integer merchantId;

    private Integer storeId;

    private BigDecimal rechargeMoney;

    private BigDecimal sendMoney;

    private BigDecimal expenseMoney;

    private BigDecimal totalMoney;

    private Float scale;

    private Integer membershipCardId;
}
