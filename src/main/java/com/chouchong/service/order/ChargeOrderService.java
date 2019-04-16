package com.chouchong.service.order;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;

/**
 * @author linqin
 * @date 2018/7/16
 */
public interface ChargeOrderService {
    /**
     * 充值订单列表查询
     * @param page
     * @param nickname 用户昵称
     * @param phone 号码
     * @param orderNo 订单号
     * @param status 充值状态，1-未支付，2-已支付  3-已删除
     * @param payWay  支付方式，1-微信，2-支付宝
     * @return
     * @author linqin
     * @date 2018/7/16
     */
    Response getList(PageQuery page, String nickname,String phone,Long orderNo,Byte status,Byte payWay);

    /**
     * 删除充值订单
     *
     * @param id 充值订单id
     * @return
     * @author linqin
     * @date 2018/7/16
     */
    Response delChargeOrder(Integer id);
}
