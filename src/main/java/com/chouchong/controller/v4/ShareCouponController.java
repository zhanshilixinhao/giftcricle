package com.chouchong.controller.v4;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.entity.v4.ShareCoupon;
import com.chouchong.service.v4.ShareCouponService;
import com.chouchong.service.webUser.vo.WebUserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @description: 分享劵
 * @author: LxH
 * @time: 2020/10/15 0015 下午 2:07
 */
@RestController
@RequestMapping("manage/v4/shareCoupon")
public class ShareCouponController {

    @Resource
    private ShareCouponService shareCouponService;

    @Resource
    private HttpServletRequest httpServletRequest;

    @RequestMapping("test")
    public Response test(){
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        return ResponseFactory.sucData(webUserInfo.getSysAdmin().getId());
    }

    /**
     *
     *
     *@description: 添加分享劵
     *@author: LxH
     *@time: 2020/10/15 0015 下午 2:16
     *
     */
    @PostMapping("addShareCoupon")
    public Response addShareCoupon(ShareCoupon shareCoupon){
        if (StringUtils.isAnyBlank(shareCoupon.getTitle(), shareCoupon.getSummary(), shareCoupon.getStoreIds())) {
            return ResponseFactory.errMissingParameter();
        }
        if (shareCoupon.getDate() == null || shareCoupon.getStartTime() == null) {
            return ResponseFactory.errMissingParameter();
        }
        if (shareCoupon.getDate().getTime() < System.currentTimeMillis()) {
            return ResponseFactory.err("最后有效期必须大于现在时间");
        }
        if (shareCoupon.getDay()<1) {
            return ResponseFactory.err("有效天数必须大于1");
        }
        return shareCouponService.addShareCoupon(shareCoupon);
    }

    /**
     *
     *
     *@description: 后台修改分享劵
     *@author: LxH
     *@time: 2020/10/15 0015 下午 2:41
     *
     */
    @PostMapping("updateShareCoupon")
    public Response updateShareCoupon(ShareCoupon shareCoupon){
        if (StringUtils.isAnyBlank(shareCoupon.getTitle(), shareCoupon.getSummary(), shareCoupon.getStoreIds())) {
            return ResponseFactory.errMissingParameter();
        }
        if (shareCoupon.getDate() == null || shareCoupon.getStartTime() == null) {
            return ResponseFactory.errMissingParameter();
        }
        if (shareCoupon.getDate().getTime() < System.currentTimeMillis()) {
            return ResponseFactory.err("最后有效期必须大于现在时间");
        }
        if (shareCoupon.getDay()<1) {
            return ResponseFactory.err("有效天数必须大于1");
        }
        return shareCouponService.updateShareCoupon(shareCoupon);
    }

    /**
     *
     *
     *@description: 删除分享劵
     *@author: LxH
     *@time: 2020/10/15 0015 下午 3:00
     *
     */
    @PostMapping("deleteShareCoupon")
    public Response deleteShareCoupon(Integer shareCouponId){
        if (shareCouponId == null) {
            return ResponseFactory.err("参数不能为空");
        }
        return shareCouponService.deleteShareCoupon(shareCouponId);
    }

    /**
     *
     *
     *@description: 后台分享劵分页查询
     *@author: LxH
     *@time: 2020/10/15 0015 下午 3:21
     *
     */
    @PostMapping("findShareCouponList")
    public Response findShareCouponList(String title, PageQuery page){
        return shareCouponService.findShareCouponList(title,page);
    }

    /**
     *
     *
     *@description: 分享劵领取记录
     *@author: LxH
     *@time: 2020/10/16 0016 下午 1:23
     *
     */
    @PostMapping("shareCouponRecord")
    public Response shareCouponRecord(String title , String phone ,PageQuery page){
        return shareCouponService.shareCouponRecord(title,phone,page);
    }

    /**
     *
     *
     *@description: 核销分享劵
     *@author: LxH
     *@time: 2020/10/16 0016 下午 3:41
     *
     */
    @PostMapping("userShareCoupon")
    public Response userShareCoupon(Long userShareId){
        if (StringUtils.isBlank(userShareId.toString())) {
            return ResponseFactory.err("参数不能为空");
        }
        return shareCouponService.userShareCoupon(userShareId);
    }

    /**
     *
     *
     *@description: 分享劵用户核销记录
     *@author: LxH
     *@time: 2020/10/16 0016 下午 5:53
     *
     */
    @PostMapping("userShareCouponLog")
    public Response userShareCouponLog(String title , String phone , Integer storeId , PageQuery page){
        return shareCouponService.userShareCouponLog(title,phone,storeId,page);
    }
}
