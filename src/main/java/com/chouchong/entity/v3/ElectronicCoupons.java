package com.chouchong.entity.v3;

import com.chouchong.service.v3.vo.StoreVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "electronic_coupons")
public class ElectronicCoupons {
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

    private String qrCode;

    private Integer day;
    /**
     * 适用门店
     */
    private List<StoreVo> storeVos;

    public static final String COLUMN_ADMIN_ID = "adminId";

    public static final String STATUS = "status";

}
