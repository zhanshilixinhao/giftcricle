package com.chouchong.controller.order;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.service.order.VirItemOrderService;
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
@RequestMapping("manage/order/virtual_item")
public class VirItemOrderController {

    @Autowired
    private VirItemOrderService virItemOrderService;


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
    @PostMapping("list")
    public Response getVirList(PageQuery pageQuery, String nickname, String phone, Long orderNo, Byte status, Integer payWay) {
        if (StringUtils.isBlank(nickname)) {
            nickname = null;
        }
        if (StringUtils.isBlank(phone)) {
            phone = null;
        }
        return virItemOrderService.getList(pageQuery, nickname, phone, orderNo, status, payWay);
    }

    /**
     * 删除虚拟商品订单
     *
     * @param id 虚拟商品订单Id
     * @return
     * @author linqin
     * @date 2018/7/19
     */
    @PostMapping("delete")
    public Response delVirItemOrder(Integer id) {
        if (id == null){
            return ResponseFactory.errMissingParameter();
        }
        return virItemOrderService.delVirItemOrder(id);
    }

}
