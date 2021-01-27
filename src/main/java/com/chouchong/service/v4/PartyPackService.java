package com.chouchong.service.v4;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.entity.v4.ElCouponList;

import java.util.Date;

/**
 * @Description:
 * @Author Lxh
 * @Date 2020/9/30 16:35
 */
public interface PartyPackService {

    /**
     * @Description: 后台添加大礼包
     * @Author: LxH
     * @Date: 2020/9/30 16:44
     */
    Response addPartyPack(ElCouponList elCouponList, String couponJson);

    /**
     * @Description: 后台修改大礼包
     * @Author: LxH
     * @Date: 2020/9/30 17:17
     */
    Response updatePartyPack(ElCouponList elCouponList, String couponJson);

    /**
     * @Description: 后台删除礼包
     * @Author: LxH
     * @Date: 2020/9/30 17:38
     */
    Response deletePartyPack(Integer elCouponListId);

    /**
     * @Description: 可以添加到礼包的优惠券
     * @Author: LxH
     * @Date: 2020/10/6 14:43
     */
    Response findCoupons();

    /**
     * @Description: 礼包分页查询
     * @Author: LxH
     * @Date: 2020/10/6 15:06
     */
    Response findPartyPackByPage(String title, PageQuery page);

    /**
     *
     *
     *@description: 后台赠送用户优惠券礼包
     *@author: LxH
     *@time: 2020/10/14 0014 下午 5:50
     *
     */
    Response givePartyPack(String phone, String partyPackJson);

    /**
     *
     *
     *@description: 后台赠送礼包记录
     *@author: LxH
     *@time: 2020/10/15 0015 上午 10:14
     *
     */
    Response partyPackRecord(PageQuery page, String title, String phone, Integer storeId, Date startTime, Date endTime);
}
