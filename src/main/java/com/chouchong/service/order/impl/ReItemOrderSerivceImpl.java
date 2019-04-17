package com.chouchong.service.order.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.order.ReceiveItemOrderMapper;
import com.chouchong.entity.webUser.ReceiveItemOrder;
import com.chouchong.service.order.ReItemOrderService;
import com.chouchong.service.order.kdapi.ExpressApi;
import com.chouchong.service.order.kdapi.ExpressApi2;
import com.chouchong.service.order.kdapi.KdResult;
import com.chouchong.service.order.kdapi.KdResult2;
import com.chouchong.service.order.vo.LogisticsInfoVo;
import com.chouchong.service.order.vo.Shipping;
import com.chouchong.service.webUser.vo.WebUserInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * @author linqin
 * @date 2018/7/19
 */
@Service
public class ReItemOrderSerivceImpl implements ReItemOrderService {

    @Autowired
    private ReceiveItemOrderMapper receiveItemOrderMapper;

    @Autowired
    private HttpServletRequest httpServletRequest;

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
    @Override
    public Response getReItemOrder(PageQuery pageQuery, String nickname, String phone, Long orderNo, Byte status) {
        //分页
        PageHelper.startPage(pageQuery.getPageNum(),pageQuery.getPageSize());
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        Integer adminId = null;
        if (webUserInfo.getRoleId() == 3){
            adminId = webUserInfo.getSysAdmin().getId();
        }
        //查询列表
        List<Map> maps = receiveItemOrderMapper.selectAll(nickname,phone,orderNo,status,adminId);
        PageInfo pageInfo = new PageInfo<>(maps);
        return ResponseFactory.page(maps,pageInfo.getTotal(),pageInfo.getPages(),
                pageInfo.getPageNum(),pageInfo.getPageSize());
    }


    /**
     * 删除商品提货订单
     *
     * @param id 商品提货订单id
     * @return
     * @author linqin
     * @date 2018/7/19
     */
    @Override
    public Response delReItemOrder(Integer id) {
        int count = receiveItemOrderMapper.updateStatusById(id);
        if (count<1){
            return ResponseFactory.err("状态更新失败");
        }
        return ResponseFactory.suc();
    }

    /**
     * 收货信息
     *
     * @param id 商品提货订单id
     * @return
     * @author linqin
     * @date 2018/7/19
     */
    @Override
    public Response receive(Integer id) {
        //查询订单
        ReceiveItemOrder order = receiveItemOrderMapper.selectByPrimaryKey(id);
        if (order == null){
            return ResponseFactory.err("该订单不存在");
        }
        //将字符串转化为对象
        Shipping receive = JSON.parseObject(order.getReceiveInfo(), new TypeReference<Shipping>() {
		});
        return ResponseFactory.sucData(receive);
    }


    /**
     * 物流信息
     *
     * @param id 商品提货订单id
     * @return
     * @author linqin
     * @date 2018/7/19
     */
    @Override
    public Response logisticsInfo(Integer id) {
        //查询订单
        ReceiveItemOrder order = receiveItemOrderMapper.selectByPrimaryKey(id);
        if (order == null){
            return ResponseFactory.err("该订单不存在");
        }
        if (order.getStatus()<2){
            return ResponseFactory.err("商品未发货");
        }
        //序列化，将物流信息对象转化为json字符串
        LogisticsInfoVo logisticsInfoVo = JSON.parseObject(order.getLogisticsInfo(), new TypeReference<LogisticsInfoVo>() {
        });
        //根据物流公司和订单号查询物流信息
        KdResult2 logisticsInfo = ExpressApi2.checkLogisticsInfo(logisticsInfoVo.getCom(), logisticsInfoVo.getExpressNo());

        Map map = new HashMap();
        map.put("logisticsInfoVo",logisticsInfoVo);
        map.put("logisticsDetail",logisticsInfo.getData());
        //把物流信息返回给前端
        return ResponseFactory.sucData(map);
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
    @Override
    public Response shipments(Integer id,String expressCompany, String com, String expressNo) {
        //查询订单
        ReceiveItemOrder order = receiveItemOrderMapper.selectByPrimaryKey(id);
        if (order == null){
            return ResponseFactory.err("订单不存在或已发货");
        }
        //添加物流信息
       LogisticsInfoVo vo = new LogisticsInfoVo();
        vo.setExpressCompany(expressCompany);
        vo.setCom(com);
        vo.setExpressNo(expressNo);
        order.setLogisticsInfo(JSON.toJSONString(vo));
        order.setStatus((byte)2);
        int update = receiveItemOrderMapper.updateByPrimaryKeySelective(order);
        if (update<1){
            return ResponseFactory.err("物流信息添加失败");
        }
        return ResponseFactory.suc();
    }


    /**
     * 修改物流信息
     *
     * @param id   商品提货订单id
     * @author linqin
     * @date 2018/7/21
     */
    @Override
    public Response updateLogistics(Integer id, LogisticsInfoVo logisticsInfoVo) {
        ReceiveItemOrder itemOrder = new ReceiveItemOrder();
        itemOrder.setId(id);
        //添加物流信息
//        HashSet<LogisticsInfoVo> vo = JSON.parseObject(itemOrder.getLogisticsInfo(), new TypeReference<HashSet<LogisticsInfoVo>>() {
//        });
        LogisticsInfoVo vo = new LogisticsInfoVo();
        if (StringUtils.isNotBlank(logisticsInfoVo.getExpressCompany())){
            vo.setExpressCompany(logisticsInfoVo.getExpressCompany());
        }
        if (StringUtils.isNotBlank(logisticsInfoVo.getExpressNo())){
            vo.setExpressNo(logisticsInfoVo.getExpressNo());
        }
        if (StringUtils.isNotBlank(logisticsInfoVo.getCom())){
            vo.setCom(logisticsInfoVo.getCom());
        }
        itemOrder.setLogisticsInfo(JSON.toJSONString(vo));
        int update = receiveItemOrderMapper.updateByPrimaryKeySelective(itemOrder);
        if (update<1){
            return ResponseFactory.err("物流信息修改失败");
        }
        return  ResponseFactory.sucMsg("物流信息修改成功");
    }
}

