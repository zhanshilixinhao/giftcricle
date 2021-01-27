package com.chouchong.service.v4;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.entity.v3.MemberEvent;

/**
 * @Description:
 * @Author Lxh
 * @Date 2020/9/25 14:14
 */
public interface ActivityService {

    /**
     * @Description: 后台添加活动
     * @Author: LxH
     * @Date: 2020/9/25 14:18
     */
    Response addEvent(MemberEvent event,String couponJson,String storeIds);

    /**
     * @Description: 修改活动
     * @Author: LxH
     * @Date: 2020/9/25 17:34
     */
    Response updateEvent(MemberEvent event, String couponJson);

    /**
     * @Description: 删除活动
     * @Author: LxH
     * @Date: 2020/9/27 14:23
     */
    Response deleteEvent(Integer eventId);

    /**
     * @Description: 查询活动列表
     * @Author: LxH
     * @Date: 2020/9/27 14:47
     */
    Response selectList(String title, PageQuery page);

    /**
     * @Description: 获取门店对应的活动
     * @Author: LxH
     * @Date: 2020/9/29 16:09
     */
    Response getEventList(String eventIds);
}
