package com.chouchong.service.v3.impl;

import com.alibaba.fastjson.JSON;
import com.chouchong.common.*;
import com.chouchong.dao.iwant.appUser.AppUserMapper;
import com.chouchong.dao.v3.ElCouponSendMapper;
import com.chouchong.dao.v3.ElUserCouponMapper;
import com.chouchong.dao.v3.ElectronicCouponsMapper;
import com.chouchong.dao.v3.StoreMapper;
import com.chouchong.dao.webUser.SysAdminMapper;
import com.chouchong.entity.iwant.appUser.AppUser;
import com.chouchong.entity.v3.*;
import com.chouchong.exception.ServiceException;
import com.chouchong.service.v3.ElCouponService;
import com.chouchong.service.v3.vo.*;
import com.chouchong.service.webUser.vo.WebUserInfo;
import com.chouchong.utils.TimeUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author linqin
 * @date 2020/2/11 16:15
 */
@Service
@Slf4j
public class ElCouponServiceImpl implements ElCouponService {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private ElectronicCouponsMapper electronicCouponsMapper;

    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    private AppUserMapper appUserMapper;

    @Autowired
    private OrderHelper orderHelper;

    @Autowired
    private ElUserCouponMapper elUserCouponMapper;

    @Autowired
    private ElCouponSendMapper elCouponSendMapper;

    @Autowired
    private SysAdminMapper sysAdminMapper;

    /**
     * 获取优惠券列表
     *
     * @param title 标题
     * @param page
     * @return
     */
    @Override
    public Response getElCouponList(String title, PageQuery page) {
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        // 平台商登录
        Integer adminId = webUserInfo.getSysAdmin().getId();
        if (webUserInfo.getRoleId() == 3) {
            PageHelper.startPage(page.getPageNum(), page.getPageSize());
            List<ElectronicCoupons> coupons = electronicCouponsMapper.selectBySearch(adminId, title);
            if (!CollectionUtils.isEmpty(coupons)) {
                for (ElectronicCoupons coupon : coupons) {
                    List<StoreVo> stores = new ArrayList<>();
                    if (!StringUtils.isEmpty(coupon.getStoreIds())) {
                        String[] split = coupon.getStoreIds().split(",");
                        for (String s : split) {
                            StoreVo store = storeMapper.selectById(Integer.parseInt(s));
                            stores.add(store);
                        }
                    }
                    coupon.setStoreVos(stores);
                }
            }
            PageInfo pageInfo = new PageInfo<>(coupons);
            return ResponseFactory.page(coupons, pageInfo.getTotal(), pageInfo.getPages(),
                    pageInfo.getPageNum(), pageInfo.getPageSize());
        } else {
            // 门店
            // 创建者adminId(商家adminId)
            Integer createdAdminId = webUserInfo.getSysAdmin().getCreateAdminId();
            // 查询门店id
            Store store = storeMapper.selectByAdminId(adminId);
            if (store == null) {
                return ResponseFactory.suc();
            }
            List<ElectronicCoupons> list = new ArrayList<>();
            PageHelper.startPage(page.getPageNum(), page.getPageSize());
            List<ElectronicCoupons> coupons = electronicCouponsMapper.selectBySearch(createdAdminId, title);
            if (!CollectionUtils.isEmpty(coupons)) {
                for (ElectronicCoupons coupon : coupons) {
                    List<StoreVo> stores = new ArrayList<>();
                    if (!StringUtils.isEmpty(coupon.getStoreIds())) {
                        String[] split = coupon.getStoreIds().split(",");
                        for (String s : split) {
                            StoreVo store1 = storeMapper.selectById(Integer.parseInt(s));
                            stores.add(store1);
                        }
                    }
                    coupon.setStoreVos(stores);
                    // 分店优惠券
                    String[] strings = coupon.getStoreIds().split(",");
                    for (String string : strings) {
                        if (string.equals(store.getId().toString())) {
                            list.add(coupon);
                        }
                    }
                }
            }
            PageInfo pageInfo = new PageInfo<>(list);
            return ResponseFactory.page(list, pageInfo.getTotal(), pageInfo.getPages(),
                    pageInfo.getPageNum(), pageInfo.getPageSize());
        }
    }

    /**
     * 获取优惠券列表(小程序商家端)(优惠券只有平台商和门店可看)
     *
     * @return
     */
    @Override
    public Response getElCouponListXcx() {
        // 门店
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        if (webUserInfo.getRoleId() != 5){
            return ResponseFactory.err("门店账号登陆才可以使用");
        }
        Integer adminId = webUserInfo.getSysAdmin().getId();
        // 创建者adminId(商家adminId)
        Integer createdAdminId = webUserInfo.getSysAdmin().getCreateAdminId();
        // 查询门店id
        Store store = storeMapper.selectByAdminId(adminId);
        if (store == null) {
            return ResponseFactory.suc();
        }
        List<ElectronicCoupons> list = new ArrayList<>();
        List<ElectronicCoupons> coupons = electronicCouponsMapper.selectBySearch(createdAdminId,null);
        if (!CollectionUtils.isEmpty(coupons)) {
            for (ElectronicCoupons coupon : coupons) {
                List<StoreVo> stores = new ArrayList<>();
                if (!StringUtils.isEmpty(coupon.getStoreIds())) {
                    String[] split = coupon.getStoreIds().split(",");
                    for (String s : split) {
                        StoreVo store1 = storeMapper.selectById(Integer.parseInt(s));
                        stores.add(store1);
                    }
                }
                coupon.setStoreVos(stores);
                // 分店优惠券
                String[] strings = coupon.getStoreIds().split(",");
                for (String string : strings) {
                    if (string.equals(store.getId().toString())) {
                        list.add(coupon);
                    }
                }
            }
        }
        return ResponseFactory.sucData(list);
    }

    /**
     * 获取优惠券所有列表(优惠券部分只有平台商有)
     *
     * @return
     */
    @Override
    public Response getElCouponAllList() {
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        List<ElectronicCoupons> list = electronicCouponsMapper.selectByAdminId(webUserInfo.getSysAdmin().getId());
        return ResponseFactory.sucData(list);
    }

    /**
     * 通过二维码获取活动xiangqin
     *
     * @param id
     * @return
     */
    @Override
    public Response detailByQrcode(Long id) {
        ElCouponVo vo = elUserCouponMapper.selectDetailById(id);
        if (vo != null && org.apache.commons.lang3.StringUtils.isNotBlank(vo.getStoreIds())) {
            StringBuilder titles = new StringBuilder();
            String[] split = vo.getStoreIds().split(",");
            for (String s : split) {
                Store store = storeMapper.selectByPrimaryKey(Integer.parseInt(s));
                if (store != null) {
                    titles.append(store.getName()).append("/");
                }
            }
            vo.setQrcodeType(2);
            vo.setStoreName(titles.toString());
            return ResponseFactory.sucData(vo);
        }
        return ResponseFactory.err("優惠券不存在!");
    }

    /**
     * 用户优惠券核销
     *
     * @param num
     * @return
     */
    @Override
    public Response useCoupon(Long num) {
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        Store store = storeMapper.selectByAdminId(webUserInfo.getSysAdmin().getId());
        if (store == null) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "门店发优惠券出错");
        }
        // 判断优惠券是否存在
        ElCouponVo vo = elUserCouponMapper.selectDetailById(num);
        if (vo == null) {
            return ResponseFactory.err("优惠券不存在!");
        }
        // 优惠券是否已过期
        if (vo.getDate().getTime() < System.currentTimeMillis()) {
            return ResponseFactory.err("优惠券已过期!");
        }
        if (vo.getStartTime().getTime() > System.currentTimeMillis()) {
            return ResponseFactory.err("优惠券活动还未开始!");
        }
        // 判断门店是否匹配
        String[] split = vo.getStoreIds().split(",");
        boolean isMatch = false;
        for (String id : split) {
            if (id.equals(String.valueOf(store.getId()))) {
                isMatch = true;
                break;
            }
        }
        if (!isMatch) {
            return ResponseFactory.err("无权核销!");
        }
        // 用户的优惠券数量-1
        int quantity = vo.getQuantity();
        if (quantity == 0) {
            return ResponseFactory.err("優惠券已使用！");
        }
        quantity -= 1;
        if (quantity <= 0) {
            // 如果数量用完，直接删除
            elUserCouponMapper.deleteById(num);
        } else {
            // 数量减-1
            int count = elUserCouponMapper.updateQuantity(num);
            if (count == 0) {
                return ResponseFactory.err("优惠券已用完!");
            }
        }
        // 保存核销记录
        ElCouponUseLog log = new ElCouponUseLog();
        log.setCouponId(vo.getCouponId());
        log.setNum(vo.getNum());
        log.setUserId(vo.getUserId());
        log.setStoreId(store.getId());
        log.setDetail(JSON.toJSONString(vo));
        log.setAdminId(webUserInfo.getSysAdmin().getId());
        log.setQuantity(1);
        elUserCouponMapper.insertUseLog(log);
        return ResponseFactory.sucData(log);
    }

    /**
     * 添加平台商优惠券
     *
     * @param coupon
     * @return
     */
    @Override
    public Response addElCoupon(ElectronicCoupons coupon) {
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        ElectronicCoupons elCoupon = new ElectronicCoupons();
        elCoupon.setTitle(coupon.getTitle());
        elCoupon.setSummary(coupon.getSummary());
        elCoupon.setLogo(coupon.getLogo());
        elCoupon.setStoreIds(coupon.getStoreIds());
        elCoupon.setAdminId(webUserInfo.getSysAdmin().getId());
        elCoupon.setType((byte) 1);
        elCoupon.setStatus((byte) 1);
        elCoupon.setDate(coupon.getDate());
        elCoupon.setStartTime(coupon.getStartTime());
        int insert = electronicCouponsMapper.insert(elCoupon);
        if (insert < 1) {
            return ResponseFactory.err("添加失败");
        }
        return ResponseFactory.sucMsg("添加成功");
    }


    /**
     * 修改平台商优惠券
     *
     * @param coupon
     * @return
     */
    @Override
    public Response updateElCoupon(ElectronicCoupons coupon) {
        ElectronicCoupons elCoupon = electronicCouponsMapper.selectByKey(coupon.getId());
        if (elCoupon == null) {
            return ResponseFactory.err("该优惠券不存在或已被删除");
        }
        elCoupon.setTitle(coupon.getTitle());
        elCoupon.setSummary(coupon.getSummary());
        elCoupon.setLogo(coupon.getLogo());
        elCoupon.setStoreIds(coupon.getStoreIds());
        elCoupon.setDate(coupon.getDate());
        elCoupon.setStartTime(coupon.getStartTime());
        int i = electronicCouponsMapper.updateByPrimaryKeySelective(elCoupon);
        if (i < 1) {
            return ResponseFactory.err("修改失败");
        }
        return ResponseFactory.sucMsg("修改成功");
    }


    /**
     * 删除平台商优惠券
     *
     * @param couponId 优惠券id
     * @return
     */
    @Override
    public Response deleteElCoupon(Integer couponId) {
        ElectronicCoupons elCoupon = electronicCouponsMapper.selectByKey(couponId);
        if (elCoupon == null) {
            return ResponseFactory.err("该优惠券不存在或已被删除");
        }
        elCoupon.setStatus((byte) 10);
        int i = electronicCouponsMapper.updateByPrimaryKeySelective(elCoupon);
        if (i < 1) {
            return ResponseFactory.err("删除失败");
        }
        return ResponseFactory.sucMsg("删除成功");
    }

    /**
     * 门店给用户发优惠券
     *
     * @param phone    用户号码
     * @param couponId 优惠券id
     * @param quantity 优惠券数量
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
    public Response addCouponForUser(String phone, Integer couponId, Integer quantity) {
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        Store store = storeMapper.selectByAdminId(webUserInfo.getSysAdmin().getId());
        if (store == null) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "门店发优惠券出错");
        }
        // 查询用户是否注册过礼遇圈
        AppUser appUser = appUserMapper.selectByPhone1(phone);
        if (appUser == null) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "注册礼遇圈才可以送优惠券哦");
        }
        addCoupon(couponId, quantity, store.getId(), appUser.getId(), webUserInfo.getSysAdmin().getId());
        return ResponseFactory.sucMsg("赠送成功");
    }

    @Override
    public Long addCoupon(Integer couponId, Integer quantity, Integer storeId,
                          Integer userId, Integer adminId) {
        String traceId = (String) httpServletRequest.getAttribute("traceId");
        ElectronicCoupons coupons = electronicCouponsMapper.selectByKey(couponId);
        if (coupons == null) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "该优惠券不存在或已被删除");
        }
        log.info("traceId:{}, 优惠券:{}", traceId, JSON.toJSONString(coupons));
        // 给用户添加优惠券
        ElUserCoupon elUserCoupon = new ElUserCoupon();
        Long aLong = orderHelper.genOrderNo(4, 12);
        elUserCoupon.setId(aLong);
        elUserCoupon.setCouponId(couponId);
        elUserCoupon.setUserId(userId);
        elUserCoupon.setTotalQuantity(quantity);
        elUserCoupon.setQuantity(quantity);
        elUserCoupon.setStatus((byte) 1);
        elUserCoupon.setCode("");
        elUserCoupon.setStoreId(storeId);
        elUserCoupon.setAdminId(adminId);
        int insert = elUserCouponMapper.insert(elUserCoupon);
        if (insert < 1) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "赠送失败");
        }
        log.info("traceId:{}, 用户优惠券:{}", traceId, JSON.toJSONString(elUserCoupon));
        return aLong;
    }

    /**
     * 商家给用户赠送优惠券记录
     *
     * @param page
     * @param title     优惠券标题
     * @param phone     用户电话
     * @param store     赠送门店
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    @Override
    public Response getForUserList(PageQuery page, String title, String phone, String store,
                                   Long startTime, Long endTime) throws ParseException {
        if (startTime != null) {
            startTime = TimeUtils.time(startTime);
        }
        if (endTime != null) {
            endTime = TimeUtils.timeEnd(endTime);
        }
        // 角色
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        Integer adminId = null;
        // 商家
        if (webUserInfo.getRoleId() == 3) {
            Integer cAdminId = webUserInfo.getSysAdmin().getId();
            List<Integer> list = sysAdminMapper.selectIdByCreatedId(cAdminId);
            if (list.size() == 0) {
                return ResponseFactory.suc();
            }
            PageHelper.startPage(page.getPageNum(), page.getPageSize());
            List<ForUserVo> userVos = elUserCouponMapper.selectBySearch1(title, phone, store, startTime, endTime, list);
            PageInfo pageInfo = new PageInfo<>(userVos);
            return ResponseFactory.page(userVos, pageInfo.getTotal(), pageInfo.getPages(),
                    pageInfo.getPageNum(), pageInfo.getPageSize());
        } else if (webUserInfo.getRoleId() == 5) {
            adminId = webUserInfo.getSysAdmin().getId();
        }
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<ForUserVo> userVoList = elUserCouponMapper.selectBySearch(title, phone, store, startTime, endTime, adminId);
        PageInfo pageInfo = new PageInfo<>(userVoList);
        return ResponseFactory.page(userVoList, pageInfo.getTotal(), pageInfo.getPages(),
                pageInfo.getPageNum(), pageInfo.getPageSize());
    }

    /**
     * 优惠券转赠记录
     *
     * @param page
     * @param nickname  赠送者昵称
     * @param title     优惠券
     * @param status    状态
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public Response getSendCouponList(PageQuery page, String nickname, String title, Byte status, Long startTime, Long endTime) throws ParseException {
        if (startTime != null) {
            startTime = TimeUtils.time(startTime);
        }
        if (endTime != null) {
            endTime = TimeUtils.timeEnd(endTime);
        }
        // 角色
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        // 所有优惠券id
        List<Integer> list = new ArrayList<>();
        // 商家
        if (webUserInfo.getRoleId() == 3) {
            //查询商家创建的所有优惠券
            List<ElectronicCoupons> coupons = electronicCouponsMapper.selectByAdminId(webUserInfo.getSysAdmin().getId());
            if (!CollectionUtils.isEmpty(coupons)) {
                for (ElectronicCoupons coupon : coupons) {
                    list.add(coupon.getId());
                }
            }
            if (list.size() == 0) {
                List<SendFriendVo> sendFriendVos = new ArrayList<>();
                PageInfo pageInfo = new PageInfo<>(sendFriendVos);
                return ResponseFactory.page(sendFriendVos, pageInfo.getTotal(), pageInfo.getPages(),
                        pageInfo.getPageNum(), pageInfo.getPageSize());
            }
        } else if (webUserInfo.getRoleId() == 5) {
            //        分店adminId
            Integer adminId = webUserInfo.getSysAdmin().getId();
            // 创建者adminId(商家adminId)
            Integer createdAdminId = webUserInfo.getSysAdmin().getCreateAdminId();
            // 查询门店id
            Store store = storeMapper.selectByAdminId(adminId);
            if (store == null) {
                return ResponseFactory.suc();
            }
            List<ElectronicCoupons> coupons = electronicCouponsMapper.selectByAdminId(createdAdminId);
            if (!CollectionUtils.isEmpty(coupons)) {
                for (ElectronicCoupons coupon : coupons) {
                    // 分店优惠券
                    String[] strings = coupon.getStoreIds().split(",");
                    for (String string : strings) {
                        if (string.equals(store.getId().toString())) {
                            list.add(coupon.getId());
                        }
                    }
                }
            }
            if (list.size() == 0) {
                List<SendFriendVo> sendFriendVos = new ArrayList<>();
                PageInfo pageInfo = new PageInfo<>(sendFriendVos);
                return ResponseFactory.page(sendFriendVos, pageInfo.getTotal(), pageInfo.getPages(),
                        pageInfo.getPageNum(), pageInfo.getPageSize());
            }
        }
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<SendFriendVo> sendFriendVos = elCouponSendMapper.selectBySearch(nickname, title, status, startTime, endTime, list);
        PageInfo pageInfo = new PageInfo<>(sendFriendVos);
        return ResponseFactory.page(sendFriendVos, pageInfo.getTotal(), pageInfo.getPages(),
                pageInfo.getPageNum(), pageInfo.getPageSize());
    }
}
