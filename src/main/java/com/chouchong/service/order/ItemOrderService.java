package com.chouchong.service.order;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;

/**
 * @author linqin
 * @date 2018/7/18
 */
public interface ItemOrderService {

    /**
     * 充值订单列表查询
     *
     * @param nickname 用户昵称
     * @param phone    号码
     * @param orderNo  订单号
     * @param status   订单状态，1-未支付，2-已支付，3-已取消,4-已删除
     * @param payWay   支付方式，1-微信(80)，2-支付宝 (-114)，3-余额(-47)
     * @return
     * @author linqin
     * @date 2018/7/18
     */
    Response itemOrderList(PageQuery pageQuery, String nickname, String phone, Long orderNo, Byte status, Integer payWay);

    /**
     * 删除商品购买订单
     *
     * @param id 商品购买订单id
     * @return
     * @author linqin
     * @date 2018/7/18
     */
    Response delItemApi(Integer id);

    /**
     * 商品购买订单详情
     *
     * @param orderNo 订单号
     * @return
     * @author linqin
     * @date 2018/7/18
     */
    Response itemOrderDetail(Long orderNo);

    /**
     * 订单未查看数量
     *
     * @return
     * @author linqin
     * date 2018/7/18
     */
    Response getOrderCount();
}
