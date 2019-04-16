package com.chouchong.controller.order;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.service.order.ReItemOrderService;
import com.chouchong.service.order.vo.LogisticsInfoVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author linqin
 * @date 2018/7/19
 */
@RestController
@RequestMapping("manage/order/re_item")
public class ReItemOrderController {

    @Autowired
    private ReItemOrderService reItemOrderService;

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
    @PostMapping("list")
    public Response getReItemOrder(PageQuery pageQuery, String nickname, String phone, Long orderNo, Byte status) {
        if (StringUtils.isBlank(nickname)) {
            nickname = null;
        }
        if (StringUtils.isBlank(phone)) {
            phone = null;
        }
        return reItemOrderService.getReItemOrder(pageQuery, nickname, phone, orderNo, status);
    }


    /**
     * 删除商品提货订单
     *
     * @param id 商品提货订单id
     * @return
     * @author linqin
     * @date 2018/7/19
     */
    @PostMapping("delete")
    public Response delReItemOrder(Integer id) {
        if (id == null) {
            return ResponseFactory.errMissingParameter();
        }
        return reItemOrderService.delReItemOrder(id);
    }

    /**
     * 收货信息
     *
     * @param id 商品提货订单id
     * @return
     * @author linqin
     * @date 2018/7/19
     */
    @PostMapping("receive")
    public Response receiveInfo(Integer id) {
        if (id == null) {
            return ResponseFactory.errMissingParameter();
        }
        return reItemOrderService.receive(id);
    }

    /**
     * 物流信息
     *
     * @param id 商品提货订单id
     * @return
     * @author linqin
     * @date 2018/7/19
     */
    @PostMapping("logistics")
    public Response logisticsInfo(Integer id) {
        if (id == null) {
            return ResponseFactory.errMissingParameter();
        }
        return reItemOrderService.logisticsInfo(id);
    }


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
    @PostMapping("shipments")
    public Response shipments(Integer id, String expressCompany, String com, String expressNo) {
        if (id == null) {
            return ResponseFactory.errMissingParameter();
        }
        if (StringUtils.isBlank(expressCompany)) {
            return ResponseFactory.errMissingParameter();
        }
        if (StringUtils.isBlank(com)) {
            return ResponseFactory.errMissingParameter();
        }
        if (StringUtils.isBlank(expressNo)) {
            return ResponseFactory.errMissingParameter();
        }
        return reItemOrderService.shipments(id, expressCompany, com, expressNo);
    }


    /**
     * 修改物流信息
     *
     * @param id   商品提货订单id
     * @author linqin
     * @date 2018/7/21
     */
    @PostMapping("update")
    public Response updateLogistics(Integer id, LogisticsInfoVo logisticsInfoVo) {
        if (id == null) {
            return ResponseFactory.errMissingParameter();
        }
        return reItemOrderService.updateLogistics(id, logisticsInfoVo);
    }

}
