package com.chouchong.entity.v4;

import com.chouchong.entity.v3.ElectronicCoupons;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Table;

/**
 * @Description:
 * @Author Lxh
 * @Date 2020/9/23 15:39
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "electronic_coupons")
public class PrivilegeCoupons extends ElectronicCoupons {
    private Integer day;
}
