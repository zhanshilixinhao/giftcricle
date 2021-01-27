package com.chouchong.service.v4.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;


/**
 * @Description:
 * @Author Lxh
 * @Date 2020/10/22 9:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RebateCouponLogVo {
    private Long rebateCouponId;

    private String nickname;

    private String phone;

    private BigDecimal rebate;

    private Date created;

}
