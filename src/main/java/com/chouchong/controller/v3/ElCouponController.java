package com.chouchong.controller.v3;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.entity.v3.ElectronicCoupons;
import com.chouchong.entity.v4.PrivilegeCoupons;
import com.chouchong.service.v3.ElCouponService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

/**
 * @author linqin
 * @date 2020/2/11 16:13
 */
@RestController
@RequestMapping("manage/v3/coupon")
public class ElCouponController {

    @Autowired
    private ElCouponService elCouponService;

    /**
     * 获取优惠券列表(优惠券只有平台商和门店可看)
     *
     * @param title 标题
     * @param page
     * @return
     */
    @PostMapping("list")
    public Response getElCouponList(String title, PageQuery page) {
        return elCouponService.getElCouponList(title, page);
    }

    /**
     * 获取优惠券列表(小程序商家端)(优惠券只有平台商和门店可看)
     *
     * @return
     */
    @PostMapping("xcx_list")
    public Response getElCouponListXcx() {
        return elCouponService.getElCouponListXcx();
    }


    /**
     * 用户优惠券核销
     *
     * @param num 二维码
     * @return
     */
    @PostMapping("use")
    public Response useCoupon(Long num) {
        if (num == null) {
            return ResponseFactory.errMissingParameter();
        }
        return elCouponService.useCoupon(num);
    }

//    /**
//     * 通过二维码获取用户的优惠券详情
//     *
//     * @param qrcode 二维码
//     * @return
//     */
//    @PostMapping("detailByQrcode")
//    public Response detailByQrcode(String qrcode) {
//        if (StringUtils.isBlank(qrcode)) {
//            return ResponseFactory.errMissingParameter();
//        }
//        return elCouponService.detailByQrcode(qrcode);
//    }

    /**
     * 获取优惠券所有列表(优惠券部分只有平台商有)
     *
     * @return
     */
    @PostMapping("all_list")
    public Response getElCouponAllList() {
        return elCouponService.getElCouponAllList();
    }

    /**
     * 添加平台商优惠券
     *
     * @param coupon
     * @return
     */
    @PostMapping("add")
    public Response addElCoupon(ElectronicCoupons coupon) {
        if (StringUtils.isAnyBlank(coupon.getTitle(), coupon.getSummary(), coupon.getStoreIds())) {
            return ResponseFactory.errMissingParameter();
        }
        if (coupon.getDate() == null || coupon.getStartTime() == null) {
            return ResponseFactory.errMissingParameter();
        }
        if (coupon.getDate().getTime() < System.currentTimeMillis()) {
            return ResponseFactory.err("最后有效期必须大于现在时间");
        }
        return elCouponService.addElCoupon(coupon);
    }

    /**
     * @Description: 添加优惠券
     * @Author: LxH
     * @Date: 2020/9/23 16:37
     */
    @PostMapping("addPrivilegeCoupons")
    public Response addPrivilegeCoupons(PrivilegeCoupons privilegeCoupons){
        if (StringUtils.isAnyBlank(privilegeCoupons.getTitle(), privilegeCoupons.getSummary(), privilegeCoupons.getStoreIds())) {
            return ResponseFactory.errMissingParameter();
        }

        if (privilegeCoupons.getDay()!=null&&privilegeCoupons.getDate()!=null&&privilegeCoupons.getStartTime()!=null) {
            return ResponseFactory.err("有效天数有效日期二选一！！");
        }
        return elCouponService.addPrivilegeCoupons(privilegeCoupons);
    }

    /**
     * @Description: 修改优惠券
     * @Author: LxH
     * @Date: 2020/9/23 16:37
     */
    @PostMapping("updatePrivilegeCoupons")
    public Response updatePrivilegeCoupons(PrivilegeCoupons privilegeCoupons){
        if (StringUtils.isAnyBlank(privilegeCoupons.getTitle(), privilegeCoupons.getSummary(), privilegeCoupons.getStoreIds())) {
            return ResponseFactory.errMissingParameter();
        }
        if (privilegeCoupons.getDay()!=null&&privilegeCoupons.getDate()!=null&&privilegeCoupons.getStartTime()!=null) {
            return ResponseFactory.err("有效天数有效日期二选一！！");
        }
        return elCouponService.updatePrivilegeCoupons(privilegeCoupons);
    }

    /**
     *
     *
     *@description: 删除优惠券
     *@author: LxH
     *@time: 2020/10/15 0015 下午 2:57
     *
     */
    @PostMapping("deletePrivilegeCoupon")
    public Response deletePrivilegeCoupon(Integer privilegeCouponId){
        if (privilegeCouponId==null) {
            return ResponseFactory.errMissingParameter();
        }
        return elCouponService.deletePrivilegeCoupon(privilegeCouponId);
    }

    /**
     * 修改平台商优惠券
     *
     * @param coupon
     * @return
     */
    @PostMapping("update")
    public Response updateElCoupon(ElectronicCoupons coupon) {
        if (StringUtils.isAnyBlank(coupon.getTitle(), coupon.getSummary(), coupon.getStoreIds())) {
            return ResponseFactory.errMissingParameter();
        }
        if (coupon.getDate() == null || coupon.getId() == null) {
            return ResponseFactory.errMissingParameter();
        }
        if (coupon.getDate().getTime() < System.currentTimeMillis()) {
            return ResponseFactory.err("最后有效期必须大于现在时间");
        }
        return elCouponService.updateElCoupon(coupon);
    }

    /**
     * 删除平台商优惠券
     *
     * @param couponId 优惠券id
     * @return
     */
    @PostMapping("delete")
    public Response deleteElCoupon(Integer couponId) {
        if (couponId == null) {
            return ResponseFactory.errMissingParameter();
        }
        return elCouponService.deleteElCoupon(couponId);
    }

    /**
     * 门店给用户发优惠券
     *
     * @param phone    用户号码
     * @param couponId 优惠券id
     * @param quantity 优惠券数量
     * @return
     */
    @PostMapping("forUser")
    public Response addCouponForUser(String phone, Integer couponId, Integer quantity) {
        if (StringUtils.isBlank(phone)) {
            return ResponseFactory.errMissingParameter();
        }
        if (couponId == null || quantity == null) {
            return ResponseFactory.errMissingParameter();
        }
        return elCouponService.addCouponForUser(phone, couponId, quantity);
    }

    /**
     * @Description: 批量赠送用户优惠券  例：couponJson="[{couponId:336,quantity:5},{couponId:339,quantity:8},{couponId:337,quantity:10}]"
     * @Author: LxH
     * @Date: 2020/9/23 13:11
     */
    @PostMapping("giveUserCoupons")
    public Response giveUserCoupons(String phone,String couponJson){
        if (StringUtils.isBlank(phone)) {
            return ResponseFactory.errMissingParameter();
        }
        return elCouponService.giveUserCoupons(phone,couponJson);
    }

    /**
     * @Description: 可以发送的优惠券
     * @Author: LxH
     * @Date: 2020/12/1 10:18
     */
    @PostMapping("findCoupons")
    public Response findCoupons(){
        return elCouponService.findCoupons();
    }

    /**
     * 商家给用户赠送优惠券记录
     *
     * @param page
     * @param title     优惠券标题
     * @param phone     用户电话
     * @param store     赠送门店
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    @PostMapping("user_list")
    public Response getForUserList(PageQuery page, String title, String phone, String store,
                                   Long startTime, Long endTime) throws ParseException {
        return elCouponService.getForUserList(page, title, phone, store, startTime, endTime);
    }

    /**
     * 优惠券转赠记录
     *
     * @param page
     * @param nickname  赠送者昵称
     * @param title     优惠券
     * @param status    状态
     * @param startTime
     * @param endTime
     * @return
     */
    @PostMapping("send_friend")
    public Response getSendCouponList(PageQuery page, String nickname, String title, Byte status,
                                      Long startTime, Long endTime) throws ParseException {
        return elCouponService.getSendCouponList(page, nickname, title, status, startTime, endTime);
    }

    /**
     * @Description: 获取非门店赠送的优惠券记录
     * @Author: LxH
     * @Date: 2020/10/27 11:25
     */
    @PostMapping("findCouponLog")
    public Response findCouponLog(Integer couponId){
        return elCouponService.findCouponLog(couponId);
    }
}
