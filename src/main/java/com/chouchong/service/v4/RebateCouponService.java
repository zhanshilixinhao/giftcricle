package com.chouchong.service.v4;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;

/**
 * @Description:
 * @Author Lxh
 * @Date 2020/10/21 16:19
 */
public interface RebateCouponService {

    /**
     * @Description: 二维码展示详情
     * @Author: LxH
     * @Date: 2020/10/21 16:24
     */
    Response detailByQrcode(long id);

    /**
     * @Description: 核销用户折扣卷
     * @Author: LxH
     * @Date: 2020/10/21 16:43
     */
    Response userRebateCoupon(long rebateCouponId);

    /**
     * @Description: 根据条件查询折扣卷核销日志  type 0.今天 1.本周 2.本月 3.全部
     * @Author: LxH
     * @Date: 2020/10/22 10:33
     */
    Response findRebateCouponLog(Integer type, String phone, PageQuery page);
}
