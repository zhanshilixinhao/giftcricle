package com.chouchong.service.v4;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.entity.v4.ShareCoupon;

public interface ShareCouponService {

    /**
     *
     *
     *@description: 添加分享劵
     *@author: LxH
     *@time: 2020/10/15 0015 下午 2:16
     *
     */
    Response addShareCoupon(ShareCoupon shareCoupon);

    /**
     *
     *
     *@description: 修改分享劵
     *@author: LxH
     *@time: 2020/10/15 0015 下午 2:42
     *
     */
    Response updateShareCoupon(ShareCoupon shareCoupon);

    /**
     *
     *
     *@description: 删除分享劵
     *@author: LxH
     *@time: 2020/10/15 0015 下午 3:00
     *
     */
    Response deleteShareCoupon(Integer shareCouponId);

    /**
     *
     *
     *@description: 后台分享劵分页查询
     *@author: LxH
     *@time: 2020/10/15 0015 下午 3:21
     *
     */
    Response findShareCouponList(String title, PageQuery page);
    /**
     *
     *
     *@description: 分享劵领取记录
     *@author: LxH
     *@time: 2020/10/16 0016 下午 1:23
     *
     */
    Response shareCouponRecord(String title, String phone, PageQuery page);
    /**
     *
     *
     *@description: 扫二维码获取分享劵详情
     *@author: LxH
     *@time: 2020/10/16 0016 下午 2:59
     *
     */
    Response detailByQrcode(long id);
    /**
     *
     *
     *@description: 核销分享劵
     *@author: LxH
     *@time: 2020/10/16 0016 下午 3:41
     *
     */
    Response userShareCoupon(Long userShareId);
    /**
     *
     *
     *@description: 分享劵用户核销记录
     *@author: LxH
     *@time: 2020/10/16 0016 下午 5:53
     *
     */
    Response userShareCouponLog(String title, String phone, Integer storeId, PageQuery page);
}
