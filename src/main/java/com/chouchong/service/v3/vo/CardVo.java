package com.chouchong.service.v3.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author linqin
 * @date 2019/11/6
 */

@Data
public class CardVo {

    private Integer id;

    private Long cardNo;

    private String title;

    private String colour;

    private String logo;

    private String storeIds;

    private String eventIds;

    private Integer adminId;

    private Byte type;

    private Date created;

    private String summary;

    private String membershipCardId;
    /**
     * 活动标题
     */
    private String detail;

    private List<StoreVo> storeVos;

    private List<EventVo> eventVos;

    private String userId;

    private BigDecimal capital;
    private BigDecimal send;

    private Integer qrcodeType;

    private String nickname;
    private String avatar;
}
