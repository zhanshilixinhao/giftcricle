package com.chouchong.controller.statistics;

import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.service.statistics.OrderStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

/**
 * @author yy
 * @date 2018/7/31
 **/

@RestController
@RequestMapping("manage/statistics")
public class OrderStatisticsController {
    @Autowired
    private OrderStatisticsService orderStatisticsService;

    /**
     * 获得订单统计
     *
     * @param: [orderType 订单类型, startTime 开始日期, endTime 结束日期]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/8/2
     */
    @PostMapping("order")
    private Response getOrderCount(Integer orderType, String startTime, String endTime){
        if (orderType == null) {
            return ResponseFactory.errMissingParameter();
        }
        return orderStatisticsService.getOrderCount(orderType, startTime, endTime);
    }
}
