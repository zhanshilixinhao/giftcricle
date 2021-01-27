package com.chouchong.entity.v4;
import	java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;


/**
 * @Description: 礼包用户关联表
 * @Author Lxh
 * @Date 2020/9/30 13:19
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "el_user_coupon_list")
public class ElUserCouponList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Integer elCouponListId;

    private Integer userId;

    private String phone;

    private Integer quantity;

    private Integer storeId;

    private Integer adminId;

    private Integer creatAdminId;

    private Date updated;

    private Date created;

    @Transient
    private String userName;

    @Transient
    private String storeName;

    public static final String CREATED = "created";

    public static final String PHONE = "phone";

    public static final String STORE_ID = "storeId";

    public static final String ADMIN_ID = "adminId";

    public static final String CREAT_ADMIN = "creatAdminId";

}
