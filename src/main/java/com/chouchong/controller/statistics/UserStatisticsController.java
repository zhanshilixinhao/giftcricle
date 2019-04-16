package com.chouchong.controller.statistics;

import com.chouchong.common.Response;
import com.chouchong.service.statistics.UserStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;


/**
 * @author linqin
 * @date 2018/7/31
 */
@RestController
@RequestMapping("manage/statistics")
public class UserStatisticsController {

    @Autowired
    private UserStatisticsService userStatisticsService;

    /**
     * 统计用户注册
     *
     * @return
     * @author linqin
     * @date 2018/7/31
     */
    @PostMapping("user")
    public Response userNumber() {
        return userStatisticsService.userNumber();
    }


    /**
     * 按天统计用户注册
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     * @author linqin
     * @date 2018/7/31
     */
    @PostMapping("days")
    public Response days(String startTime, String endTime) {
        return userStatisticsService.days(startTime, endTime);
    }

    /**
     * 按月统计用户注册
     *
     * @param month 月
     * @return
     * @author linqin
     * @date 2018/7/31
     */
    @PostMapping("month")
    public Response month(Integer month) {
        String startMonth;
        String endMonth = null;
        if (month == null) {
            startMonth = Calendar.getInstance().get(Calendar.YEAR) + "-01-01";
        } else {
            startMonth = month+"-01-01";
            endMonth = month+1+"-01-01";
        }
        return userStatisticsService.month(startMonth,endMonth);
    }


}
