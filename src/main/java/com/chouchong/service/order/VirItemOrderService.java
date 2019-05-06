package com.chouchong.service.order;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;

/**
 * @author linqin
 * @date 2018/7/19
 */
public interface VirItemOrderService {

    /**
     * 虚拟商品订单列表
     *
     * @param pageQuery 分页
     * @param nickname  用户昵称
     * @param phone     用户号码
     * @param orderNo   订单号
     * @param status    订单状态, 1 未支付 2 已支付 3 已取消，4已删除
     * @param payWay    支付方式，1-微信(80)，2-支付宝 (-114)
     * @return
     * @author linqin
     * @date 2018/7/19
     */
    Response getList(PageQuery pageQuery, String nickname, String phone, Long orderNo, Byte status, Integer payWay);

    /**
     * 删除虚拟商品订单
     *
     * @param id 虚拟商品订单Id
     * @return
     * @author linqin
     * @date 2018/7/19
     */
    Response delVirItemOrder(Integer id);
}
