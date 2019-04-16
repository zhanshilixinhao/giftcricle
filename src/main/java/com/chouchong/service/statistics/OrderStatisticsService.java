package com.chouchong.service.statistics;

import com.chouchong.common.Response;

import java.text.ParseException;

public interface OrderStatisticsService {
    /**
     * 获得订单统计
     *
     * @param: [orderType 订单类型, startTime 开始日期, endTime 结束日期]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/8/2
     */
    Response getOrderCount(Integer orderType, String startTime, String endTime);
}
