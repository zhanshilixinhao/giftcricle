package com.chouchong.service.order.impl;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.order.VirtualItemOrderMapper;
import com.chouchong.service.order.VirItemOrderService;
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
public class VirItemOrderServiceImpl implements VirItemOrderService {

    @Autowired
    private VirtualItemOrderMapper virtualItemOrderMapper;

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
    @Override
    public Response getList(PageQuery pageQuery, String nickname, String phone, Long orderNo, Byte status, Byte payWay) {
        //分页
        PageHelper.startPage(pageQuery.getPageNum(),pageQuery.getPageSize());
        //查询虚拟商品订单列表
        List<Map> maps = virtualItemOrderMapper.selectAll(nickname,phone,orderNo,status,payWay);
        PageInfo pageInfo = new PageInfo<>(maps);
        return ResponseFactory.page(maps,pageInfo.getTotal(),pageInfo.getPages(),
                pageInfo.getPageNum(),pageInfo.getPageSize());
    }

    /**
     * 删除虚拟商品订单
     *
     * @param id 虚拟商品订单Id
     * @return
     * @author linqin
     * @date 2018/7/19
     */
    @Override
    public Response delVirItemOrder(Integer id) {
        int count = virtualItemOrderMapper.updateStatusById(id);
        if (count < 1){
            return ResponseFactory.err("更新状态失败");
        }
        return ResponseFactory.suc();
    }


}
