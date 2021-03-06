package com.chouchong.service.gift.coupon.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.chouchong.common.*;
import com.chouchong.dao.gift.bpItem.BpItemMapper;
import com.chouchong.dao.gift.coupon.CouponMapper;
import com.chouchong.dao.gift.coupon.CouponSendRecordMapper;
import com.chouchong.dao.gift.coupon.CouponUseRecordMapper;
import com.chouchong.dao.iwant.merchant.MerchantMapper;
import com.chouchong.entity.gift.bpItem.BpItem;
import com.chouchong.entity.gift.coupon.Coupon;
import com.chouchong.entity.gift.coupon.CouponSendRecord;
import com.chouchong.entity.gift.coupon.CouponUseRecord;
import com.chouchong.entity.iwant.merchant.Merchant;
import com.chouchong.entity.webUser.SysAdmin;
import com.chouchong.redis.MRedisTemplate;
import com.chouchong.service.gift.coupon.CouponService;
import com.chouchong.service.gift.coupon.vo.CouponVo;
import com.chouchong.service.webUser.vo.WebUserInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yy
 * @date 2018/7/9
 **/

@Service
@Transactional(rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
public class CouponServiceImpl implements CouponService{
    @Autowired
    private CouponMapper couponMapper;

    @Autowired
    private CouponSendRecordMapper couponSendRecordMapper;

    @Autowired
    private CouponUseRecordMapper couponUseRecordMapper;

    @Autowired
    private MerchantMapper merchantMapper;

    @Autowired
    private BpItemMapper bpItemMapper;

    @Autowired
    private OrderHelper orderHelper;

    @Autowired
    private MRedisTemplate mRedisTemplate;

    /**
     * 获得优惠券列表
     *
     * @param: [page 分页信息, title 优惠券标题, adminId 创建者, brandName 品牌名称]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/11
     */
    @Override
    public Response getCouponList(PageQuery page, String title, String brandName, String token) {

        // 根据token取出用户信息
        WebUserInfo webUserInfo = mRedisTemplate.get(token, new TypeReference<WebUserInfo>() {
        });
        if (webUserInfo == null) {
            return ResponseFactory.errNeedLogin(Constants.USER_TOKEN_ERROR);
        }
        SysAdmin sysAdmin = webUserInfo.getSysAdmin();
        Merchant merchant = merchantMapper.selectByAdminId(sysAdmin.getId());
        if (merchant == null) {
            return ResponseFactory.err("您还未申请商铺, 请先申请商铺!");
        }
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<Coupon> coupons = couponMapper.selectByTitle(title, merchant.getId(), brandName);
        PageInfo pageInfo = new PageInfo<>(coupons);
        return ResponseFactory.page(coupons, pageInfo.getTotal(),
                pageInfo.getPages(), pageInfo.getPageNum(), pageInfo.getPageSize());
    }

    /**
     * 添加优惠券
     *
     * @param: [coupon 优惠券信息]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/11
     */
    @Override
    public Response addCoupon(Coupon coupon, String token) {
        // 根据token取出用户信息
        WebUserInfo webUserInfo = mRedisTemplate.get(token, new TypeReference<WebUserInfo>() {
        });
        if (webUserInfo == null) {
            return ResponseFactory.errNeedLogin(Constants.USER_TOKEN_ERROR);
        }
        SysAdmin sysAdmin = webUserInfo.getSysAdmin();
        Merchant merchant = merchantMapper.selectByAdminId(sysAdmin.getId());
        if (merchant == null) {
            return ResponseFactory.err("您还未申请商铺!");
        }
        Coupon coupon1 = new Coupon();
        coupon1.setBrandName(coupon.getBrandName());
        coupon1.setCover(coupon.getCover());
        coupon1.setTitle(coupon.getTitle());
        coupon1.setMerchantId(merchant.getId());
        coupon1.setStatus((byte)1);
        coupon1.setAdminId(sysAdmin.getId());
        coupon1.setCreated(new Date());
        coupon1.setUpdated(new Date());
        int count = couponMapper.insert(coupon1);
        if (count == 1) {
            return ResponseFactory.sucMsg("添加成功!");
        }
        return ResponseFactory.err("添加失败");
    }

    @Override
    public Response updateCoupon(Coupon coupon) {
        Coupon coupon1 = couponMapper.selectByPrimaryKey(coupon.getId());
        if (coupon1 == null) {
            return ResponseFactory.err("无此优惠券");
        }
        coupon1.setBrandName(coupon.getBrandName());
        coupon1.setCover(coupon.getCover());
        coupon1.setTitle(coupon.getTitle());
        coupon1.setUpdated(new Date());
        int count = couponMapper.updateByPrimaryKey(coupon1);
        if (count == 1) {
            return ResponseFactory.sucMsg("修改成功!");
        }
        return ResponseFactory.err("修改失败");
    }

    /**
     * 删除优惠券
     *
     * @param: [id 优惠券id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/11
     */
    @Override
    public Response delCoupon(Integer id) {
        Coupon coupon = couponMapper.selectByPrimaryKey(id);
        if (coupon == null) {
            return ResponseFactory.err("无此优惠券");
        }
        coupon.setStatus((byte)3);
        coupon.setUpdated(new Date());
        int count =  couponMapper.updateByPrimaryKey(coupon);
        if (count == 1) {
            return ResponseFactory.sucMsg("删除成功!");
        }
        return ResponseFactory.err("删除失败");
    }

    /**
     * 修改优惠券状态
     *
     * @param: [id 优惠券id, status 优惠券状态]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/11
     */
    @Override
    public Response changeStatus(Integer id, Integer status) {
        Coupon coupon = couponMapper.selectByPrimaryKey(id);
        if (coupon == null) {
            return ResponseFactory.err("无此优惠券");
        }
        coupon.setStatus(status.byteValue());
        coupon.setUpdated(new Date());
        int count = couponMapper.updateByPrimaryKey(coupon);
        if (count == 1) {
            return ResponseFactory.sucMsg("设置成功!");
        }
        return ResponseFactory.err("设置失败!");
    }

    /**
     * 赠送优惠券
     *
     * @param: [userId app用户id, couponId 优惠券id, quantity 优惠券数量]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/11
     */
    @Override
    public Response giveCouponUser(Integer userId, Integer couponId, Integer quantity,String token) {
        // 背包实体 类型为存放用户优惠券
        BpItem bpItem = new BpItem();
        bpItem.setUserId(userId);
        bpItem.setUpdated(new Date());
        bpItem.setType((byte)3);
        bpItem.setTargetId(couponId);
        bpItem.setQuantity(quantity);
        bpItem.setCreated(new Date());
        bpItem.setId(orderHelper.genOrderNo(4,8));
        bpItem.setPrice(new BigDecimal(0));
        // 优惠券来源 后台赠送 type = 3
        Map map = new HashMap();
        map.put("type", 3);
        bpItem.setFrom(JSON.toJSONString(map));
        int count = bpItemMapper.insert(bpItem);
        if (count  < 0) {
            return ResponseFactory.err("赠送失败!");
        }
        // 添加赠送记录
        int i = addSendRecord(userId, couponId, quantity, token,bpItem.getId());
        if (i< 0) {
            return ResponseFactory.err("添加赠送记录失败!");
        }
        return ResponseFactory.sucMsg("赠送成功!");
    }

    /**
     * 添加优惠券赠送记录
     * @param userId
     * @param couponId
     * @param quantity
     * @param token
     * @return 1 正确  0 错误
     */
    private int addSendRecord(Integer userId, Integer couponId, Integer quantity,String token,Long bpId){
        // 根据token取出用户信息
        WebUserInfo webUserInfo = mRedisTemplate.get(token, new TypeReference<WebUserInfo>() {
        });
        if (webUserInfo == null) {
            return 0;
        }
        SysAdmin sysAdmin = webUserInfo.getSysAdmin();
        Merchant merchant = merchantMapper.selectByAdminId(sysAdmin.getId());
        if (merchant == null) {
            return 0;
        }
        Coupon coupon = couponMapper.selectByPrimaryKey(couponId);
        if (coupon == null){
            return 0;
        }
        CouponSendRecord record = new CouponSendRecord();
        record.setAdminId(sysAdmin.getId());
        record.setMerchantId(merchant.getId());
        record.setCouponId(couponId);
        record.setUserId(userId);
        record.setTitle(coupon.getTitle());
        record.setCover(coupon.getCover());
        record.setQuantity(quantity);
        record.setBpId(bpId);
        return couponSendRecordMapper.insert(record);
    }

    /**
     * 使用优惠券
     *
     * @param: [couponCode 优惠券码]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/11
     */
    @Override
    public Response useCoupon(Long couponCode,String token) {
        BpItem bpItem = bpItemMapper.selectByPrimaryKey(couponCode);
        if (bpItem == null) {
            return ResponseFactory.err("无此优惠券");
        }
        if (bpItem.getQuantity() < 1) {
            return ResponseFactory.err("优惠券数量不足");
        }
        // 判断类型是否为优惠券类型
        if (bpItem.getType() == (byte)3) {
            Integer couponId = bpItem.getTargetId();
            Coupon coupon = couponMapper.selectByPrimaryKey(couponId);
            // 判断该优惠券是否可用
            if (coupon.getStatus() == (byte)1) {
                int quantity = bpItem.getQuantity();
                // 若优惠券数量为1则删除该优惠券
                if (quantity == 1) {
                   int count = bpItemMapper.deleteByPrimaryKey(couponCode);
                   if (count == 0) {
                       return ResponseFactory.err("使用失败了");
                   }
                   // 添加使用记录
                    int i = addUserRecord(token, coupon, bpItem.getUserId(), couponCode);
                    if (i == 0) {
                        return ResponseFactory.err("添加使用记录失败");
                    }
                    return ResponseFactory.sucMsg("使用成功");
                }
                // 若优惠券数量大于1则减少该优惠券的数量
                if (quantity > 1) {
                    bpItem.setQuantity(quantity - 1);
                    bpItem.setUpdated(new Date());
                    int count = bpItemMapper.updateByPrimaryKey(bpItem);
                    if (count == 0) {
                        return ResponseFactory.err("使用失败了");
                    }
                    // 添加使用记录
                    int i = addUserRecord(token, coupon, bpItem.getUserId(), couponCode);
                    if (i == 0) {
                        return ResponseFactory.err("添加使用记录失败");
                    }
                    return ResponseFactory.sucMsg("使用成功");
                }
            } else {
                return ResponseFactory.err("使用失败,优惠券已被禁用或已删除");
            }
        }
        return ResponseFactory.err("使用失败");
    }


    /**
     * 添加优惠券使用记录
     * @return 1 正确 0 错误
     */
    private int addUserRecord(String token,Coupon coupon,Integer userId,Long bpId){
        // 根据token取出用户信息
        WebUserInfo webUserInfo = mRedisTemplate.get(token, new TypeReference<WebUserInfo>() {
        });
        if (webUserInfo == null) {
            return 0;
        }
        SysAdmin sysAdmin = webUserInfo.getSysAdmin();
        Merchant merchant = merchantMapper.selectByAdminId(sysAdmin.getId());
        if (merchant == null) {
            return 0;
        }
        CouponUseRecord record = new CouponUseRecord();
        record.setAdminId(sysAdmin.getId());
        record.setMerchantId(merchant.getId());
        record.setCouponId(coupon.getId());
        record.setUserId(userId);
        record.setTitle(coupon.getTitle());
        record.setCover(coupon.getCover());
        record.setBpId(bpId);
        return couponUseRecordMapper.insert(record);
    }



    /**
     * 获取优惠券赠送列表
     * @param page
     * @param token
     * @param title
     * @return
     */
    @Override
    public Response getSendRecordList(PageQuery page, String token, String title) {
        // 根据token取出用户信息
        WebUserInfo webUserInfo = mRedisTemplate.get(token, new TypeReference<WebUserInfo>() {
        });
        if (webUserInfo == null) {
            return ResponseFactory.errNeedLogin(Constants.USER_TOKEN_ERROR);
        }
        SysAdmin sysAdmin = webUserInfo.getSysAdmin();
        Merchant merchant = merchantMapper.selectByAdminId(sysAdmin.getId());
        if (merchant == null) {
            return ResponseFactory.err("您还未申请商铺, 请先申请商铺!");
        }
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<CouponVo> coupons = couponSendRecordMapper.selectByTitle(title, merchant.getId());
        PageInfo pageInfo = new PageInfo<>(coupons);
        return ResponseFactory.page(coupons, pageInfo.getTotal(),
                pageInfo.getPages(), pageInfo.getPageNum(), pageInfo.getPageSize());
    }



    /**
     * 删除优惠券赠送记录
     * @param sendId 优惠券赠送记录id
     * @return
     */
    @Override
    public Response deleteSendRecord(Integer sendId) {
        int i = couponSendRecordMapper.deleteByPrimaryKey(sendId);
        if (i == 1) {
            return ResponseFactory.sucMsg("删除成功!");
        }
        return ResponseFactory.err("删除失败");
    }



    /**
     * 获取优惠券使用列表
     * @param page
     * @param token
     * @param title
     * @return
     */
    @Override
    public Response getUseRecordList(PageQuery page, String token, String title) {
        // 根据token取出用户信息
        WebUserInfo webUserInfo = mRedisTemplate.get(token, new TypeReference<WebUserInfo>() {
        });
        if (webUserInfo == null) {
            return ResponseFactory.errNeedLogin(Constants.USER_TOKEN_ERROR);
        }
        SysAdmin sysAdmin = webUserInfo.getSysAdmin();
        Merchant merchant = merchantMapper.selectByAdminId(sysAdmin.getId());
        if (merchant == null) {
            return ResponseFactory.err("您还未申请商铺, 请先申请商铺!");
        }
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<CouponVo> coupons = couponUseRecordMapper.selectByTitle(title, merchant.getId());
        PageInfo pageInfo = new PageInfo<>(coupons);
        return ResponseFactory.page(coupons, pageInfo.getTotal(),
                pageInfo.getPages(), pageInfo.getPageNum(), pageInfo.getPageSize());
    }



    /**
     * 删除优惠券赠送记录
     * @param useId 优惠券使用记录id
     * @return
     */
    @Override
    public Response deleteUseRecord(Integer useId) {
        int i = couponUseRecordMapper.deleteByPrimaryKey(useId);
        if (i == 1) {
            return ResponseFactory.sucMsg("删除成功!");
        }
        return ResponseFactory.err("删除失败");
    }


}
