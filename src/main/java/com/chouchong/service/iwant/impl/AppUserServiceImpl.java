package com.chouchong.service.iwant.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.iwant.appUser.AppUserMapper;
import com.chouchong.entity.iwant.appUser.AppUser;
import com.chouchong.service.iwant.AppUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yy
 * @date 2018/7/9
 **/

@Service
public class AppUserServiceImpl implements AppUserService{
    @Autowired
    private AppUserMapper appUserMapper;

    /**
     * 获得APP用户列表
     *
     * @param: [page 分页信息, search 查询条件]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @Override
    public Response getAppUserList(PageQuery page, String search) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        JSONObject jsonObject = JSON.parseObject(search);
        Map map = new HashMap();
        map.put("nickname",jsonObject.getString("nickname"));
        map.put("district",jsonObject.getString("district"));
        map.put("phone",jsonObject.getString("phone"));
        map.put("gender",jsonObject.getInteger("gender"));
        map.put("age",jsonObject.getInteger("age"));
        map.put("status",jsonObject.getInteger("status"));
        List<AppUser> appUsers = appUserMapper.selectBySearch(map);
        PageInfo pageInfo = new PageInfo<>(appUsers);
        return ResponseFactory.page(appUsers, pageInfo.getTotal(),
                pageInfo.getPages(), pageInfo.getPageNum(), pageInfo.getPageSize());
    }

    /**
     * 通过电话查询APP用户
     *
     * @param: [page, phone]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @Override
    public Response getAppUserByPhone(PageQuery page, String phone) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<AppUser> appUsers = appUserMapper.selectByPhone(phone);
        PageInfo pageInfo = new PageInfo<>(appUsers);
        return ResponseFactory.page(appUsers,pageInfo.getTotal(),
                pageInfo.getPages(), pageInfo.getPageNum(), pageInfo.getPageSize());
    }

    /**
     * 改变APP用户状态
     *
     * @param: [id APP用户id, status APP用户状态]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @Override
    public Response changeStatus(Integer id, Integer status) {
        AppUser appUser = appUserMapper.selectByPrimaryKey(id);
        if (appUser == null) {
            return ResponseFactory.err("用户不存在");
        }
        appUser.setStatus(status.byteValue());
        appUser.setUpdated(new Date());
        int count = appUserMapper.updateByPrimaryKey(appUser);
        if (count == 1) {
            return ResponseFactory.sucMsg("设置成功");
        }
        return ResponseFactory.err("设置失败");
    }
}
