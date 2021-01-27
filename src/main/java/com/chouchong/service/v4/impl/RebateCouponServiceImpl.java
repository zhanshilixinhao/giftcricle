package com.chouchong.service.v4.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chouchong.common.ErrorCode;
import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.v3.StoreMapper;
import com.chouchong.dao.v4.RebateCouponBeInvitedMapper;
import com.chouchong.dao.v4.RebateCouponLogMapper;
import com.chouchong.dao.v4.RebateCouponMapper;
import com.chouchong.entity.v3.Store;
import com.chouchong.entity.v4.RebateCoupon;
import com.chouchong.entity.v4.RebateCouponBeInvited;
import com.chouchong.entity.v4.RebateCouponLog;
import com.chouchong.entity.v4.RebateCouponManage;
import com.chouchong.exception.ServiceException;
import com.chouchong.service.v4.RebateCouponService;
import com.chouchong.service.v4.vo.RebateCouponLogVo;
import com.chouchong.service.webUser.vo.WebUserInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import static com.chouchong.entity.v4.RebateCouponBeInvited.REBATE_COUPON_ID;

/**
 * @Description:
 * @Author Lxh
 * @Date 2020/10/21 16:19
 */
@Service
public class RebateCouponServiceImpl implements RebateCouponService {

    @Resource
    private HttpServletRequest httpServletRequest;

    @Resource
    private RebateCouponMapper rebateCouponMapper;

    @Resource
    private RebateCouponBeInvitedMapper rebateCouponBeInvitedMapper;

    @Resource
    private StoreMapper storeMapper;

    @Resource
    private RebateCouponLogMapper rebateCouponLogMapper;

    /**
     * @param： id
     * @Description: 二维码展示详情
     * @Author: LxH
     * @Date: 2020/10/21 16:24
     */
    @Override
    public Response detailByQrcode(long id) {
        RebateCoupon rebateCoupon = rebateCouponMapper.selectByPrimaryKey(id);
        if (StringUtils.isBlank(rebateCoupon.toString())) {
            return ResponseFactory.err("折扣卷不存在！！");
        }
        Store store = storeMapper.selectByPrimaryKey(rebateCoupon.getStoreId());
        rebateCoupon.setStoreName(store.getName()).setStoreAddress(store.getArea()+store.getAddress());
        return ResponseFactory.sucData(rebateCoupon);
    }

    /**
     * @param: rebateCouponId
     * @Description: 核销用户折扣卷
     * @Author: LxH
     * @Date: 2020/10/21 16:43
     */
    @Override
    @Transactional
    public Response userRebateCoupon(long rebateCouponId) {
        RebateCoupon rebateCoupon = rebateCouponMapper.selectByPrimaryKey(rebateCouponId);
        if (StringUtils.isBlank(rebateCoupon.toString())) {
            return ResponseFactory.err("折扣卷不存在！！");
        }
        RebateCouponLog rebateCouponLog = new RebateCouponLog();
        rebateCouponLog.setRebate(rebateCoupon.getRebate().multiply(new BigDecimal("100")));
        String s = JSONObject.toJSONString(rebateCoupon);
        Integer userId = rebateCoupon.getUserId();
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        Store store = storeMapper.selectByAdminId(webUserInfo.getSysAdmin().getId());
        if (store == null) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "门店出错");
        }
        if (!rebateCoupon.getStoreId().equals(store.getId())) {
            return ResponseFactory.err("无权核销!");
        }
        Example example = new Example(RebateCouponBeInvited.class);
        example.createCriteria().andEqualTo(REBATE_COUPON_ID, rebateCoupon.getId());
        rebateCouponBeInvitedMapper.deleteByExample(example);
        rebateCouponMapper.deleteByPrimaryKey(rebateCoupon.getId());
        //添加核销折扣卷记录

        rebateCouponLog.setUserId(userId).
                setAdminId(webUserInfo.getSysAdmin().getId()).setRebateCouponId(rebateCouponId).setStoreId(store.getId()).
                setDetail(s).setCreated(new Date()).setCreatAdminId(webUserInfo.getSysAdmin().getCreateAdminId());
        rebateCouponLogMapper.insertSelective(rebateCouponLog);
        return ResponseFactory.sucMsg("折扣卷核销成功！");
    }

    /**
     * @param: type
     * @param: phone
     * @Description: 根据条件查询折扣卷核销日志  type 0.今天 1.本周 2.本月 3.全部
     * @Author: LxH
     * @Date: 2020/10/22 10:33
     */
    @Override
    public Response findRebateCouponLog(Integer type, String phone, PageQuery page) {
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        ArrayList<RebateCouponLogVo> logVos = new ArrayList<>();
        if (type == null) {
            type = 3;
        }
        switch (type) {
            case 0 :
            if (webUserInfo.getRoleId() ==3) {
                logVos =rebateCouponLogMapper.findLogByDay(phone,null,webUserInfo.getSysAdmin().getId());
                setLogVos(logVos);
                PageInfo pageInfo = new PageInfo<>(logVos);
                return ResponseFactory.page(pageInfo, pageInfo.getTotal(), pageInfo.getPages(),
                        pageInfo.getPageNum(), pageInfo.getPageSize());
            } else {
                logVos =rebateCouponLogMapper.findLogByDay(phone,webUserInfo.getSysAdmin().getId(),null);
                setLogVos(logVos);
                PageInfo pageInfo = new PageInfo<>(logVos);
                return ResponseFactory.page(pageInfo, pageInfo.getTotal(), pageInfo.getPages(),
                        pageInfo.getPageNum(), pageInfo.getPageSize());
            }
            case 1 :
                if (webUserInfo.getRoleId() ==3) {
                    logVos =rebateCouponLogMapper.findLogByWeek(phone,null,webUserInfo.getSysAdmin().getId());
                    setLogVos(logVos);
                    PageInfo pageInfo = new PageInfo<>(logVos);
                    return ResponseFactory.page(pageInfo, pageInfo.getTotal(), pageInfo.getPages(),
                            pageInfo.getPageNum(), pageInfo.getPageSize());
                } else {
                    logVos =rebateCouponLogMapper.findLogByWeek(phone,webUserInfo.getSysAdmin().getId(),null);
                    setLogVos(logVos);
                    PageInfo pageInfo = new PageInfo<>(logVos);
                    return ResponseFactory.page(pageInfo, pageInfo.getTotal(), pageInfo.getPages(),
                            pageInfo.getPageNum(), pageInfo.getPageSize());
                }
            case 2 :
                if (webUserInfo.getRoleId() ==3) {
                    logVos =rebateCouponLogMapper.findLogByMonth(phone,null,webUserInfo.getSysAdmin().getId());
                    setLogVos(logVos);
                    PageInfo pageInfo = new PageInfo<>(logVos);
                    return ResponseFactory.page(pageInfo, pageInfo.getTotal(), pageInfo.getPages(),
                            pageInfo.getPageNum(), pageInfo.getPageSize());
                } else {
                    logVos =rebateCouponLogMapper.findLogByMonth(phone,webUserInfo.getSysAdmin().getId(),null);
                    setLogVos(logVos);
                    PageInfo pageInfo = new PageInfo<>(logVos);
                    return ResponseFactory.page(pageInfo, pageInfo.getTotal(), pageInfo.getPages(),
                            pageInfo.getPageNum(), pageInfo.getPageSize());
                }
            case 3 :
                if (webUserInfo.getRoleId() ==3) {
                    logVos =rebateCouponLogMapper.findAllLog2(phone,null,webUserInfo.getSysAdmin().getId());
                    setLogVos(logVos);
                    PageInfo pageInfo = new PageInfo<>(logVos);
                    return ResponseFactory.page(pageInfo, pageInfo.getTotal(), pageInfo.getPages(),
                            pageInfo.getPageNum(), pageInfo.getPageSize());
                } else {
                    logVos =rebateCouponLogMapper.findAllLog(phone,webUserInfo.getSysAdmin().getId(),null);
                    setLogVos(logVos);
                    PageInfo pageInfo = new PageInfo<>(logVos);
                    return ResponseFactory.page(pageInfo, pageInfo.getTotal(), pageInfo.getPages(),
                            pageInfo.getPageNum(), pageInfo.getPageSize());
                }
            default:
                return ResponseFactory.err("未知选项");
        }
    }


    private void setLogVos(ArrayList<RebateCouponLogVo> logVos) {
        for (RebateCouponLogVo logVo : logVos) {
            Example example = new Example(RebateCouponLog.class);
            example.createCriteria().andEqualTo("rebateCouponId", logVo.getRebateCouponId());
            RebateCouponLog rebateCouponLog = rebateCouponLogMapper.selectOneByExample(example);
            if (rebateCouponLog != null) {
                RebateCoupon rebateCoupon = JSON.parseObject(rebateCouponLog.getDetail(), RebateCoupon.class);
                logVo.setRebate(rebateCoupon.getRebate().multiply(new BigDecimal("100")));
            }
        }
    }
}
