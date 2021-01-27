package com.chouchong.entity.v4;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

/**
 * @description: 分享劵用户关联表对象
 * @author: LxH
 * @time: 2020/10/15 0015 下午 5:55
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "share_coupon_user")
public class ShareCouponUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer shareCouponId;

    private Integer userId;

    private Integer totalQuantity;

    private Integer quantity;

    private Byte status;

    private Integer storeId;

    private Integer adminId;

    private Integer creatAdminId;

    private String phone;

    private String title;

    private Date created;

    private Date updated;

    @Transient
    private String userName;

    @Transient
    private Integer qrcodeType;

    @Transient
    private String logo;

    @Transient
    private String storeName;


    private Date endTime;

    public static final String USER_ID = "userId";

    public static final String SHARE_COUPON_ID = "shareCouponId";

    public static final String STATUS = "status";

    public static final String TITLE = "`title`";

    public static final String PHONE = "phone";
}
