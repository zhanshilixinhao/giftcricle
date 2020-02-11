package com.chouchong.controller.v3;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.entity.v3.ElectronicCoupons;
import com.chouchong.service.v3.ElCouponService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * 获取优惠券列表(优惠券部分只有平台商有)
     * @param title 标题
     * @param page
     * @return
     */
    @PostMapping("list")
    public Response getElCouponList(String title, PageQuery page){
        return elCouponService.getElCouponList(title,page);
    }

    /**
     * 添加平台商优惠券
     * @param coupon
     * @return
     */
    @PostMapping("add")
    public Response addElCoupon(ElectronicCoupons coupon){
        if (StringUtils.isAnyBlank(coupon.getTitle(),coupon.getSummary(),coupon.getStoreIds())){
            return ResponseFactory.errMissingParameter();
        }
        if (coupon.getDate() == null){
            return ResponseFactory.errMissingParameter();
        }
        return elCouponService.addElCoupon(coupon);
    }

    /**
     * 修改平台商优惠券
     * @param coupon
     * @return
     */
    @PostMapping("update")
    public Response updateElCoupon(ElectronicCoupons coupon){
        if (StringUtils.isAnyBlank(coupon.getTitle(),coupon.getSummary(),coupon.getStoreIds())){
            return ResponseFactory.errMissingParameter();
        }
        if (coupon.getDate() == null || coupon.getId() == null){
            return ResponseFactory.errMissingParameter();
        }
        return elCouponService.updateElCoupon(coupon);
    }

    /**
     * 删除平台商优惠券
     * @param couponId 优惠券id
     * @return
     */
    @PostMapping("delete")
    public Response deleteElCoupon(Integer couponId){
        if (couponId == null){
            return ResponseFactory.errMissingParameter();
        }
        return elCouponService.deleteElCoupon(couponId);
    }

    /**
     * 门店给用户发优惠券
     * @param phone 用户号码
     * @param couponId 优惠券id
     * @param quantity 优惠券数量
     * @return
     */
    @PostMapping("forUser")
    public Response addCouponForUser(String phone,Integer couponId,Integer quantity){
        if (StringUtils.isBlank(phone)){
            return ResponseFactory.errMissingParameter();
        }
        if (couponId == null || quantity == null){
            return ResponseFactory.errMissingParameter();
        }
        return elCouponService.addCouponForUser(phone,couponId,quantity);
    }



}
