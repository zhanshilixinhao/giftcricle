package com.chouchong.entity.v4;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @Description: 活动优惠券关联表
 * @Author Lxh
 * @Date 2020/9/25 11:40
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "member_event_coupon")
public class MemberEventCoupon {
    @Id
    private Integer id;
    private Integer eventId;
    private Integer couponId;
    @Transient
    private String couponName;
    private Integer quantity;

    public static final String EVENT_ID = "eventId";

}
