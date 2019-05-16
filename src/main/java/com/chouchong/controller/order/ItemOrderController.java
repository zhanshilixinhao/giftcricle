package com.chouchong.controller.order;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.service.order.ItemOrderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author linqin
 * @date 2018/7/18
 */
@RestController
@RequestMapping("manage/order/item")
public class ItemOrderController {

    @Autowired
    private ItemOrderService itemOrderService;


    /**
     * 订单未查看数量
     *
     * @return
     * @author linqin
     * date 2018/7/18
     */
    @PostMapping("orderCount")
    public Response getOrderCount() {
        return itemOrderService.getOrderCount();
    }


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
    @PostMapping("list")
    public Response itemOrderList(PageQuery pageQuery, String nickname, String phone, Long orderNo, Byte status, Integer payWay) {
        if (StringUtils.isBlank(nickname)) {
            nickname = null;
        }
        if (StringUtils.isBlank(phone)) {
            phone = null;
        }
        return itemOrderService.itemOrderList(pageQuery, nickname, phone, orderNo, status, payWay);
    }


    /**
     * 删除商品购买订单
     *
     * @param id 商品购买订单id
     * @return
     * @author linqin
     * @date 2018/7/18
     */
    @PostMapping("delete")
    public Response delItemApi(Integer id) {
        if (id == null) {
            return ResponseFactory.errMissingParameter();
        }
        return itemOrderService.delItemApi(id);
    }

    /**
     * 商品购买订单详情
     *
     * @param orderNo 订单号
     * @return
     * @author linqin
     * @date 2018/7/18
     */
    @PostMapping("detail")
    public Response itemOrderDetail(Long orderNo) {
        if (orderNo == null) {
            return ResponseFactory.errMissingParameter();
        }
        return itemOrderService.itemOrderDetail(orderNo);
    }

}
