package com.chouchong.entity.v3;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Table(name = "el_user_coupon")
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ElUserCoupon {
    @Id
    private Long id;

    private Integer couponId;

    @Transient
    private String couponName;

    private Integer userId;

    @Transient
    private String userName;

    @Transient
    private String phonr;

    private Integer totalQuantity;

    private Integer quantity;

    private Byte status;

    private String code;

    private Integer storeId;

    private Integer adminId;

    private Date updated;

    private Date created;

    public static final String ELUSERCOUPON_STATUS = "status";


}