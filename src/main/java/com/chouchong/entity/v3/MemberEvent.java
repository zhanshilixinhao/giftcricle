package com.chouchong.entity.v3;

import com.chouchong.entity.v4.MemberEventCoupon;
import com.chouchong.service.v3.vo.StoreVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Accessors(chain = true)
@Table(name = "member_event")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String summary;

    private BigDecimal rechargeMoney;

    private BigDecimal sendMoney;



    private Integer targetId;

    private Integer adminId;

    private Byte type;

    private Byte status;

    private Date updated;

    private Date created;

    private Float scale;

    private Integer quantity;

    private String storeIds;

    @Transient
    private String couponTitle;

    @Transient
    private List<MemberEventCoupon> memberEventCouponsList;

    @Transient
    private List<StoreVo> storeVos;

    public static final String ADMIN_ID = "adminId";

    public static final String TITLE = "title";


}
