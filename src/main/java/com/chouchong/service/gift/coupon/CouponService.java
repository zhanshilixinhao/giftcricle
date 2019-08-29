package com.chouchong.service.gift.coupon;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.entity.gift.coupon.Coupon;

public interface CouponService {
    /**
     * 获得优惠券列表
     *
     * @param: [page 分页信息, title 优惠券标题, adminId 创建者, brandName 品牌名称]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/11
     */
    Response getCouponList(PageQuery page, String title, String brandName, String token);

    /**
     * 添加优惠券
     *
     * @param: [coupon 优惠券信息]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/11
     */
    Response addCoupon(Coupon coupon, String token);

    /**
     * 修改优惠券
     *
     * @param: [coupon 优惠券信息]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/11
     */
    Response updateCoupon(Coupon coupon);

    /**
     * 删除优惠券
     *
     * @param: [id 优惠券id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/11
     */
    Response delCoupon(Integer id);

    /**
     * 修改优惠券状态
     *
     * @param: [id 优惠券id, status 优惠券状态]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/11
     */
    Response changeStatus(Integer id, Integer status);

    /**
     * 赠送优惠券
     *
     * @param: [userId app用户id, couponId 优惠券id, quantity 优惠券数量]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/11
     */
    Response giveCouponUser(Integer userId, Integer couponId, Integer quantity,String token);

    /**
     * 使用优惠券
     *
     * @param: [couponCode 优惠券码]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/11
     */
    Response useCoupon(Long couponCode,String token);


    /**
     * 获取优惠券赠送列表
     * @param page
     * @param token
     * @param title
     * @return
     */
    Response getSendRecordList(PageQuery page, String token, String title);



    /**
     * 删除优惠券赠送记录
     * @param sendId 优惠券赠送记录id
     * @return
     */
    Response deleteSendRecord(Integer sendId);

    /**
     * 获取优惠券使用列表
     * @param page
     * @param token
     * @param title
     * @return
     */
    Response getUseRecordList(PageQuery page, String token, String title);

    /**
     * 删除优惠券赠送记录
     * @param useId 优惠券使用记录id
     * @return
     */
    Response deleteUseRecord(Integer useId);
}
