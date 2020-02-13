package com.chouchong.entity.v3;

import lombok.Data;

import java.util.Date;

@Data
public class ElCouponUseLog {
    private Integer id;
    private Long num;
    private Integer couponId;
    private Integer userId;
    private Integer storeId;
    private String detail;
    private Integer adminId;
    private Integer quantity;
    private Date created;
}
