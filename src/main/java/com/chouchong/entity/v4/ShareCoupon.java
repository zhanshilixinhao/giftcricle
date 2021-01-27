package com.chouchong.entity.v4;

import com.chouchong.service.v3.vo.StoreVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @description: 分享劵实体类
 * @author: LxH
 * @time: 2020/10/15 0015 下午 2:02
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "share_coupon")
public class ShareCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String summary;

    private String logo;

    private String storeIds;

    private Integer adminId;

    private Byte type;

    private Byte status;

    private Date date;
    private Date startTime;

    private Date updated;

    private Date created;

    private Integer day;

    private Long totality;

    private Integer ceiling;

    private Long total;

    private String rule;


    /**
     * 适用门店
     */
    @Transient
    private List<StoreVo> storeVos;
}
