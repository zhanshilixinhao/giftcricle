package com.chouchong.service.v3.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author linqin
 * @date 2020/2/12 15:36
 */
@Data
public class ForUserVo {

    private String nickname;

    private String phone;

    private Integer userId;

    private Integer couponId;

    private String title;

    private Long num;

    private Date date;

    private Integer totalQuantity;

    private Integer quantity;

    private Date created;

    private String storeName;
}
