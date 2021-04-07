package com.chouchong.service.v3.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chouchong.common.*;
import com.chouchong.dao.iwant.appUser.AppUserMapper;
import com.chouchong.dao.v3.ElCouponSendMapper;
import com.chouchong.dao.v3.ElUserCouponMapper;
import com.chouchong.dao.v3.ElectronicCouponsMapper;
import com.chouchong.dao.v3.StoreMapper;
import com.chouchong.dao.v4.CoMapper;
import com.chouchong.dao.v4.PrivilegeCouponsMapper;
import com.chouchong.dao.webUser.SysAdminMapper;
import com.chouchong.delayed.DelayedSmsSend;
import com.chouchong.entity.iwant.appUser.AppUser;
import com.chouchong.entity.v3.*;
import com.chouchong.entity.v4.PrivilegeCoupons;
import com.chouchong.exception.ServiceException;
import com.chouchong.service.v3.ElCouponService;
import com.chouchong.service.v3.vo.ForUserVo;
import com.chouchong.service.v3.vo.SendFriendVo;
import com.chouchong.service.v3.vo.SpecialVo;
import com.chouchong.service.v3.vo.StoreVo;
import com.chouchong.service.v4.vo.Cascader;
import com.chouchong.service.v4.vo.CascaderVo;
import com.chouchong.service.v4.vo.ElVo;
import com.chouchong.service.webUser.vo.WebUserInfo;
import com.chouchong.utils.QrCodeUtil;
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
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.*;

import static com.chouchong.entity.v3.ElUserCoupon.ELUSERCOUPON_STATUS;

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

    @Resource
    private PrivilegeCouponsMapper privilegeCouponsMapper;

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
        if (vo.getDay()!=null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(vo.getCreated());
            calendar.add(Calendar.DATE,vo.getDay());
            vo.setDate(calendar.getTime());
        }
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

        Date reEndTime ;
        PrivilegeCoupons privilegeCoupons = privilegeCouponsMapper.selectByPrimaryKey(vo.getCouponId());
        if (privilegeCoupons.getDay()!=null) {
            Calendar calendar = DelayedSmsSend.getDate(Calendar.DATE, privilegeCoupons.getDay(), vo.getCreated());
            reEndTime = calendar.getTime();
            vo.setDate(reEndTime);
            vo.setStartTime(vo.getCreated());
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
     * @param: privilegeCoupons
     * @Description: 添加优惠券
     * @Author: LxH
     * @Date: 2020/9/23 16:37
     */
    @Override
    @Transactional
    public Response addPrivilegeCoupons(PrivilegeCoupons privilegeCoupons) {
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        PrivilegeCoupons coupons = new PrivilegeCoupons();
        coupons.setTitle(privilegeCoupons.getTitle());
        coupons.setSummary(privilegeCoupons.getSummary());
        coupons.setLogo(privilegeCoupons.getLogo());
        coupons.setStoreIds(privilegeCoupons.getStoreIds());
        coupons.setAdminId(webUserInfo.getSysAdmin().getId());
        coupons.setType((byte) 1);
        coupons.setStatus((byte) 1);
        if (privilegeCoupons.getDay()!=null) {
            coupons.setDay(privilegeCoupons.getDay());
        }
        if (privilegeCoupons.getStartTime()!=null) {
            coupons.setDate(privilegeCoupons.getDate());
            coupons.setStartTime(privilegeCoupons.getStartTime());
        }
        coupons.setCreated(new Date());
        coupons.setUpdated(coupons.getCreated());
        int insert = privilegeCouponsMapper.insert(coupons);
        String url = "http://liyuquan.cn/static/elqrcode";
        String path = "/data/upload/image/elqrcode";
        //String path = "D:\\Desktop\\aaa";
        HashMap<String, Object> map = new HashMap<>();
        String name = UUID.randomUUID().toString()+".jpg";
        map.put("elCouponId", coupons.getId());
        String qrCode = QrCodeUtil.createQrCode(url, map, path, name);
        if (qrCode == null) {
            throw new ServiceException(ErrorCode.ERROR.getCode(),"二维码保存失败");
        }
        String qrCodeUrl = "/elqrcode/"+name;
        coupons.setQrCode(qrCodeUrl);
        privilegeCouponsMapper.updateByPrimaryKey(coupons);
        if (insert < 1) {
            return ResponseFactory.err("添加失败");
        }
        return ResponseFactory.sucMsg("添加成功");
    }

    /**
     * @param: privilegeCoupons
     * @Description: 修改优惠券
     * @Author: LxH
     * @Date: 2020/9/23 16:37
     */
    @Override
    public Response updatePrivilegeCoupons(PrivilegeCoupons privilegeCoupons) {
        PrivilegeCoupons privilegeCoupons1 = privilegeCouponsMapper.selectByPrimaryKey(privilegeCoupons.getId());
        if (privilegeCoupons1==null) {
            ResponseFactory.err("优惠券已删除");
        }
        privilegeCoupons1.setTitle(privilegeCoupons.getTitle());
        privilegeCoupons1.setSummary(privilegeCoupons.getSummary());
        privilegeCoupons1.setLogo(privilegeCoupons.getLogo());
        privilegeCoupons1.setStoreIds(privilegeCoupons.getStoreIds());
        /*if (privilegeCoupons.getDay()!=null) {

        }else {}*/
        privilegeCoupons1.setDay(privilegeCoupons.getDay());
        if (privilegeCoupons.getDate()!=null&&privilegeCoupons.getStartTime()!=null) {
            privilegeCoupons1.setDate(privilegeCoupons.getDate());
            privilegeCoupons1.setStartTime(privilegeCoupons.getStartTime());
        }
        /*if (privilegeCoupons.getStartTime()!=null) {

        } else {
            privilegeCoupons1.setDate(null);
            privilegeCoupons1.setStartTime(null);
        }*/
        privilegeCoupons1.setUpdated(new Date());
        //int update = privilegeCouponsMapper.updateByPrimaryKeySelective();
        int update = privilegeCouponsMapper.updateByPrimaryKey(privilegeCoupons1);
        if (update < 1) {
            return ResponseFactory.err("修改失败");
        }
        return ResponseFactory.sucMsg("修改成功");
    }

    /**
     * @param： privilegeCouponId
     * @description: 删除优惠券
     * @author: LxH
     * @time: 2020/10/15 0015 下午 2:57
     */
    @Override
    public Response deletePrivilegeCoupon(Integer privilegeCouponId) {
        PrivilegeCoupons privilegeCoupons = privilegeCouponsMapper.selectByPrimaryKey(privilegeCouponId);
        if (privilegeCoupons == null) {
            return ResponseFactory.err("优惠券不存在");
        }
        privilegeCoupons.setStatus((byte) 10);
        int i = privilegeCouponsMapper.updateByPrimaryKeySelective(privilegeCoupons);
        if (i < 1) {
            return ResponseFactory.err("删除失败");
        }
        return ResponseFactory.sucMsg("删除成功");
    }

    /**
     * @param: couponId
     * @Description: 获取非门店赠送的优惠券记录
     * @Author: LxH
     * @Date: 2020/10/27 11:25
     */
    @Override
    public Response findCouponLog(Integer couponId) {
        Example example = new Example(ElUserCoupon.class);
        example.createCriteria().andEqualTo("couponId", couponId).
                andEqualTo(ELUSERCOUPON_STATUS, 1).
                andEqualTo("storeId", null).andEqualTo("adminId", null);
        List<ElUserCoupon> elUserCoupons = elUserCouponMapper.selectByExample(example);
        for (ElUserCoupon elUserCoupon : elUserCoupons) {
            AppUser appUser = appUserMapper.selectByPrimaryKey(elUserCoupon.getUserId());
            if (appUser !=null) {
                elUserCoupon.setUserName(appUser.getNickname()).setPhonr(appUser.getPhone());
            }
            ElectronicCoupons electronicCoupons = privilegeCouponsMapper.selectByPrimaryKey(elUserCoupon.getCouponId());
            if (electronicCoupons != null) {
                elUserCoupon.setCouponName(electronicCoupons.getTitle());
            }
        }
        return ResponseFactory.sucData(elUserCoupons);
    }

    /**
     * @Description: 可以发送的优惠券
     * @Author: LxH
     * @Date: 2020/12/1 10:18
     */
    @Override
    public Response findCoupons() {
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        // 门店
        // 创建者adminId(商家adminId)
        Integer createdAdminId = webUserInfo.getSysAdmin().getCreateAdminId();
        // 查询门店id
        Store store = storeMapper.selectByAdminId(webUserInfo.getSysAdmin().getId());
        if (store == null) {
            return ResponseFactory.suc();
        }
        List<ElectronicCoupons> list = new ArrayList<>();
        List<ElectronicCoupons> coupons = electronicCouponsMapper.selectBySearch(createdAdminId,"");
        ArrayList<CascaderVo> list1 = new ArrayList<>(20);
        ArrayList<Cascader> arrayList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            CascaderVo vo = new CascaderVo();
            vo.setValue(i+1);
            vo.setLabel(i+1+"");
            list1.add(vo);
        }
        if (!CollectionUtils.isEmpty(coupons)) {
            for (ElectronicCoupons coupon : coupons) {
                // 分店优惠券
                String[] strings = coupon.getStoreIds().split(",");
                for (String string : strings) {
                    if (string.equals(store.getId().toString())) {
                        list.add(coupon);
                    }
                }

            }
        }
        for (ElectronicCoupons electronicCoupons : list) {
            Cascader cascader = new Cascader();
            cascader.setValue(electronicCoupons.getId());
            cascader.setLabel(electronicCoupons.getTitle());
            cascader.setChildren(list1);
            arrayList.add(cascader);
        }

        return ResponseFactory.sucData(arrayList);
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

    /**
     * @param： phone
     * @param： couponJson   例：couponJson="[{couponId:336,quantity:5},{couponId:339,quantity:8},{couponId:337,quantity:10}]"
     * @Description: 批量赠送优惠券
     * @Author: LxH
     * @Date: 2020/9/23 13:13
     */
    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
    public Response giveUserCoupons(String phone, String couponJson) {
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
        List<SpecialVo> specialVos = JSONObject.parseArray(couponJson, SpecialVo.class);
        giveCoupons(store,appUser,specialVos);
        return ResponseFactory.sucMsg("赠送成功");
    }


    private void giveCoupons(Store store, AppUser appUser, List<SpecialVo> specialVos) {
        for (SpecialVo specialVo : specialVos) {
            ElUserCoupon elUserCoupon = new ElUserCoupon();
            Long aLong = orderHelper.genOrderNo(4, 12);
            elUserCoupon.setId(aLong);
            elUserCoupon.setAdminId(store.getAdminId());
            elUserCoupon.setCouponId(specialVo.getCouponId());
            elUserCoupon.setQuantity(specialVo.getQuantity());
            elUserCoupon.setTotalQuantity(specialVo.getQuantity());
            elUserCoupon.setUserId(appUser.getId());
            elUserCoupon.setStatus((byte) 1);
            elUserCoupon.setCode("");
            elUserCoupon.setStoreId(store.getId());
            int insert = elUserCouponMapper.insert(elUserCoupon);
            if (insert < 1) {
                throw new ServiceException(ErrorCode.ERROR.getCode(), "赠送失败");
            }
        }
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
            return getResponse(userVos);
        } else if (webUserInfo.getRoleId() == 5) {
            adminId = webUserInfo.getSysAdmin().getId();
        }
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<ForUserVo> userVoList = elUserCouponMapper.selectBySearch(title, phone, store, startTime, endTime, adminId);
        return getResponse(userVoList);
    }

    private Response getResponse(List<ForUserVo> userVoList) {
        for (ForUserVo userVo : userVoList) {
            Date reEndTime ;
            PrivilegeCoupons privilegeCoupons = privilegeCouponsMapper.selectByPrimaryKey(userVo.getCouponId());
            if (privilegeCoupons.getDay()!=null) {
                Calendar calendar = DelayedSmsSend.getDate(Calendar.DATE, privilegeCoupons.getDay(), userVo.getCreated());
                reEndTime = calendar.getTime();
                userVo.setDate(reEndTime);

            }
        }
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
