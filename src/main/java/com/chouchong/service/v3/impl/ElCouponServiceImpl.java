package com.chouchong.service.v3.impl;

import com.chouchong.common.*;
import com.chouchong.dao.iwant.appUser.AppUserMapper;
import com.chouchong.dao.v3.ElUserCouponMapper;
import com.chouchong.dao.v3.ElectronicCouponsMapper;
import com.chouchong.dao.v3.StoreMapper;
import com.chouchong.entity.iwant.appUser.AppUser;
import com.chouchong.entity.v3.ElUserCoupon;
import com.chouchong.entity.v3.ElectronicCoupons;
import com.chouchong.entity.v3.Store;
import com.chouchong.exception.ServiceException;
import com.chouchong.service.v3.ElCouponService;
import com.chouchong.service.v3.vo.StoreVo;
import com.chouchong.service.webUser.vo.WebUserInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author linqin
 * @date 2020/2/11 16:15
 */
@Service
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
        ElectronicCoupons coupons = electronicCouponsMapper.selectByKey(couponId);
        if (coupons == null) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "该优惠券不存在或已被删除");
        }
        // 给用户添加优惠券
        ElUserCoupon elUserCoupon = new ElUserCoupon();
        elUserCoupon.setId(orderHelper.genOrderNo(4, 12));
        elUserCoupon.setCouponId(couponId);
        elUserCoupon.setUserId(appUser.getId());
        elUserCoupon.setTotalQuantity(quantity);
        elUserCoupon.setQuantity(quantity);
        elUserCoupon.setStatus((byte) 1);
        elUserCoupon.setCode("");
        elUserCoupon.setStoreId(store.getId());
        elUserCoupon.setAdminId(webUserInfo.getSysAdmin().getId());
        int insert = elUserCouponMapper.insert(elUserCoupon);
        if (insert < 1) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "赠送失败");
        }
        return ResponseFactory.sucMsg("赠送成功");
    }


}
