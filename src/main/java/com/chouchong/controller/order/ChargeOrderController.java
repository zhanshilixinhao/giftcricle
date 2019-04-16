package com.chouchong.controller.order;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.service.order.ChargeOrderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author linqin
 * @date 2018/7/16
 */

@RestController
@RequestMapping("manage/order/charge")
public class ChargeOrderController {


    @Autowired
    private ChargeOrderService chargeOrderService;

    /**
     * 充值订单列表查询
     *
     * @param page
     * @param nickname 用户昵称
     * @param phone    号码
     * @param orderNo  订单号
     * @param status   充值状态，1-未支付，2-已支付 ，3-已删除
     * @param payWay   支付方式，1-微信，2-支付宝，
     * @return
     * @author linqin
     * @date 2018/7/16
     */
    @PostMapping("list")
    public Response getList(PageQuery page, String nickname, String phone, Long orderNo, Byte status, Byte payWay) {
        if (StringUtils.isBlank(nickname)) {
            nickname = null;
        }
        if (StringUtils.isBlank(phone)) {
            phone = null;
        }
        return chargeOrderService.getList(page, nickname, phone, orderNo, status, payWay);
    }

    /**
     * 删除充值订单
     *
     * @param id 充值订单id
     * @return
     * @author linqin
     * @date 2018/7/16
     */
    @PostMapping("delete")
    public Response delChargeOrder(Integer id) {
        if (id == null){
            return ResponseFactory.errMissingParameter();
        }
        return chargeOrderService.delChargeOrder(id);
    }

}
