package com.chouchong.service.v3.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author linqin
 * @date 2019/11/20
 */
@Data
public class ChargeReVo {

    private Integer id;

    private Integer membershipCardId;

    private String title;

    private Long cardNo;

    private Integer userId;

    private String nickname;

    private String phone;

    private Integer memberEventId;

    private String eventName;

    private BigDecimal rechargeMoney;

    private BigDecimal sendMoney;

    private Byte type;

    private Integer storeId;

    private String storeName;

    private String explain;

    private Date created;

    private Long orderNo;

    private String image;


}
