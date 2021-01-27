package com.chouchong.service.v4.impl;
import	java.util.stream.Collectors;

import com.alibaba.fastjson.JSONObject;
import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.v3.StoreMapper;
import com.chouchong.dao.v4.ActivityMapper;
import com.chouchong.dao.v4.CoMapper;
import com.chouchong.dao.v4.MemberEventCouponMapper;
import com.chouchong.entity.v3.ElectronicCoupons;
import com.chouchong.entity.v3.MemberEvent;
import com.chouchong.entity.v4.MemberEventCoupon;
import com.chouchong.service.v3.vo.SpecialVo;
import com.chouchong.service.v3.vo.StoreVo;
import com.chouchong.service.v4.ActivityService;
import com.chouchong.service.webUser.vo.WebUserInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.chouchong.entity.v3.MemberEvent.ADMIN_ID;
import static com.chouchong.entity.v3.MemberEvent.TITLE;
import static com.chouchong.entity.v4.ElUserCouponList.CREATED;
import static com.chouchong.entity.v4.MemberEventCoupon.EVENT_ID;

/**
 * @Description:
 * @Author Lxh
 * @Date 2020/9/25 14:15
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    @Resource
    private HttpServletRequest httpServletRequest;

    @Resource
    private ActivityMapper activityMapper;

    @Resource
    private MemberEventCouponMapper memberEventCouponMapper;

    @Resource
    private CoMapper coMapper;

    @Resource
    private StoreMapper storeMapper;

    /**
     * @param： event
     * @Description: 后台添加活动
     * @Author: LxH
     * @Date: 2020/9/25 14:18
     */
    @Override
    @Transactional
    public Response addEvent(MemberEvent event, String couponJson, String storeIds) {
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        List<SpecialVo> specialVos = JSONObject.parseArray(couponJson, SpecialVo.class);
        MemberEvent memberEvent = new MemberEvent();
        memberEvent.setTitle(event.getTitle());
        if (event.getSummary() == null) {
            memberEvent.setSummary(event.getTitle());
        } else {
            memberEvent.setSummary(event.getSummary());
        }
        memberEvent.setRechargeMoney(event.getRechargeMoney());
        if ((event.getSendMoney() == null)) {
            memberEvent.setSendMoney(new BigDecimal("0"));
        } else {
            memberEvent.setSendMoney(event.getSendMoney());
        }
        memberEvent.setAdminId(webUserInfo.getSysAdmin().getId());
        memberEvent.setStoreIds(storeIds);
        memberEvent.setStatus(event.getStatus());
        memberEvent.setCreated(new Date());
        memberEvent.setUpdated(new Date());
        if (event.getScale() != null) {
            float num = (float) (Math.round(event.getScale() * 0.01 * 1000)) / 1000;
            memberEvent.setScale(num);
        }
        int insert = activityMapper.insertSelective(memberEvent);
        if (insert < 1) {
            return ResponseFactory.err("添加失败！");
        }
        for (SpecialVo specialVo : specialVos) {
            MemberEventCoupon memberEventCoupon = new MemberEventCoupon();
            memberEventCoupon.setCouponId(specialVo.getCouponId()).setEventId(memberEvent.getId()).setQuantity(specialVo.getQuantity());
            int insertSelective = memberEventCouponMapper.insertSelective(memberEventCoupon);
            if (insertSelective < 1) {
                return ResponseFactory.err("添加失败！");
            }
        }
        return ResponseFactory.sucData(memberEvent.getId());
    }

    /**
     * @param： event
     * @param： couponJson
     * @Description: 修改活动
     * @Author: LxH
     * @Date: 2020/9/25 17:34
     */
    @Override
    @Transactional
    public Response updateEvent(MemberEvent event, String couponJson) {
        List<SpecialVo> specialVos = JSONObject.parseArray(couponJson, SpecialVo.class);
        MemberEvent memberEvent = activityMapper.selectByPrimaryKey(event.getId());
        if (memberEvent == null) {
            return ResponseFactory.err("该活动不存在");
        }
        event.setUpdated(new Date());
        int i = activityMapper.updateByPrimaryKeySelective(event);
        if (i < 1) {
            return ResponseFactory.err("修改失败！");
        }
        Example example = new Example(MemberEventCoupon.class);
        example.createCriteria().andEqualTo("eventId", event.getId());
        List<MemberEventCoupon> memberEventCoupons = memberEventCouponMapper.selectByExample(example);
        for (MemberEventCoupon memberEventCoupon : memberEventCoupons) {
            memberEventCouponMapper.deleteByPrimaryKey(memberEventCoupon.getId());
        }
        for (SpecialVo specialVo : specialVos) {
            MemberEventCoupon memberEventCoupon = new MemberEventCoupon();
            memberEventCoupon.setEventId(event.getId()).setCouponId(specialVo.getCouponId()).setQuantity(specialVo.getQuantity());
            int i1 = memberEventCouponMapper.insertSelective(memberEventCoupon);
            if (i1 < 1) {
                return ResponseFactory.err("修改失败！");
            }
        }
        return ResponseFactory.sucMsg("修改成功");
    }

    /**
     * @param: eventId
     * @Description: 删除活动
     * @Author: LxH
     * @Date: 2020/9/27 14:23
     */
    @Override
    public Response deleteEvent(Integer eventId) {
        activityMapper.deleteByPrimaryKey(eventId);
        Example example = new Example(MemberEventCoupon.class);
        example.createCriteria().andEqualTo("eventId", eventId);
        List<MemberEventCoupon> memberEventCoupons = memberEventCouponMapper.selectByExample(example);
        for (MemberEventCoupon memberEventCoupon : memberEventCoupons) {
            memberEventCouponMapper.deleteByPrimaryKey(memberEventCoupon.getId());
        }
        return ResponseFactory.sucMsg("活动删除成功");
    }

    /**
     * @Description: 查询活动列表
     * @Author: LxH
     * @Date: 2020/9/27 14:47
     */
    @Override
    public Response selectList(String title, PageQuery page) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        // 平台商登录
        Integer adminId = null;
        // 超级管理员和商家
        if (webUserInfo.getRoleId() != 2) {
            if (webUserInfo.getRoleId() == 3) {
                adminId = webUserInfo.getSysAdmin().getId();
            }
            Example example = new Example(MemberEvent.class);
            example.orderBy(CREATED).desc();
            Example.Criteria criteria = example.createCriteria();
            if (adminId != null) {
                criteria.andEqualTo(ADMIN_ID, adminId);
            }
            if (title != null) {
                criteria.andLike(TITLE, "%" + title + "%");
            }
            List<MemberEvent> memberEvents = activityMapper.selectByExample(example);
            for (MemberEvent memberEvent : memberEvents) {
                Example example1 = new Example(MemberEventCoupon.class);
                example1.createCriteria().andEqualTo(EVENT_ID, memberEvent.getId());
                List<MemberEventCoupon> memberEventCoupons = memberEventCouponMapper.selectByExample(example1);
                if (memberEventCoupons.size()>0) {
                    for (MemberEventCoupon memberEventCoupon : memberEventCoupons) {
                        ElectronicCoupons electronicCoupons = coMapper.selectByPrimaryKey(memberEventCoupon.getCouponId());
                        if (electronicCoupons !=null) {
                            memberEventCoupon.setCouponName(electronicCoupons.getTitle());
                        }
                    }
                }
                    String[] split = memberEvent.getStoreIds().split(",");
                    ArrayList<StoreVo> stores = new ArrayList<>();
                    for (String s : split) {
                        StoreVo store = storeMapper.selectById(Integer.parseInt(s));
                        if (store != null) {
                            stores.add(store);
                        }
                    }
                    memberEvent.setStoreVos(stores);
                    memberEvent.setMemberEventCouponsList(memberEventCoupons);
            }
            PageInfo<MemberEvent> pageInfo = new PageInfo<>(memberEvents);
            return ResponseFactory.page(pageInfo, pageInfo.getTotal(), pageInfo.getPages(),
                    pageInfo.getPageNum(), pageInfo.getPageSize());
        }
        return null;
    }

    /**
     * @param: storeIds
     * @Description: 获取门店对应的活动
     * @Author: LxH
     * @Date: 2020/9/29 16:09
     */
    @Override
    public Response getEventList(String eventIds) {
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        // 平台商登录
        Integer adminId = null;
        // 超级管理员和商家
        if (webUserInfo.getRoleId() != 2) {
            if (webUserInfo.getRoleId() == 3) {
                adminId = webUserInfo.getSysAdmin().getId();
            }
            Example example = new Example(MemberEvent.class);
            example.createCriteria().andEqualTo(ADMIN_ID, adminId);
            //创建的所有活动
            List<MemberEvent> memberEvents = activityMapper.selectByExample(example);
            String[] split = eventIds.split(",");
            ArrayList<MemberEvent> events = new ArrayList<>();
            for (MemberEvent memberEvent : memberEvents) {
                for (String s : split) {
                        if (memberEvent.getStoreIds().contains(s)) {
                            events.add(memberEvent);
                    }
                }
            }
            //去重
            List<MemberEvent> collect = events.stream().distinct().collect(Collectors.toList());
            return ResponseFactory.sucData(collect);
            //return ResponseFactory.sucData(memberEvents);
        }
        return null;
    }
}
