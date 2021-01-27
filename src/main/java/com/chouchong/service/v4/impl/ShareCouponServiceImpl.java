package com.chouchong.service.v4.impl;

import com.alibaba.fastjson.JSONObject;
import com.chouchong.common.ErrorCode;
import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.iwant.appUser.AppUserMapper;
import com.chouchong.dao.v3.StoreMapper;
import com.chouchong.dao.v4.ShareCouponMapper;
import com.chouchong.dao.v4.ShareCouponUserLogMapper;
import com.chouchong.dao.v4.ShareCouponUserMapper;
import com.chouchong.entity.iwant.appUser.AppUser;
import com.chouchong.entity.v3.Store;
import com.chouchong.entity.v4.ShareCoupon;
import com.chouchong.entity.v4.ShareCouponUser;
import com.chouchong.entity.v4.ShareCouponUserLog;
import com.chouchong.exception.ServiceException;
import com.chouchong.service.v3.vo.StoreVo;
import com.chouchong.service.v4.ShareCouponService;
import com.chouchong.service.webUser.vo.WebUserInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.chouchong.entity.v3.ElectronicCoupons.STATUS;
import static com.chouchong.entity.v4.ElCouponList.TYPE;
import static com.chouchong.entity.v4.ElUserCouponList.*;
import static com.chouchong.entity.v4.ShareCouponUser.PHONE;
import static com.chouchong.entity.v4.ShareCouponUser.TITLE;

/**
 * @description:
 * @author: LxH
 * @time: 2020/10/15 0015 下午 2:09
 */
@Service
public class ShareCouponServiceImpl implements ShareCouponService {

    @Resource
    private HttpServletRequest httpServletRequest;

    @Resource
    private ShareCouponMapper shareCouponMapper;

    @Resource
    private ShareCouponUserMapper shareCouponUserMapper;

    @Resource
    private StoreMapper storeMapper;

    @Resource
    private ShareCouponUserLogMapper shareCouponUserLogMapper;

    @Resource
    private AppUserMapper appUserMapper;

    /**
     * @param: shareCoupon
     * @description: 添加分享劵
     * @author: LxH
     * @time: 2020/10/15 0015 下午 2:16
     */
    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
    public Response addShareCoupon(ShareCoupon shareCoupon) {
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        Example example = new Example(ShareCoupon.class);
        example.createCriteria().andEqualTo(ADMIN_ID,webUserInfo.getSysAdmin().getId()).
                andEqualTo(TYPE, 1).andEqualTo(STATUS, 1);
        ShareCoupon coupon = shareCouponMapper.selectOneByExample(example);
        if (coupon != null) {
            return ResponseFactory.err("分享券上限为一张");
        }
        shareCoupon.setAdminId(webUserInfo.getSysAdmin().getId()).setStatus((byte) 1).setType((byte) 1).setCreated(new Date()).
                setUpdated(new Date()).setTotal(shareCoupon.getTotality());
        int i = shareCouponMapper.insertSelective(shareCoupon);
        if (i < 1) {
            return ResponseFactory.err("添加失败");
        }
        return ResponseFactory.sucMsg("添加成功");
    }

    /**
     * @param： shareCoupon
     * @description: 修改分享劵
     * @author: LxH
     * @time: 2020/10/15 0015 下午 2:42
     */
    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
    public Response updateShareCoupon(ShareCoupon shareCoupon) {
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        ShareCoupon share = shareCouponMapper.selectByPrimaryKey(shareCoupon.getId());
        if (share == null) {
            ResponseFactory.err("分享劵已删除");
        }
        share.setType(shareCoupon.getType()).setStatus(shareCoupon.getStatus()).setAdminId(webUserInfo.getSysAdmin().getId()).
                setTitle(shareCoupon.getTitle()).setCeiling(shareCoupon.getCeiling()).setDate(shareCoupon.getDate()).setStoreIds(shareCoupon.getStoreIds()).
                setSummary(shareCoupon.getSummary()).setStartTime(shareCoupon.getStartTime()).setLogo(shareCoupon.getLogo()).setDay(shareCoupon.getDay()).
                setTotality(shareCoupon.getTotality()).setUpdated(new Date()).setTotal(shareCoupon.getTotality()).setRule(shareCoupon.getRule());
        int i = shareCouponMapper.updateByPrimaryKeySelective(share);
        if (i < 1) {
            return ResponseFactory.err("修改失败");
        }
        return ResponseFactory.sucMsg("修改成功");
    }

    /**
     * @param: shareCouponId
     * @description: 删除分享劵
     * @author: LxH
     * @time: 2020/10/15 0015 下午 3:00
     */
    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
    public Response deleteShareCoupon(Integer shareCouponId) {
        ShareCoupon shareCoupon = shareCouponMapper.selectByPrimaryKey(shareCouponId);
        if (shareCoupon == null) {
            return ResponseFactory.err("分享劵已删除");
        }
        shareCoupon.setStatus((byte) 10);
        int i = shareCouponMapper.updateByPrimaryKeySelective(shareCoupon);
        if (i < 1) {
            return ResponseFactory.err("删除失败");
        }
        return ResponseFactory.sucMsg("删除成功");
    }

    /**
     * @param: title
     * @param: page
     * @description: 后台分享劵分页查询
     * @author: LxH
     * @time: 2020/10/15 0015 下午 3:21
     */
    @Override
    public Response findShareCouponList(String title, PageQuery page) {
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        Integer adminId = webUserInfo.getSysAdmin().getId();
        if (webUserInfo.getRoleId() == 3) {
            Example example = new Example(ShareCoupon.class);
            example.orderBy(CREATED).desc();
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo(ADMIN_ID,adminId);
            criteria.andEqualTo(STATUS,1);
            List<ShareCoupon> shareCoupons = shareCouponMapper.selectByExample(example);
            for (ShareCoupon shareCoupon : shareCoupons) {
                List<StoreVo> stores = new ArrayList<>();
                if (shareCoupon.getStoreIds() != null) {
                    String[] split = shareCoupon.getStoreIds().split(",");
                    for (String s : split) {
                        StoreVo store = storeMapper.selectById(Integer.parseInt(s));
                        stores.add(store);
                    }
                }
                shareCoupon.setStoreVos(stores);

            }
            PageInfo pageInfo = new PageInfo<>(shareCoupons);
            return ResponseFactory.page(pageInfo, pageInfo.getTotal(), pageInfo.getPages(),
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
            List<ShareCoupon> list = new ArrayList<>();
            PageHelper.startPage(page.getPageNum(), page.getPageSize());
            Example example = new Example(ShareCoupon.class);
            example.orderBy(CREATED).desc();
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo(ADMIN_ID,createdAdminId);
            criteria.andEqualTo(STATUS,1);
            List<ShareCoupon> shareCoupons = shareCouponMapper.selectByExample(example);
            for (ShareCoupon shareCoupon : shareCoupons) {
                List<StoreVo> stores = new ArrayList<>();
                if (shareCoupon.getStoreIds() != null) {
                    String[] split = shareCoupon.getStoreIds().split(",");
                    for (String s : split) {
                        StoreVo store1 = storeMapper.selectById(Integer.parseInt(s));
                        stores.add(store1);
                    }
                }
                shareCoupon.setStoreVos(stores);
            }
            PageInfo pageInfo = new PageInfo<>(shareCoupons);
            return ResponseFactory.page(pageInfo, pageInfo.getTotal(), pageInfo.getPages(),
                    pageInfo.getPageNum(), pageInfo.getPageSize());
        }
    }

    /**
     * @param: title
     * @param: phone
     * @param: page
     * @description: 分享劵领取记录
     * @author: LxH
     * @time: 2020/10/16 0016 下午 1:23
     */
    @Override
    public Response shareCouponRecord(String title, String phone, PageQuery page) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        Example example = new Example(ShareCouponUser.class);
        example.orderBy(CREATED).desc();
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo(STATUS,1);
        if (StringUtils.isNotBlank(title)) {
            criteria.andEqualTo(TITLE,"%"+title+"%");
        }
        if (StringUtils.isNotBlank(phone)) {
            criteria.andEqualTo(PHONE,phone);
        }
        List<ShareCouponUser> shareCouponUsers = shareCouponUserMapper.selectByExample(example);
        for (ShareCouponUser shareCouponUser : shareCouponUsers) {
            StringBuilder titles = new StringBuilder();
            ShareCoupon shareCoupon = shareCouponMapper.selectByPrimaryKey(shareCouponUser.getShareCouponId());
            if (StringUtils.isNotBlank(shareCoupon.getStoreIds())) {
                String[] split = shareCoupon.getStoreIds().split(",");
                for (String s : split) {
                    Store store = storeMapper.selectByPrimaryKey(Integer.parseInt(s));
                    if (StringUtils.isNotBlank(store.toString())) {
                        titles.append(store.getName()).append("/");
                    }
                }
            }
            shareCouponUser.setStoreName(titles.delete(titles.length()-1,titles.length()).toString());
            AppUser appUser = appUserMapper.selectByPrimaryKey(shareCouponUser.getUserId());
            shareCouponUser.setUserName(appUser.getNickname());
        }
        PageInfo pageInfo = new PageInfo<>(shareCouponUsers);
        return ResponseFactory.page(pageInfo, pageInfo.getTotal(), pageInfo.getPages(),
                pageInfo.getPageNum(), pageInfo.getPageSize());
    }

    /**
     * @param: id
     * @description: 扫二维码获取分享劵详情
     * @author: LxH
     * @time: 2020/10/16 0016 下午 2:59
     */
    @Override
    public Response detailByQrcode(long id) {
        ShareCouponUser shareCouponUser = shareCouponUserMapper.selectByPrimaryKey(id);
        ShareCoupon shareCoupon = shareCouponMapper.selectByPrimaryKey(shareCouponUser.getShareCouponId());
        if (shareCouponUser != null && StringUtils.isNotBlank(shareCoupon.getStoreIds())) {
            StringBuilder titles = new StringBuilder();
            String[] split = shareCoupon.getStoreIds().split(",");
            for (String s : split) {
                Store store = storeMapper.selectByPrimaryKey(Integer.parseInt(s));
                if (store!=null) {
                    titles.append(store.getName()).append("/");
                }
            }
            shareCouponUser.setStoreName(titles.delete(titles.length()-1,titles.length()).toString()).
                    setLogo(shareCoupon.getLogo()).setQrcodeType(3);
            return ResponseFactory.sucData(shareCouponUser);
        }
        return ResponseFactory.sucMsg("分享劵不存在");
    }

    /**
     * @param: userShareId
     * @description: 核销分享劵
     * @author: LxH
     * @time: 2020/10/16 0016 下午 3:41
     */
    @Override
    @Transactional
    public Response userShareCoupon(Long userShareId) {
        ShareCouponUser shareCouponUser = shareCouponUserMapper.selectByPrimaryKey(userShareId);
        ShareCoupon shareCoupon = shareCouponMapper.selectByPrimaryKey(shareCouponUser.getShareCouponId());
        if (StringUtils.isBlank(shareCouponUser.toString())) {
            return ResponseFactory.err("分享劵不存在");
        }
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        Store store = storeMapper.selectByAdminId(webUserInfo.getSysAdmin().getId());
        if (store == null) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "门店发优惠券出错");
        }
        // 优惠券是否已过期
        if (shareCouponUser.getEndTime().getTime() < System.currentTimeMillis()) {
            return ResponseFactory.err("优惠券已过期!");
        }
        if (shareCouponUser.getCreated().getTime() > System.currentTimeMillis()) {
            return ResponseFactory.err("优惠券活动还未开始!");
        }
        // 判断门店是否匹配
        String[] split = shareCoupon.getStoreIds().split(",");
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
        if (shareCouponUser.getQuantity() == 0) {
            return ResponseFactory.err("分享劵已经使用");
        }
        Integer quantity = shareCouponUser.getQuantity();
        quantity -= 1;
        if (quantity <=0) {
            int i = shareCouponUserMapper.deleteByPrimaryKey(userShareId);
            if (i < 1) {
                return ResponseFactory.err("删除失败");
            }
        } else {
            shareCouponUser.setQuantity(quantity);
            shareCouponUserMapper.updateByPrimaryKeySelective(shareCouponUser);
        }
        ShareCouponUserLog shareCouponUserLog = new ShareCouponUserLog();
        shareCouponUserLog.setAdminId(webUserInfo.getSysAdmin().getId()).setStoreId(store.getId()).
                setShareCouponUserId(shareCouponUser.getId()).setShareCouponId(shareCoupon.getId()).
                setUserId(shareCouponUser.getUserId()).setDetail(JSONObject.toJSONString(shareCouponUser)).
                setCreated(new Date()).setCreatAdminId(webUserInfo.getSysAdmin().getCreateAdminId());
        int i = shareCouponUserLogMapper.insertSelective(shareCouponUserLog);
        if (i < 1) {
            return ResponseFactory.err("核销记录添加失败");
        }
        return ResponseFactory.sucMsg("分享劵核销成功");
    }

    /**
     * @param: title
     * @param: phone
     * @param: storeId
     * @param: page
     * @description: 分享劵用户核销记录
     * @author: LxH
     * @time: 2020/10/16 0016 下午 5:53
     */
    @Override
    public Response userShareCouponLog(String title, String phone, Integer storeId, PageQuery page) {
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        Example example = new Example(ShareCouponUserLog.class);
        example.orderBy(CREATED).desc();
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(title)) {
            criteria.andLike(TITLE,"%"+title+"%");
        }
        if (StringUtils.isNotBlank(phone)) {
            criteria.andEqualTo(PHONE,phone);
        }
        if (storeId!=null) {
            criteria.andEqualTo(STORE_ID,storeId);
        }
        if (webUserInfo.getRoleId() == 3) {
            criteria.andEqualTo(CREAT_ADMIN,webUserInfo.getSysAdmin().getId());
        }
        if (webUserInfo.getRoleId() == 5) {
            criteria.andEqualTo(ADMIN_ID,webUserInfo.getSysAdmin().getId());
        }
        List<ShareCouponUserLog> shareCouponUserLogs = shareCouponUserLogMapper.selectByExample(example);
        if (shareCouponUserLogs.size()==0) {
            return ResponseFactory.sucData(shareCouponUserLogs);
        }
        for (ShareCouponUserLog shareCouponUserLog : shareCouponUserLogs) {
            //查询用户信息
            AppUser appUser = appUserMapper.selectByPrimaryKey(shareCouponUserLog.getUserId());
            if (appUser!=null) {
                shareCouponUserLog.setUserName(appUser.getNickname()).setUserPhone(appUser.getPhone());
            }
            //查询分享劵信息
            ShareCoupon shareCoupon = shareCouponMapper.selectByPrimaryKey(shareCouponUserLog.getShareCouponId());
            if (shareCoupon!=null) {
                shareCouponUserLog.setTitle(shareCoupon.getTitle());
            }
            //查询门店信息
            Store store = storeMapper.selectByPrimaryKey(shareCouponUserLog.getStoreId());
            if (store!=null) {
                shareCouponUserLog.setStoreName(store.getName());
            }
        }
        PageInfo pageInfo = new PageInfo<>(shareCouponUserLogs);
        return ResponseFactory.page(pageInfo, pageInfo.getTotal(), pageInfo.getPages(),
                pageInfo.getPageNum(), pageInfo.getPageSize());
    }
}