package com.chouchong.service.statistics;

import com.chouchong.common.Response;

/**
 * @author linqin
 * @date 2018/7/31
 */
public interface UserStatisticsService {
    /**
     * 按天统计用户注册
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     * @author linqin
     * @date 2018/7/31
     */
    Response days(String startTime, String endTime);

    /**
     * 按月统计用户注册
     *
     * @param endMonth
     * @return
     * @author linqin
     * @date 2018/7/31
     */
    Response month(String startMonth, String endMonth);

    /**
     * 统计用户注册
     *
     * @return
     * @author linqin
     * @date 2018/7/31
     */
    Response userNumber();
}
