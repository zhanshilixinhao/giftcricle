package com.chouchong.service.order;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;

/**
 * @author linqin
 * @date 2018/7/19
 */
public interface ConItemOrderService {
    /**
     * 寄售台商品订单列表
     *
     * @param pageQuery 分页
     * @param nickname  用户昵称
     * @param phone     用户电话
     * @param orderNo   订单号
     * @param status    订单状态 1 未支付 2 已支付 ,3 已取消,4 已删除
     * @param payWay    支付方式 1-微信(80)，2-支付宝 (-114)，3-余额(-47)，
     * @param type      1 商品 2 虚拟商品（没有)，3 优惠券
     * @return
     * @author linqin
     * @date 2018/7/19
     */
    Response getList(PageQuery pageQuery, String nickname, String phone, Long orderNo, Byte status, Byte payWay, Byte type);

    /**
     * 删除寄售台订单
     *
     * @param id 寄售台订单id
     * @return
     * @author linqin
     * @date 2018/7/19
     */
    Response delConItemList(Integer id);
}
