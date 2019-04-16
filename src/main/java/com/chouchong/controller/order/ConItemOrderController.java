package com.chouchong.controller.order;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.service.order.ConItemOrderService;
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
@RequestMapping("manage/order/con_item")
public class ConItemOrderController {

    @Autowired
    private ConItemOrderService conItemOrderService;

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
    @PostMapping("list")
    public Response getConItemList(PageQuery pageQuery, String nickname, String phone, Long orderNo, Byte status,
                                   Byte payWay, Byte type) {
        if (StringUtils.isBlank(nickname)) {
            nickname = null;
        }
        if (StringUtils.isBlank(phone)) {
            phone = null;
        }
        return conItemOrderService.getList(pageQuery, nickname, phone, orderNo, status, payWay, type);

    }

    /**
     * 删除寄售台订单
     *
     * @param id 寄售台订单id
     * @return
     * @author linqin
     * @date 2018/7/19
     */
    @PostMapping("delete")
    public Response delConItemList(Integer id) {
        if (id == null){
            return ResponseFactory.errMissingParameter();
        }
        return conItemOrderService.delConItemList(id);
    }


}
