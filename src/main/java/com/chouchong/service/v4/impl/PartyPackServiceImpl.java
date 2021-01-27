package com.chouchong.service.v4.impl;

import com.alibaba.fastjson.JSONObject;
import com.chouchong.common.*;
import com.chouchong.dao.iwant.appUser.AppUserMapper;
import com.chouchong.dao.v3.ElUserCouponMapper;
import com.chouchong.dao.v3.StoreMapper;
import com.chouchong.dao.v4.*;
import com.chouchong.entity.iwant.appUser.AppUser;
import com.chouchong.entity.v3.ElUserCoupon;
import com.chouchong.entity.v3.ElectronicCoupons;
import com.chouchong.entity.v3.Store;
import com.chouchong.entity.v4.ElCouponList;
import com.chouchong.entity.v4.ElCouponListCoupon;
import com.chouchong.entity.v4.ElUserCouponList;
import com.chouchong.entity.v4.User;
import com.chouchong.exception.ServiceException;
import com.chouchong.service.v3.vo.SpecialVo;
import com.chouchong.service.v4.PartyPackService;
import com.chouchong.service.v4.vo.Cascader;
import com.chouchong.service.v4.vo.CascaderVo;
import com.chouchong.service.v4.vo.PartyPackJsonVo;
import com.chouchong.service.webUser.vo.WebUserInfo;
import com.chouchong.utils.QrCodeUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.chouchong.entity.v3.ElectronicCoupons.COLUMN_ADMIN_ID;
import static com.chouchong.entity.v3.ElectronicCoupons.STATUS;
import static com.chouchong.entity.v4.ElCouponList.TITLE;
import static com.chouchong.entity.v4.ElCouponList.TYPE;
import static com.chouchong.entity.v4.ElCouponListCoupon.COLUMN_EL_COUPON_ID;
import static com.chouchong.entity.v4.ElUserCouponList.*;

/**
 * @Description:
 * @Author Lxh
 * @Date 2020/9/30 16:35
 */
@Service
public class PartyPackServiceImpl implements PartyPackService {

    @Resource
    private HttpServletRequest httpServletRequest;

    @Resource
    private ElCouponListMapper elCouponListMapper;

    @Resource
    private ElCouponListCouponMapper elCouponListCouponMapper;

    @Resource
    private ElUserCouponListMapper elUserCouponListMapper;

    @Resource
    private ElUserCouponMapper elUserCouponMapper;

    @Resource
    private CoMapper coMapper;

    @Resource
    private StoreMapper storeMapper;

    @Resource
    private AppUserMapper appUserMapper;

    @Resource
    private OrderHelper orderHelper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private V4StroeMapper v4StroeMapper;


    /**
     * @param: elCouponList
     * @param: couponJson
     * @Description: 后台添加大礼包
     * @Author: LxH
     * @Date: 2020/9/30 16:44
     */
    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
    public Response addPartyPack(ElCouponList elCouponList, String couponJson) {
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        List<SpecialVo> specialVos = JSONObject.parseArray(couponJson, SpecialVo.class);
        Example example = new Example(ElCouponList.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo(ADMIN_ID, webUserInfo.getSysAdmin().getId());
        if (elCouponList.getType() == 0) {
            criteria.andEqualTo(TYPE, 0);
        }
        if (elCouponList.getType() == 1) {
            criteria.andEqualTo(TYPE, 1);
        }
        ElCouponList list = elCouponListMapper.selectOneByExample(example);
        if (list != null) {
            return ResponseFactory.err("该类型礼包上限为一个");
        }
        UUID.randomUUID().toString();
        elCouponList.setAdminId(webUserInfo.getSysAdmin().getId()).setStatus((byte) 1).
                setCreated(new Date()).setUpdated(new Date());
        int insert = elCouponListMapper.insertSelective(elCouponList);
        String url = "http://liyuquan.cn/static/qrcode";
        String path = "/data/upload/image/qrcode";
        HashMap<String, Object> map = new HashMap<>();
        String name = UUID.randomUUID().toString()+".jpg";
        map.put("elCouponListId", elCouponList.getId());
        String qrCode = QrCodeUtil.createQrCode(url, map, path, name);
        if (qrCode == null) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "礼包添加失败！");
        }
        String qrCodeUrl = "/qrcode/" + name;
        elCouponList.setQrCode(qrCodeUrl);
        elCouponListMapper.updateByPrimaryKeySelective(elCouponList);
        if (insert < 1) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "礼包添加失败！");
        }
        //添加礼包和优惠券关联
        if (elCouponList.getType() == 0) {
            if (specialVos.size() > 5) {
                throw new ServiceException(ErrorCode.ERROR.getCode(), "新用户礼包最多可添加5张优惠券");
            }
        }
        for (SpecialVo specialVo : specialVos) {
            ElCouponListCoupon elCouponListCoupon = new ElCouponListCoupon();
            elCouponListCoupon.setElCouponListId(elCouponList.getId()).setCouponId(specialVo.getCouponId()).
                    setQuantity(specialVo.getQuantity());
            int insertSelective = elCouponListCouponMapper.insertSelective(elCouponListCoupon);
            if (insertSelective < 1) {
                throw new ServiceException(ErrorCode.ERROR.getCode(), "礼包用户关联失败！");
            }
        }
        return ResponseFactory.sucData(elCouponList.getId());
    }

    /**
     * @param： elCouponList
     * @param： couponJson
     * @Description: 后台修改大礼包
     * @Author: LxH
     * @Date: 2020/9/30 17:17
     */
    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
    public Response updatePartyPack(ElCouponList elCouponList, String couponJson) {
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        List<SpecialVo> specialVos = JSONObject.parseArray(couponJson, SpecialVo.class);
        ElCouponList select = elCouponListMapper.selectByPrimaryKey(elCouponList.getId());
        if (select == null) {
            return ResponseFactory.err("该礼包已删除或不存在！");
        }
        select.setAdminId(webUserInfo.getSysAdmin().getId()).setUpdated(new Date()).
                setTitle(elCouponList.getTitle()).setSummary(elCouponList.getSummary()).
                setType(elCouponList.getType()).setPrice(elCouponList.getPrice()).setThePaid(elCouponList.getThePaid()).
        setPartyPackJson(elCouponList.getPartyPackJson());
        int update = elCouponListMapper.updateByPrimaryKeySelective(select);
        if (update < 1) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "礼包修改失败！");
        }
        //修改礼包优惠券关联表
        Example example = new Example(ElCouponListCoupon.class);
        example.createCriteria().andEqualTo(COLUMN_EL_COUPON_ID, elCouponList.getId());
        int i = elCouponListCouponMapper.deleteByExample(example);
        if (i < 1) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "礼包用户关联修改失败！");
        }
        for (SpecialVo specialVo : specialVos) {
            ElCouponListCoupon elCouponListCoupon = new ElCouponListCoupon();
            elCouponListCoupon.setElCouponListId(elCouponList.getId()).setCouponId(specialVo.getCouponId()).
                    setQuantity(specialVo.getQuantity());
            int insertSelective = elCouponListCouponMapper.insertSelective(elCouponListCoupon);
            if (insertSelective < 1) {
                throw new ServiceException(ErrorCode.ERROR.getCode(), "礼包用户关联修改失败！");
            }
        }
        return ResponseFactory.sucData(elCouponList.getId());
    }

    /**
     * @param: elCouponListId
     * @Description: 后台删除礼包
     * @Author: LxH
     * @Date: 2020/9/30 17:38
     */
    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
    public Response deletePartyPack(Integer elCouponListId) {
        int i = elCouponListMapper.deleteByPrimaryKey(elCouponListId);
        if (i < 1) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "礼包删除失败！");
        }
        Example example = new Example(ElCouponListCoupon.class);
        example.createCriteria().andEqualTo(COLUMN_EL_COUPON_ID, elCouponListId);
        int i1 = elCouponListCouponMapper.deleteByExample(example);
        if (i1 < 1) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "礼包用户关联删除失败！");
        }
        return ResponseFactory.sucMsg("成功删除");
    }

    /**
     * @Description: 可以添加到礼包的优惠券
     * @Author: LxH
     * @Date: 2020/10/6 14:43
     */
    @Override
    public Response findCoupons() {
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        // 平台商登录
        Integer adminId = null;
        // 超级管理员和商家
        if (webUserInfo.getRoleId() != 2) {
            if (webUserInfo.getRoleId() == 3) {
                adminId = webUserInfo.getSysAdmin().getId();
            }
            if (webUserInfo.getRoleId() == 5) {
                adminId = webUserInfo.getSysAdmin().getCreateAdminId();
            }
        }
        Example example = new Example(ElectronicCoupons.class);
        example.createCriteria().andEqualTo(COLUMN_ADMIN_ID, adminId).andEqualTo(STATUS, 1);
        List<ElectronicCoupons> electronicCoupons = coMapper.selectByExample(example);
        if (electronicCoupons.size() <= 0) {
            return ResponseFactory.sucMsg("该商户还未添加优惠券");
        }
        ArrayList<CascaderVo> list = new ArrayList<>(20);

        for (int i = 0; i < 20; i++) {
            CascaderVo vo = new CascaderVo();
            vo.setValue(i+1);
            vo.setLabel(i+1+"");
            list.add(vo);
        }
        ArrayList<Cascader> arrayList = new ArrayList<>();
        for (ElectronicCoupons electronicCoupon : electronicCoupons) {
            Cascader cascader = new Cascader();
            cascader.setValue(electronicCoupon.getId());
            cascader.setLabel(electronicCoupon.getTitle());
            cascader.setChildren(list);
            arrayList.add(cascader);
        }
        return ResponseFactory.sucData(arrayList);
    }

    /**
     * @param: title
     * @param: page
     * @Description: 礼包分页查询
     * @Author: LxH
     * @Date: 2020/10/6 15:06
     */
    @Override
    public Response findPartyPackByPage(String title, PageQuery page) {
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        Example example = new Example(ElCouponList.class);
        example.orderBy(CREATED);
        Example.Criteria criteria = example.createCriteria();
        if (webUserInfo.getRoleId() == 3) {
            criteria.andEqualTo(ADMIN_ID,webUserInfo.getSysAdmin().getId());
        }
        if (webUserInfo.getRoleId() == 5) {
            criteria.andEqualTo(ADMIN_ID,webUserInfo.getSysAdmin().getCreateAdminId());
        }
        if (title != null) {
            criteria.andLike(TITLE, "%" + title + "%");
        }
        List<ElCouponList> elCouponLists = elCouponListMapper.selectByExample(example);
        for (ElCouponList elCouponList : elCouponLists) {
            Example e = new Example(ElCouponListCoupon.class);
            e.createCriteria().andEqualTo(COLUMN_EL_COUPON_ID, elCouponList.getId());
            List<ElCouponListCoupon> elCouponListCoupons = elCouponListCouponMapper.selectByExample(e);
            if (elCouponListCoupons.size() <= 0) {
                return ResponseFactory.sucData("该礼包还未添加优惠券");
            }
            for (ElCouponListCoupon elCouponListCoupon : elCouponListCoupons) {
                ElectronicCoupons electronicCoupons = coMapper.selectByPrimaryKey(elCouponListCoupon.getCouponId());
                if (electronicCoupons != null) {
                    elCouponListCoupon.setCouponName(electronicCoupons.getTitle());
                }
            }
            elCouponList.setElCouponListCoupons(elCouponListCoupons);
        }
        PageInfo pageInfo = new PageInfo<>(elCouponLists);
        return ResponseFactory.page(pageInfo, pageInfo.getTotal(), pageInfo.getPages(),
                pageInfo.getPageNum(), pageInfo.getPageSize());
    }

    /**
     * @param: phone
     * @param: partyPackJson
     * @description: 后台赠送用户优惠券礼包
     * @author: LxH
     * @time: 2020/10/14 0014 下午 5:50
     */
    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
    public Response givePartyPack(String phone, String partyPackJson) {
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
        List<PartyPackJsonVo> partyPackJsonVos = JSONObject.parseArray(partyPackJson, PartyPackJsonVo.class);
        givePartyPackByUser(store,appUser,partyPackJsonVos,webUserInfo);
        return ResponseFactory.sucMsg("赠送成功");
    }

    /**
     * @param: page
     * @param: title
     * @param: phone
     * @param: storeId
     * @param: startTime
     * @param: endTime
     * @description: 后台赠送礼包记录
     * @author: LxH
     * @time: 2020/10/15 0015 上午 10:14
     */
    @Override
    public Response partyPackRecord(PageQuery page, String title, String phone, Integer storeId, Date startTime, Date endTime) {
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        Example example = new Example(ElUserCouponList.class);
        example.orderBy(CREATED);
        Example.Criteria criteria = example.createCriteria();
        if (startTime!=null&&endTime!=null) {
            criteria.andBetween(CREATED,startTime,endTime);
        }
        if (title!=null) {
            criteria.andLike(TITLE,"%"+title+"%");
        }
        if (phone!=null) {
            criteria.andEqualTo(PHONE,phone);
        }
        if (storeId!=null) {
            criteria.andEqualTo(STORE_ID,storeId);
        }
        /*if (webUserInfo.getRoleId() == 3) {
            criteria.andEqualTo(CREAT_ADMIN,webUserInfo.getSysAdmin().getId());
        }
        if (webUserInfo.getRoleId() == 5) {
            criteria.andEqualTo(ADMIN_ID,webUserInfo.getSysAdmin().getId());
        }*/
        criteria.andEqualTo(STATUS, 1);
        List<ElUserCouponList> elUserCouponLists = elUserCouponListMapper.selectByExample(example);
        for (ElUserCouponList elUserCouponList : elUserCouponLists) {
            User user = userMapper.selectByPrimaryKey(elUserCouponList.getUserId());
            elUserCouponList.setUserName(user.getNickname());
            Store store = v4StroeMapper.selectByPrimaryKey(elUserCouponList.getStoreId());
            elUserCouponList.setStoreName(store.getName());
        }
        PageInfo pageInfo = new PageInfo<>(elUserCouponLists);
        return ResponseFactory.page(pageInfo, pageInfo.getTotal(), pageInfo.getPages(),
                pageInfo.getPageNum(), pageInfo.getPageSize());
    }

    private void givePartyPackByUser(Store store, AppUser appUser, List<PartyPackJsonVo> partyPackJsonVos, WebUserInfo webUserInfo) {
        for (PartyPackJsonVo partyPackJsonVo : partyPackJsonVos) {
            ElUserCouponList elUserCouponList = new ElUserCouponList();
            ElCouponList elCouponList = elCouponListMapper.selectByPrimaryKey(partyPackJsonVo.getPartyPackId());
            Long aLong = orderHelper.genOrderNo(4, 12);
            elUserCouponList.setId(aLong).setAdminId(store.getAdminId()).setElCouponListId(partyPackJsonVo.getPartyPackId()).
                    setQuantity(partyPackJsonVo.getQuantity()).setStoreId(store.getId()).setUserId(appUser.getId()).
                    setCreated(new Date()).setUpdated(new Date()).setCreatAdminId(webUserInfo.getSysAdmin().getCreateAdminId()).
                    setTitle(elCouponList.getTitle()).setPhone(appUser.getPhone());
            int i = elUserCouponListMapper.insertSelective(elUserCouponList);
            if (i < 1) {
                throw new ServiceException(ErrorCode.ERROR.getCode(), "用户添加礼包失败");
            }
            Example example = new Example(ElCouponListCoupon.class);
            example.createCriteria().andEqualTo(COLUMN_EL_COUPON_ID,partyPackJsonVo.getPartyPackId());
            List<ElCouponListCoupon> elCouponListCoupons = elCouponListCouponMapper.selectByExample(example);
            for (ElCouponListCoupon elCouponListCoupon : elCouponListCoupons) {
                ElUserCoupon elUserCoupon = new ElUserCoupon();
                Long bLong = orderHelper.genOrderNo(4, 12);
                elUserCoupon.setId(bLong);
                elUserCoupon.setAdminId(store.getAdminId());
                elUserCoupon.setCouponId(elCouponListCoupon.getCouponId());
                elUserCoupon.setQuantity(elCouponListCoupon.getQuantity());
                elUserCoupon.setStatus((byte) 1);
                elUserCoupon.setUserId(appUser.getId());
                elUserCoupon.setTotalQuantity(elCouponListCoupon.getQuantity());
                elUserCoupon.setStoreId(store.getId());
                elUserCoupon.setCreated(new Date());
                elUserCoupon.setUpdated(new Date());
                int insert = elUserCouponMapper.insert(elUserCoupon);
                if (insert < 1) {
                    throw new ServiceException(ErrorCode.ERROR.getCode(), "用户添加礼包失败");
                }
            }
        }
    }
}
