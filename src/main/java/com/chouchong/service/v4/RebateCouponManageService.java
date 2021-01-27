package com.chouchong.service.v4;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.entity.v4.RebateCouponManage;

/**
 * @Description:
 * @Author Lxh
 * @Date 2020/12/30 9:39
 */
public interface RebateCouponManageService {
    /**
     * @Description: 后台添加折扣券
     * @Author: LxH
     * @Date: 2020/12/30 9:43
     */
    Response addRebateCouponManage(RebateCouponManage rebateCouponManage);

    /**
     * @Description: 后台修改折扣券
     * @Author: LxH
     * @Date: 2020/12/30 9:59
     */
    Response updateRebateCouponManage(RebateCouponManage rebateCouponManage);

    /**
     * @Description: 后台删除折扣券
     * @Author: LxH
     * @Date: 2020/12/30 10:19
     */
    Response deleteRebateCouponManage(Integer id);

    /**
     * @Description: 折扣券查询
     * @Author: LxH
     * @Date: 2020/12/30 10:27
     */
    Response getList(String title, PageQuery page);
}
