package com.chouchong.entity.v3;

import lombok.Data;

import java.util.Date;

/**
 * @author linqin
 * @date 2020/2/10 10:55
 */
@Data
public class ElCouponVo {

    private Long num;

    private Integer qrcodeType;
    private Integer couponId;

    private Integer userId;

    private Integer quantity;

    private String code;

    private Date created;

    private String title;

    private String summary;

    private String logo;

    private String storeIds;

    private Date date;
    private Date startTime;

    private String storeName;

    private Byte status;

}
