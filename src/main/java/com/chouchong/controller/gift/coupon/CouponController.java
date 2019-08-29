package com.chouchong.controller.gift.coupon;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.gift.coupon.CouponMapper;
import com.chouchong.entity.gift.coupon.Coupon;
import com.chouchong.service.gift.coupon.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yy
 * @date 2018/7/9
 **/
@RestController
@RequestMapping("manage/coupon")
public class CouponController {
    @Autowired
    private CouponService couponService;

    /**
     * 获得优惠券列表
     *
     * @param: [page 分页信息, title 优惠券标题, adminId 创建者, brandName 品牌名称]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/11
     */
    @PostMapping("list")
    public Response getCouponList(PageQuery page, String title, String brandName, String token) {
        return couponService.getCouponList(page, title, brandName, token);
    }

    /**
     * 添加优惠券
     *
     * @param: [coupon 优惠券信息]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/11
     */
    @PostMapping("add")
    public Response addCoupon(Coupon coupon, String token) {
        if (coupon.getCover() == null || coupon.getTitle() == null || coupon.getBrandName() == null) {
            return ResponseFactory.errMissingParameter();
        }
        return couponService.addCoupon(coupon, token);
    }

    /**
     * 修改优惠券
     *
     * @param: [coupon 优惠券信息]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/11
     */
    @PostMapping("update")
    public Response updateCoupon(Coupon coupon) {
        if (coupon.getId() == null || coupon.getCover() == null || coupon.getTitle() == null || coupon.getBrandName() == null) {
            return ResponseFactory.errMissingParameter();
        }
        return couponService.updateCoupon(coupon);
    }

    /**
     * 删除优惠券
     *
     * @param: [id 优惠券id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/11
     */
    @PostMapping("del")
    public Response delCoupon(Integer id) {
        if (id == null) {
            return ResponseFactory.errMissingParameter();
        }
        return couponService.delCoupon(id);
    }

    /**
     * 修改优惠券状态
     *
     * @param: [id 优惠券id, status 优惠券状态]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/11
     */
    @PostMapping("status")
    public Response changeStatus(Integer id, Integer status) {
        if (id == null || status == null) {
            return ResponseFactory.errMissingParameter();
        }
        return couponService.changeStatus(id, status);
    }

    /**
     * 赠送优惠券
     *
     * @param: [userId app用户id, couponId 优惠券id, quantity 优惠券数量]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/11
     */
    @PostMapping("give")
    public Response giveCouponUser(Integer userId, Integer couponId, Integer quantity,String token) {
        if (userId == null || couponId == null || quantity == null) {
            return ResponseFactory.errMissingParameter();
        }
        return couponService.giveCouponUser(userId, couponId, quantity,token);
    }

    /**
     * 使用优惠券
     *
     * @param: [couponCode 优惠券码]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/11
     */
    @PostMapping("use")
    public Response useCoupon(Long couponCode) {
        if (couponCode == null) {
            return ResponseFactory.errMissingParameter();
        }
        return couponService.useCoupon(couponCode);
    }
}
