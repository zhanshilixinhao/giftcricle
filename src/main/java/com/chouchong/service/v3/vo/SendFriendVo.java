package com.chouchong.service.v3.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author linqin
 * @date 2020/2/12 16:48
 */
@Data
public class SendFriendVo {
    /**
     * 转赠记录id
     */
    private Integer id;

    private Integer userId;

    private String nickname;

    private String phone;

    private Integer couponId;

    private Long num;

    private String title;

    private Integer quantity;

    private Date created;

    private Byte status;

    /**
     * 转增详细记录id
     */
    private Integer reId;

    private Integer reUserId;

    private String reNickname;

    private String rePhone;

    private Date created1;

}
