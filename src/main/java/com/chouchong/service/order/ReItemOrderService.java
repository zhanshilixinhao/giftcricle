package com.chouchong.service.order;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.service.order.vo.LogisticsInfoVo;

/**
 * @author linqin
 * @date 2018/7/19
 */
public interface ReItemOrderService {
    /**
     * 商品提货订单列表
     *
     * @param pageQuery 分页
     * @param nickname  用户昵称
     * @param phone     用户电话
     * @param orderNo   订单号
     * @param status    订单状态，1-待发货；2-已发货；3-已收货,待评价，4-已评价,5-取消，6-删除
     * @return
     * @author linqin
     * @date 2018/7/19
     */
    Response getReItemOrder(PageQuery pageQuery, String nickname, String phone, Long orderNo, Byte status);

    /**
     * 删除商品提货订单
     *
     * @param id 商品提货订单id
     * @return
     * @author linqin
     * @date 2018/7/19
     */
    Response delReItemOrder(Integer id);

    /**
     * 收货信息
     *
     * @param id 商品提货订单id
     * @return
     * @author linqin
     * @date 2018/7/19
     */
    Response receive(Integer id);

    /**
     * 物流信息
     *
     * @param id 商品提货订单id
     * @return
     * @author linqin
     * @date 2018/7/19
     */
    Response logisticsInfo(Integer id);

    /**
     * 点击发货
     *
     * @param id             商品提货订单id
     * @param expressCompany 快递公司
     * @param com            快递公司代码
     * @param expressNo      快递单号
     * @return
     * @author linqin
     * @date 2018/7/21
     */
    Response shipments(Integer id,String expressCompany, String com, String expressNo);

    /**
     * 修改物流信息
     *
     * @param id   商品提货订单id
     * @author linqin
     * @date 2018/7/21
     */
    Response updateLogistics(Integer id, LogisticsInfoVo logisticsInfoVo);
}
