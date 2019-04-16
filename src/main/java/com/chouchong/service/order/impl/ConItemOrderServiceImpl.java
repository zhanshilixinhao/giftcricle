package com.chouchong.service.order.impl;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.order.ConsignmentOrderMapper;
import com.chouchong.service.order.ConItemOrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author linqin
 * @date 2018/7/19
 */
@Service
public class ConItemOrderServiceImpl implements ConItemOrderService {

    @Autowired
    private ConsignmentOrderMapper consignmentOrderMapper;

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
    @Override
    public Response getList(PageQuery pageQuery, String nickname, String phone, Long orderNo, Byte status,
                            Byte payWay, Byte type) {
        //分页
        PageHelper.startPage(pageQuery.getPageNum(),pageQuery.getPageSize());
        //查询列表
        List<Map> maps = consignmentOrderMapper.selectAll(nickname,phone,orderNo,status,payWay,type);
        PageInfo pageInfo = new PageInfo<>(maps);
        return ResponseFactory.page(maps,pageInfo.getTotal(),pageInfo.getPages(),
                pageInfo.getPageNum(),pageInfo.getPageSize());
    }

    /**
     * 删除寄售台订单
     *
     * @param id 寄售台订单id
     * @return
     * @author linqin
     * @date 2018/7/19
     */
    @Override
    public Response delConItemList(Integer id) {
        int count = consignmentOrderMapper.updateById(id);
        if (count <1){
            return ResponseFactory.err("状态更新失败");
        }
        return ResponseFactory.suc();
    }


//    public static void main(String[] args) {
//        int i = 24656;
//        byte b = (byte)i;
//        int j= b;
//        System.out.println(i);
//        System.out.println(j);
//    }
}
