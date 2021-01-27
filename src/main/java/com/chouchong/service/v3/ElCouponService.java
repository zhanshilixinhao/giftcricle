package com.chouchong.service.v3;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.entity.v3.ElectronicCoupons;
import com.chouchong.entity.v4.PrivilegeCoupons;

import java.text.ParseException;

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

    Long addCoupon(Integer couponId,Integer quantity,Integer storeId,
                   Integer userId,Integer adminId);
    /**
     * 商家给用户赠送优惠券记录
     * @param page
     * @param title 优惠券标题
     * @param phone 用户电话
     * @param store 赠送门店
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    Response getForUserList(PageQuery page, String title, String phone, String store, Long startTime, Long endTime) throws ParseException;


    /**
     * 优惠券转赠记录
     * @param page
     * @param nickname 赠送者昵称
     * @param title 优惠券
     * @param status 状态
     * @param startTime
     * @param endTime
     * @return
     */
    Response getSendCouponList(PageQuery page, String nickname, String title, Byte status, Long startTime, Long endTime) throws ParseException;

    /**
     * 获取优惠券所有列表(优惠券部分只有平台商有)
     *
     * @return
     */
    Response getElCouponAllList();

    Response detailByQrcode(Long id);

    Response useCoupon(Long num);

    /**
     * 获取优惠券列表(小程序商家端)(优惠券只有平台商和门店可看)
     *
     * @return
     */
    Response getElCouponListXcx();

    /**
     * @Description: 批量赠送优惠券
     * @Author: LxH
     * @Date: 2020/9/23 13:13
     */
    Response giveUserCoupons(String phone, String couponJson);

    /**
     * @Description: 添加优惠券
     * @Author: LxH
     * @Date: 2020/9/23 16:37
     */
    Response addPrivilegeCoupons(PrivilegeCoupons privilegeCoupons);

    /**
     * @Description: 修改优惠券
     * @Author: LxH
     * @Date: 2020/9/23 16:37
     */
    Response updatePrivilegeCoupons(PrivilegeCoupons privilegeCoupons);

    /**
     *
     *
     *@description: 删除优惠券
     *@author: LxH
     *@time: 2020/10/15 0015 下午 2:57
     *
     */
    Response deletePrivilegeCoupon(Integer privilegeCouponId);

    /**
     * @Description: 获取非门店赠送的优惠券记录
     * @Author: LxH
     * @Date: 2020/10/27 11:25
     */
    Response findCouponLog(Integer couponId);

    /**
     * @Description: 可以发送的优惠券
     * @Author: LxH
     * @Date: 2020/12/1 10:18
     */
    Response findCoupons();
}
