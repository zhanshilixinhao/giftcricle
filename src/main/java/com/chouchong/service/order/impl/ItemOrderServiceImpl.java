package com.chouchong.service.order.impl;

import com.alibaba.druid.util.StringUtils;
import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.gift.item.ItemCommentMapper;
import com.chouchong.dao.order.ItemOrderDetailMapper;
import com.chouchong.dao.order.ItemOrderMapper;
import com.chouchong.dao.order.ReceiveItemOrderMapper;
import com.chouchong.dao.order.VirtualItemOrderMapper;
import com.chouchong.dao.webUser.ChargeOrderMapper;
import com.chouchong.entity.gift.item.ItemComment;
import com.chouchong.entity.webUser.ItemOrder;
import com.chouchong.redis.MRedisTemplate;
import com.chouchong.service.order.ItemOrderService;
import com.chouchong.service.order.vo.ItemOrderVo;
import com.chouchong.service.order.vo.OrderCountVo;
import com.chouchong.service.webUser.vo.WebUserInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author linqin
 * @date 2018/7/18
 */
@Service
public class ItemOrderServiceImpl implements ItemOrderService {

    @Autowired
    private ItemOrderMapper itemOrderMapper;

    @Autowired
    private ItemCommentMapper itemCommentMapper;

    @Autowired
    private ItemOrderDetailMapper itemOrderDetailMapper;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private MRedisTemplate mRedisTemplate;

    @Autowired
    private ChargeOrderMapper chargeOrderMapper;

    @Autowired
    private VirtualItemOrderMapper virtualItemOrderMapper;

    @Autowired
    private ReceiveItemOrderMapper receiveItemOrderMapper;

    /**
     * 订单未查看数量
     *
     * @return
     * @author linqin
     * date 2018/7/18
     */
    @Override
    public Response getOrderCount() {
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        // 平台商登录
        Integer adminId = null;
        if (webUserInfo.getRoleId() == 3) {
            adminId = webUserInfo.getSysAdmin().getId();
        }
        OrderCountVo countVo = new OrderCountVo();
        // 未查看的评价数量
        String star = mRedisTemplate.getString("comment" + webUserInfo.getSysAdmin().getId());
        Long start =Long.parseLong(star);
        int count = itemCommentMapper.selectUnredCount(adminId, start);
        countVo.setCommentCount(count);

        // 未查看的充值订单数量
        String ch = mRedisTemplate.getString("charge" + webUserInfo.getSysAdmin().getId());
        Long charge = Long.parseLong(ch);
        count = chargeOrderMapper.selectUnredCount(charge);
        countVo.setChargeCount(count);

        // 未查看的购买商品数量
        String string = mRedisTemplate.getString("item" + webUserInfo.getSysAdmin().getId());
        Long str = Long.parseLong(string);
        count = itemOrderMapper.selectUnredCount(adminId, str);
        countVo.setItemCount(count);

        // 未查看虚拟商品
        String vi = mRedisTemplate.getString("viItem" + webUserInfo.getSysAdmin().getId());
        Long viItem = Long.parseLong(vi);
        count = virtualItemOrderMapper.selectUnredCount(viItem);
        countVo.setViItemCount(count);
        // 未查看提货订单
        String re = mRedisTemplate.getString("reItem" + webUserInfo.getSysAdmin().getId());
        Long reTime = Long.parseLong(re);
        count = receiveItemOrderMapper.selectUnredCount(adminId,reTime );
        countVo.setReCount(count);
        return ResponseFactory.sucData(countVo);
    }


    /**
     * 商品订单列表查询
     *
     * @param nickname 用户昵称
     * @param phone    号码
     * @param orderNo  订单号
     * @param status   订单状态，1-未支付，2-已支付，3-已取消,4-已删除
     * @param payWay   支付方式，1-微信(80)，2-支付宝 (-114)，3-余额(-47)，
     * @return
     * @author linqin
     * @date 2018/7/18
     */
    @Override
    public Response itemOrderList(PageQuery pageQuery, String nickname, String phone, Long orderNo, Byte status, Integer payWay) {
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize());
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        // 平台商登录
        Integer adminId = null;
        if (webUserInfo.getRoleId() == 3) {
            adminId = webUserInfo.getSysAdmin().getId();
        }
        List<ItemOrderVo> maps = itemOrderMapper.selectList(nickname, phone, orderNo, status, payWay, adminId);
        PageInfo pageInfo = new PageInfo<>(maps);
        // 添加最后查看的时间
        mRedisTemplate.setString("item" + webUserInfo.getSysAdmin().getId(), String.valueOf(System.currentTimeMillis() / 1000));
        return ResponseFactory.page(maps, pageInfo.getTotal(), pageInfo.getPages(),
                pageInfo.getPageNum(), pageInfo.getPageSize());
    }


    /**
     * 删除商品购买订单
     *
     * @param id 商品购买订单id
     * @return
     * @author linqin
     * @date 2018/7/18
     */
    @Override
    public Response delItemApi(Integer id) {
        ItemOrder itemOrder = itemOrderMapper.selectByPrimaryKey(id);
        if (itemOrder == null) {
            return ResponseFactory.err("没有该订单");
        }
        int count = itemOrderMapper.updateStatusById(id);
        if (count < 1) {
            return ResponseFactory.err("状态更新失败");
        }
        int update = itemOrderDetailMapper.updateStatusById(itemOrder.getOrderNo());
        if (update < 1) {
            return ResponseFactory.err("状态更新失败");
        }
        return ResponseFactory.suc();
    }


    /**
     * 商品购买订单详情
     *
     * @param orderNo 订单号
     * @return
     * @author linqin
     * @date 2018/7/18
     */
    @Override
    public Response itemOrderDetail(Long orderNo) {
        List<Map> maps = itemOrderDetailMapper.selectByOrderNo(orderNo);
        return ResponseFactory.sucData(maps);
    }


}
