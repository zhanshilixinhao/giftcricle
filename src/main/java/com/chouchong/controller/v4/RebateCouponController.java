package com.chouchong.controller.v4;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.service.v4.RebateCouponService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description: 折扣卷模块
 * @Author Lxh
 * @Date 2020/10/21 16:07
 */
@RestController
@RequestMapping("manage/v4/RebateCoupon")
public class RebateCouponController {

    @Resource
    private RebateCouponService rebateCouponService;

    /**
     * @Description: 核销用户折扣卷
     * @Author: LxH
     * @Date: 2020/10/21 16:43
     */
    @PostMapping("userRebateCoupon")
    public Response userRebateCoupon(long rebateCouponId){
        return rebateCouponService.userRebateCoupon(rebateCouponId);
    }

    /**
     * @Description: 根据条件查询折扣卷核销日志  type 0.今天 1.本周 2.本月 3.全部
     * @Author: LxH
     * @Date: 2020/10/22 10:33
     */
    @PostMapping("findRebateCouponLog")
    public Response findRebateCouponLog(Integer type , String phone, PageQuery page){
        return rebateCouponService.findRebateCouponLog(type,phone,page);
    }
}
