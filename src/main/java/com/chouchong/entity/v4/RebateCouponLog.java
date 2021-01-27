package com.chouchong.entity.v4;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description:
 * @Author Lxh
 * @Date 2020/10/21 17:07
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rebate_coupon_log")
public class RebateCouponLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Long rebateCouponId;

    private Integer userId;

    private Integer storeId;

    private String detail;

    private Integer adminId;

    private Date created;

    private Integer creatAdminId;

    @Transient
    private String userName;

    @Transient
    private String userPhone;

    private BigDecimal rebate;

}
