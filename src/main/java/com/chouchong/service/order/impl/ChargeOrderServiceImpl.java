package com.chouchong.service.order.impl;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.webUser.ChargeOrderMapper;
import com.chouchong.redis.MRedisTemplate;
import com.chouchong.service.order.ChargeOrderService;
import com.chouchong.service.webUser.vo.WebUserInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author linqin
 * @date 2018/7/16
 */
@Service
public class ChargeOrderServiceImpl implements ChargeOrderService {

    @Autowired
    private ChargeOrderMapper chargeOrderMapper;

    @Autowired
    private MRedisTemplate mRedisTemplate;

    @Autowired
    private HttpServletRequest httpServletRequest;

    /**
     * 充值订单列表查询
     * @param page
     * @param nickname 用户昵称
     * @param phone 号码
     * @param orderNo 订单号
     * @param status 充值状态，1-未支付，2-已支付 3-已删除
     * @param payWay  支付方式，1-微信，2-支付宝，
     * @return
     * @author linqin
     * @date 2018/7/16
     */
    @Override
    public Response getList(PageQuery page, String nickname,String phone,Long orderNo,Byte status,Byte payWay) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        List<Map> maps = chargeOrderMapper.selectList(nickname,phone,orderNo,status,payWay);
        PageInfo pageInfo = new PageInfo<>(maps);
        mRedisTemplate.setString("charge" + webUserInfo.getSysAdmin().getId(),String.valueOf(System.currentTimeMillis() / 1000));
        return ResponseFactory.page(maps,pageInfo.getTotal(),pageInfo.getPages(),
                pageInfo.getPageNum(),pageInfo.getPageSize());
    }

    /**
     * 删除充值订单
     *
     * @param id 充值订单id
     * @return
     * @author linqin
     * @date 2018/7/16
     */
    @Override
    public Response delChargeOrder(Integer id) {
        int count = chargeOrderMapper.deleteById(id);
        if (count == 1) {
            return ResponseFactory.sucMsg("删除成功");
        }
        return ResponseFactory.err("删除失败");
    }
}
