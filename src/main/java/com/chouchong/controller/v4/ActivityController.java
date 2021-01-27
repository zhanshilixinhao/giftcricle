package com.chouchong.controller.v4;

import com.alibaba.fastjson.JSONObject;
import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.entity.v3.MemberEvent;
import com.chouchong.service.v3.vo.SpecialVo;
import com.chouchong.service.v4.ActivityService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: 新版活动控制层
 * @Author Lxh
 * @Date 2020/9/25 14:10
 */
@RestController
@RequestMapping("manage/v4/event")
public class ActivityController {
    @Resource
    private ActivityService activityService;

    /**
     * @Description: 查询活动列表
     * @Author: LxH
     * @Date: 2020/9/27 14:47
     */
    @PostMapping("selectList")
    public Response selectList(String title, PageQuery page){
        return activityService.selectList(title,page);
    }

    /**
     * @Description: 后台添加活动
     * @Author: LxH
     * @Date: 2020/9/25 14:18
     */
    @PostMapping("addEvent")
    public Response addEvent(MemberEvent event,String couponJson,String storeIds){
        if (StringUtils.isBlank(event.getTitle()) || event.getRechargeMoney() == null || event.getStatus() == null) {
            return ResponseFactory.errMissingParameter();
        }
        if (event.getStatus() == 10 && event.getScale() == null) {
            return ResponseFactory.errMissingParameter();
        }
        return activityService.addEvent(event,couponJson,storeIds);
    }

    /**
     * @Description: 修改活动
     * @Author: LxH
     * @Date: 2020/9/25 17:34
     */
    @PostMapping("updateEvent")
    public Response updateEvent(MemberEvent event,String couponJson){
        if (couponJson==null) {
            return ResponseFactory.errMissingParameter();
        }
        if (StringUtils.isBlank(event.getTitle()) || event.getRechargeMoney() == null || event.getStatus() == null) {
            return ResponseFactory.errMissingParameter();
        }
        if (event.getStatus() == 10 && event.getScale() == null) {
            return ResponseFactory.errMissingParameter();
        }
        return activityService.updateEvent(event,couponJson);
    }

    /**
     * @Description: 删除活动
     * @Author: LxH
     * @Date: 2020/9/27 14:23
     */
    @PostMapping("deleteEvent")
    public Response deleteEvent(Integer eventId){
        if (eventId == null) {
            return ResponseFactory.sucMsg("参数不能为空！！");
        }
        return activityService.deleteEvent(eventId);
    }

    /**
     * @Description: 获取门店对应的活动
     * @Author: LxH
     * @Date: 2020/9/29 16:09
     */
    @PostMapping("getEventList")
    public Response getEventList(String storeIds){
        return activityService.getEventList(storeIds);
    }
}
