package com.chouchong.service.statistics.impl;

import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.order.ConsignmentOrderMapper;
import com.chouchong.dao.order.ItemOrderMapper;
import com.chouchong.dao.order.VirtualItemOrderMapper;
import com.chouchong.dao.webUser.ChargeOrderMapper;
import com.chouchong.service.statistics.OrderStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yy
 * @date 2018/7/31
 **/

@Service
public class OrderStatisticsServiceImpl implements OrderStatisticsService{
    @Autowired
    private ItemOrderMapper itemOrderMapper;

    @Autowired
    private VirtualItemOrderMapper virtualItemOrderMapper;

    @Autowired
    private ChargeOrderMapper chargeOrderMapper;

    @Autowired
    private ConsignmentOrderMapper consignmentOrderMapper;


    /**
     * 获得订单统计
     *
     * @param: [orderType 订单类型, startTime 开始日期, endTime 结束日期]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/8/2
     */
    @Override
    public Response getOrderCount(Integer orderType, String startTime, String endTime){
        Map result = new HashMap();
        Map params = new HashMap();
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        // 若订单类型 =1 则查询商品订单
        if (orderType.intValue() == 1) {
            List<Integer> itemOrders = itemOrderMapper.getOrderStatistics(params);
            List<Integer> virItemOrders = virtualItemOrderMapper.getOrderStatistics(params);
            List<Integer> conOrders = consignmentOrderMapper.getOrderStatistics(params);
            result.put("itemOrders", itemOrders);
            result.put("virItemOrders", virItemOrders);
            result.put("conOrders", conOrders);
            result.put("title", new String[] { "已支付","已取消" });
        }
        // 若订单类型 =2 则查询充值订单
        if (orderType.intValue() == 2) {
            List<Integer> chargeOrders = chargeOrderMapper.getOrderStatistics(params);
            result.put("chargeOrders", chargeOrders);
            result.put("title", new String[] { "已支付","未支付" });
        }
        return ResponseFactory.sucData(result);
    }
}
