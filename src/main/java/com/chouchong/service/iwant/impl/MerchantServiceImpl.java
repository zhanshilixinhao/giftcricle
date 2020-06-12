package com.chouchong.service.iwant.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chouchong.common.*;
import com.chouchong.dao.iwant.appUser.AppUserMapper;
import com.chouchong.dao.iwant.merchant.MerchantMapper;
import com.chouchong.dao.webUser.SysAdminMapper;
import com.chouchong.entity.iwant.merchant.Merchant;
import com.chouchong.entity.webUser.SysAdmin;
import com.chouchong.service.iwant.MerchantService;
import com.chouchong.service.iwant.vo.MerchantApplyVo;
import com.chouchong.service.iwant.vo.MerchantVo;
import com.chouchong.utils.properties.ServiceProperties;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author yy
 * @date 2018/7/6
 **/

@Service
public class MerchantServiceImpl implements MerchantService{
    @Autowired
    private MerchantMapper merchantMapper;

    @Autowired
    private SysAdminMapper sysAdminMapper;

    @Autowired
    private AppUserMapper appUserMapper;

    @Autowired
    private ServiceProperties serviceProperties;

    /**
     * 获得商铺列表
     *
     * @param: [page 分页信息, search 查询条件]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @Override
    public Response GetMerchantList(PageQuery page, String search) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        JSONObject jsonObject = JSON.parseObject(search);
        Map map = new HashMap();
        map.put("name",jsonObject.getString("name"));
        map.put("address",jsonObject.getString("address"));
        map.put("person",jsonObject.getString("person"));
        map.put("phone",jsonObject.getString("phone"));
        map.put("status",jsonObject.getInteger("status"));
        List<MerchantVo> merchants = merchantMapper.selectBySearch(map);
        PageInfo pageInfo = new PageInfo<>(merchants);
        return ResponseFactory.page(merchants, pageInfo.getTotal(),
                pageInfo.getPages(), pageInfo.getPageNum(), pageInfo.getPageSize());
    }

    /**
     * 审核通过
     *
     * @param: [id 商家id, status 商家状态, username 后台用户名]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @Override
    public Response changePassStatus(Integer id, Integer status, String username) {
        Merchant merchant = merchantMapper.selectByPrimaryKey(id);
        if (merchant == null) {
            return ResponseFactory.err("商家不存在!");
        }
        if (merchant.getStatus().equals(status)) {
            return ResponseFactory.err("该商家已审核成功, 请勿重复点击");
        }
        merchant.setUpdated(new Date());
        merchant.setStatus(status.byteValue());
        // 修改商家状态
        int count = merchantMapper.updateByPrimaryKey(merchant);
        if (count == 1) {
            // 为商家绑定后台用户
            if (merchant.getAdminId() == null) {
                SysAdmin sysAdmin = sysAdminMapper.selectByUserName(username);
                if (sysAdmin == null) {
                    return ResponseFactory.err("用户名不存在");
                }
                Merchant mhs = merchantMapper.selectByAdminId(sysAdmin.getId());
                if (mhs != null) {
                    return ResponseFactory.err("该用户名已绑定了商家");
                }
                merchant.setAdminId(sysAdmin.getId());
                count = merchantMapper.updateByPrimaryKey(merchant);
                if (count == 1) {
                    return ResponseFactory.sucMsg("操作成功!");
                }
            } else {
                // 改变商家的后台用户为启用
                SysAdmin sysAdmin = sysAdminMapper.selectByPrimaryKey(merchant.getAdminId());
                if (sysAdmin != null) {
                    sysAdmin.setActive((byte)1);
                    sysAdminMapper.updateByPrimaryKey(sysAdmin);
                }
                return ResponseFactory.sucMsg("操作成功!");
            }

        }
        return ResponseFactory.err("操作失败!");
    }

    /**
     * 审核失败
     *
     * @param: [id 商家id, status 商家状态]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @Override
    public Response changeFailStatus(Integer id, Integer status) {
        Merchant merchant = merchantMapper.selectByPrimaryKey(id);
        if (merchant == null) {
            return ResponseFactory.err("商家不存在!");
        }
        if (merchant.getStatus().equals(status)) {
            return ResponseFactory.err("该商家已审核失败, 请勿重复点击");
        }
        merchant.setUpdated(new Date());
        merchant.setStatus(status.byteValue());
        int count = merchantMapper.updateByPrimaryKey(merchant);
        // 改变商家的后台用户为禁用
        if (merchant.getAdminId() != null) {
            SysAdmin sysAdmin = sysAdminMapper.selectByPrimaryKey(merchant.getAdminId());
            if (sysAdmin != null) {
                sysAdmin.setActive((byte)2);
                sysAdminMapper.updateByPrimaryKey(sysAdmin);
            }
        }
        if (count == 1) {
            return ResponseFactory.sucMsg("操作成功!");
        }
        return ResponseFactory.err("操作失败!");
    }

    /**
     * 商家认证申请
     *
     * @param: [details 用户认证信息, merchantVo 商家信息]
     * @return: com.chouchongkeji.goexplore.common.Response
     * @author: yy
     * @Date: 2018/6/20
     */
    @Override
    public Response applyMerchant(MerchantApplyVo vo) {
        Merchant merchant = merchantMapper.selectByPhone(vo.getPhone());
        if (merchant != null) {
            if (merchant.getStatus() == Constants.MERCHANT_STATUS.PASS){
                return ResponseFactory.err("商家认证已提交,并且已通过!");
            }else if (merchant.getStatus() == Constants.MERCHANT_STATUS.NO_PASS){
                // 被驳回，重新提交
                merchant.setUserId(0);
                merchant.setUpdated(new Date());
                merchant.setStatus(Constants.MERCHANT_STATUS.IN_REVIEW);
                merchant.setRegistrationNo(vo.getRegistrationNo());
                merchant.setPhone(vo.getPhone());
                if (vo.getOtherPics() != null && vo.getOtherPics().size() > 0) {
                    merchant.setOtherPics(JSON.toJSONString(vo.getOtherPics()));
                }
                merchant.setName(vo.getName());
                merchant.setLicensePic(vo.getLicensePic());
                merchant.setLegalPerson(vo.getLegalPerson());
                merchant.setCreated(new Date());
                merchant.setAddress(vo.getAddress());
                int i = merchantMapper.updateByPrimaryKeySelective(merchant);
                if (i < 1) {
                    return ResponseFactory.err("申请失败");
                }
                return ResponseFactory.sucMsg("重新申请成功");
            }else {
                return ResponseFactory.err("商家认证审核中!");
            }
        }
        merchant = new Merchant();
        merchant.setUserId(0);
        merchant.setUpdated(new Date());
        merchant.setStatus(Constants.MERCHANT_STATUS.IN_REVIEW);
        merchant.setRegistrationNo(vo.getRegistrationNo());
        merchant.setPhone(vo.getPhone());
        if (vo.getOtherPics() != null && vo.getOtherPics().size() > 0) {
            merchant.setOtherPics(JSON.toJSONString(vo.getOtherPics()));
        }
        merchant.setName(vo.getName());
        merchant.setLicensePic(vo.getLicensePic());
        merchant.setLegalPerson(vo.getLegalPerson());
        merchant.setCreated(new Date());
        merchant.setAddress(vo.getAddress());
        int count = merchantMapper.insert(merchant);
        if (count == 1) {
            return ResponseFactory.sucMsg("申请成功");
        }
        return ResponseFactory.err("申请失败");
    }
}
