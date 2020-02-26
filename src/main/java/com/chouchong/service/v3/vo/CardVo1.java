package com.chouchong.service.v3.vo;



import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author linqin
 * @date 2019/10/24
 */
@Data
public class CardVo1 {
    /**
     * 会员卡用户关联表id
     */
    private Integer id;

    private Integer membershipCardId;

    private Integer userId;

    private Integer qrcodeType;

    private String phone;

    private BigDecimal balance;

    private Date created;

    private Long cardNo;

    private String title;

    private String summary;

    private String colour;

    private String logo;

    private Byte type;

    private String storeIds;

//    private List<Store> stores;

    private String grade;

    private String summaryGrade;// 等级说明

    private BigDecimal capital;

    private BigDecimal send;

    private String code;

    private String avatar;

    private String nickname;
    private String storeName;


}
