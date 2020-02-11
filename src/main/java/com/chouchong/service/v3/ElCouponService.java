package com.chouchong.service.v3;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.entity.v3.ElectronicCoupons;

/**
 * @author linqin
 * @date 2020/2/11 16:15
 */

public interface ElCouponService {

    /**
     * 获取优惠券列表
     * @param title 标题
     * @param page
     * @return
     */
    Response getElCouponList(String title, PageQuery page);

    /**
     * 添加平台商优惠券
     * @param coupon
     * @return
     */
    Response addElCoupon(ElectronicCoupons coupon);

    /**
     * 修改平台商优惠券
     * @param coupon
     * @return
     */
    Response updateElCoupon(ElectronicCoupons coupon);

    /**
     * 删除平台商优惠券
     * @param couponId 优惠券id
     * @return
     */
    Response deleteElCoupon(Integer couponId);

    /**
     * 给用户发优惠券
     * @param phone 用户号码
     * @param couponId 优惠券id
     * @param quantity 优惠券数量
     * @return
     */
    Response addCouponForUser(String phone, Integer couponId, Integer quantity);
}
