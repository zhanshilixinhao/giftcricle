package com.chouchong.entity.v4;

import com.chouchong.service.v3.vo.StoreVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author Lxh
 * @Date 2020/12/29 22:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Table(name = "rebate_coupon_manage")
public class RebateCouponManage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String summary;

    private String storeIds;

    private Integer adminId;

    private Byte status;

    private BigDecimal rebateNew;

    private BigDecimal rebateOld;

    private Date created;

    private Date updated;

    @Transient
    private List<StoreVo> storeVos;

    private BigDecimal rebate;
}
