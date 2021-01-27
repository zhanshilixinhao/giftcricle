package com.chouchong.entity.v4;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

/**
 * @description: 分享劵核销记录对象
 * @author: LxH
 * @time: 2020/10/16 0016 下午 4:25
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "share_coupon_user_log")
public class ShareCouponUserLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Long shareCouponUserId;

    private Integer shareCouponId;

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

    @Transient
    private String storeName;

    @Transient
    private String title;
}
