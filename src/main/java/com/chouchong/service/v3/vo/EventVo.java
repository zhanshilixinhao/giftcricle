package com.chouchong.service.v3.vo;

import com.chouchong.entity.v4.MemberEventCoupon;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author linqin
 * @date 2019/11/7
 */
@Data
public class EventVo {

    private Integer id;

    private String title;

    private String summary;

    private BigDecimal rechargeMoney;

    private BigDecimal sendMoney;

    private Integer targetId;

    private Integer adminId;

    private Byte type;

    private Byte status;

    private Date created;

    private String targetName;

    private Integer quantity;

    private String storeIds;

    private List<StoreVo> stores;

    //优惠券
    private List<MemberEventCoupon> memberEventCouponList;


}
