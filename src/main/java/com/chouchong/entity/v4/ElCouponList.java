package com.chouchong.entity.v4;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Description: 礼包实体类
 * @Author Lxh
 * @Date 2020/9/30 12:55
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "el_coupon_list")
public class ElCouponList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String summary;

    private String logo;

    private Integer adminId;

    private Byte type;

    private Byte status;

    private Date created;

    private Date updated;

    private Byte thePaid;

    private BigDecimal price;

    private String qrCode;

    private String partyPackJson;

    @Transient
    private List<ElCouponListCoupon> elCouponListCoupons;

    public static final String TITLE = "title";

    public static final String TYPE = "type";

}
