package com.chouchong.service.webUser.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.chouchong.common.*;
import com.chouchong.dao.iwant.merchant.MerchantMapper;
import com.chouchong.dao.v3.StoreMapper;
import com.chouchong.dao.webUser.SysAdminMapper;
import com.chouchong.dao.webUser.SysAdminRoleMapper;
import com.chouchong.dao.webUser.SysRoleMapper;
import com.chouchong.entity.iwant.merchant.Merchant;
import com.chouchong.entity.v3.Store;
import com.chouchong.entity.webUser.*;
import com.chouchong.exception.ServiceException;
import com.chouchong.redis.MRedisTemplate;
import com.chouchong.service.webUser.WebUserService;
import com.chouchong.service.webUser.vo.SysAdminVo;
import com.chouchong.service.webUser.vo.WebUserInfo;
import com.chouchong.utils.IDUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yy
 * @date 2018/6/21
 **/

@Service
@Transactional(rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
public class WebUserServiceImpl implements WebUserService {
    @Autowired
    private SysAdminMapper sysAdminMapper;

    @Autowired
    private SysAdminRoleMapper sysAdminRoleMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private MerchantMapper merchantMapper;

    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    private MRedisTemplate mRedisTemplate;

    @Autowired
    private HttpServletRequest httpServletRequest;

    /**
     * 后台用户登录
     *
     * @param: [username 用户名, password 密码]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @Override
    public Response login(String username, String password, Integer client) {
        password = Utils.toMD5(password + Constants.ADMINPWD);
        SysAdmin sysAdmin = sysAdminMapper.selectByUsernameAndPwd(username, password);
        if (sysAdmin == null) {
            return ResponseFactory.err("用户名或密码错误!");
        }
        if (sysAdmin.getActive() != 1) {
            return ResponseFactory.err("该账号已被禁用!");
        }
        // 判断用户角色是否被禁用
        SysAdminRole sysAdminRole = sysAdminRoleMapper.selectByAdminId(sysAdmin.getId());
        if (sysAdminRole == null) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "用户角色错误");
        }
        SysRole sysRole = sysRoleMapper.selectByPrimaryKey(sysAdminRole.getRoleId());
        if (sysRole != null) {
            if (sysRole.getActive() == (byte) 2) {
                return ResponseFactory.err("该账号角色已被禁用!");
            }
        }
        // 小程序管理端助手只能门店账号登录
        if (client != null && sysAdminRole.getRoleId() != 5) {
            return ResponseFactory.err("程序管理端助手只能门店账号登录!");
        }
        // 查看平台商账号是否过审核
        if (sysAdminRole.getRoleId() == 3) {
            Merchant merchant = merchantMapper.selectByAdminId(sysAdmin.getId());
            if (merchant == null) {
                return ResponseFactory.err("该平台商账号还未审核!");
            }
        } else if (sysAdminRole.getRoleId() == 5) {
            // 查看门店账号是否与门店关联
            Store store = storeMapper.selectByAdminId(sysAdmin.getId());
            if (store == null) {
                return ResponseFactory.err("该门店账号还未与门店关联!");
            }
        }

        sysAdmin.setLoginCount(sysAdmin.getLoginCount() + 1);
        sysAdminMapper.updateByPrimaryKey(sysAdmin);
        // 取出菜单列表
        List<String> menus = sysAdminMapper.selectMenuById(sysAdmin.getId());
        // 取出权限列表
        List<String> permissions = sysAdminMapper.selectPermissionsById(sysAdmin.getId());
        // 取出权限集合
        List<SysFunctionPermission> permissionList = sysAdminMapper.selectAllPermissionsById(sysAdmin.getId());
        WebUserInfo webUserInfo = new WebUserInfo();
        webUserInfo.setPermissionList(permissionList);
        webUserInfo.setMenus(menus);
        webUserInfo.setPermissions(permissions);
        webUserInfo.setSysAdmin(sysAdmin);
        webUserInfo.setRoleId(sysAdminRole.getRoleId());
        // 判断之前是否有缓存
        String tKey = IDUtils.genAgentTokenKey(sysAdmin.getId());
        String token = mRedisTemplate.getString(tKey);
//        if (StringUtils.isNotBlank(token)) {
//            mRedisTemplate.del(token);
//            return ResponseFactory.sucData(token);
//        }
        // 重新生成token
        token = IDUtils.genUUID();
        // 重新保存token
        // token有效期120分钟
        long expire = 120 * 60;
        mRedisTemplate.setString(tKey, token, expire);
        mRedisTemplate.set(token, webUserInfo, expire);
        return ResponseFactory.sucData(token);
    }

    /**
     * 获取用户信息
     *
     * @param: [token]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @Override
    public Response info(String token) {
        // 根据token取出用户信息
        WebUserInfo webUserInfo = mRedisTemplate.get(token, new TypeReference<WebUserInfo>() {
        });
        if (webUserInfo == null) {
            return ResponseFactory.errNeedLogin(Constants.USER_TOKEN_ERROR);
        }
        // 该权限集合数据量大，对前端无用, 设置为null传值
        webUserInfo.setPermissionList(null);
        webUserInfo.getSysAdmin().setAvatar("https://liyuquan.cn/static"+webUserInfo.getSysAdmin().getAvatar());
        return ResponseFactory.sucData(webUserInfo);
    }

    /**
     * 修改密码
     *
     * @param: [oldPassword 老密码, newPassword 新密码, token]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @Override
    public Response updatePassword(String oldPassword, String newPassword, String token) {
        // 根据token取出用户信息
        WebUserInfo webUserInfo = mRedisTemplate.get(token, new TypeReference<WebUserInfo>() {
        });
        if (webUserInfo == null) {
            return ResponseFactory.errNeedLogin(Constants.USER_TOKEN_ERROR);
        }
        SysAdmin sysAdmin = webUserInfo.getSysAdmin();
        oldPassword = Utils.toMD5(oldPassword + Constants.ADMINPWD);
        if (!oldPassword.equals(sysAdmin.getPassword())) {
            return ResponseFactory.err("原密码不正确!");
        }
        sysAdmin.setPassword(Utils.toMD5(newPassword + Constants.ADMINPWD));
        sysAdmin.setUpdateAdminId(sysAdmin.getId());
        sysAdmin.setUpdated(new Date());
        sysAdminMapper.updateByPrimaryKeySelective(sysAdmin);
        return ResponseFactory.sucMsg("密码修改成功!");
    }

    /**
     * 退出登录
     *
     * @param: [token]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @Override
    public Response logOut(String token) {
        // 删除用户token
        mRedisTemplate.del(token);
        return ResponseFactory.sucMsg("退出登录成功");
    }

    /**
     * 获得后台用户列表
     *
     * @param: [page 分页信息, search 查询条件]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @Override
    public Response getWebUserList(PageQuery page, String search) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        JSONObject jsonObject = JSON.parseObject(search);
        Map map = new HashMap();
        map.put("username", jsonObject.getString("username"));
        map.put("role", jsonObject.getInteger("role"));
        map.put("phone", jsonObject.getString("phone"));
        map.put("gender", jsonObject.getInteger("gender"));
        map.put("status", jsonObject.getInteger("status"));
        List<SysAdminVo> sysAdmins = sysAdminMapper.selectBySearch(map);
        PageInfo pageInfo = new PageInfo<>(sysAdmins);
        return ResponseFactory.page(sysAdmins, pageInfo.getTotal(),
                pageInfo.getPages(), pageInfo.getPageNum(), pageInfo.getPageSize());
    }

    /**
     * 获得后台用户列表（新加）
     *
     * @param: [page 分页信息, search 查询条件]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @Override
    public Response getWebUserList1(PageQuery page, String username, String phone,
                                    Integer gender, Integer status) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        Integer roleId = null;
        Integer cAdminId = null;
        if (webUserInfo.getRoleId() == 2) {
            roleId = webUserInfo.getRoleId();
        }
        if (webUserInfo.getRoleId() == 3) {
            cAdminId = webUserInfo.getSysAdmin().getId();
        }
        List<SysAdminVo> sysAdmins = sysAdminMapper.selectBySearch1(username, phone, gender, status, roleId, cAdminId);
        PageInfo pageInfo = new PageInfo<>(sysAdmins);
        return ResponseFactory.page(sysAdmins, pageInfo.getTotal(),
                pageInfo.getPages(), pageInfo.getPageNum(), pageInfo.getPageSize());
    }

    /**
     * 添加后台用户
     *
     * @param: [sysAdminVo 用户信息]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @Override
    public Response addWebUser(SysAdminVo sysAdminVo) {
        SysAdmin admin = sysAdminMapper.selectByUserName(sysAdminVo.getUsername());
        if (admin != null) {
            return ResponseFactory.err("用户名已存在");
        }
        WebUserInfo webUserInfo = mRedisTemplate.get(sysAdminVo.getToken(), new TypeReference<WebUserInfo>() {
        });
        if (webUserInfo == null) {
            return ResponseFactory.errNeedLogin(Constants.USER_TOKEN_ERROR);
        }
        SysAdmin createAdmin = webUserInfo.getSysAdmin();
        SysAdmin sysAdmin = createVo(sysAdminVo);
        sysAdmin.setUpdateAdminId(createAdmin.getId());
        sysAdmin.setCreateAdminId(createAdmin.getId());
        // 添加后台用户
        sysAdminMapper.insert(sysAdmin);
        SysAdminRole sysAdminRole = new SysAdminRole();
        sysAdminRole.setCreated(new Date());
        sysAdminRole.setAdminId(sysAdmin.getId());
        sysAdminRole.setRoleId(sysAdminVo.getRoleId());
        // 添加后台用户对应的角色关系
        sysAdminRoleMapper.insert(sysAdminRole);
        return ResponseFactory.sucMsg("添加成功");
    }

    /**
     * 获得后台用户详情
     *
     * @param: [id 后台用户id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @Override
    public Response getSysAdminDetail(Integer id) {
        SysAdminVo sysAdminVo = sysAdminMapper.selectByAdminId(id);
        if (sysAdminVo == null) {
            return ResponseFactory.err("用户不存在");
        }
        return ResponseFactory.sucData(sysAdminVo);
    }

    /**
     * 修改后台用户
     *
     * @param: [sysAdminVo 后台用户信息]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @Override
    public Response updateSysAdmin(SysAdminVo sysAdminVo) {
        SysAdmin sysAdmin = sysAdminMapper.selectByPrimaryKey(sysAdminVo.getId());
        SysAdminVo sysAdminVo1 = sysAdminMapper.selectByAdminId(sysAdminVo.getId());
        if (sysAdmin == null) {
            return ResponseFactory.err("用户不存在");
        }
        WebUserInfo webUserInfo = mRedisTemplate.get(sysAdminVo.getToken(), new TypeReference<WebUserInfo>() {
        });
        if (webUserInfo == null) {
            return ResponseFactory.errNeedLogin(Constants.USER_TOKEN_ERROR);
        }
        // 获得修改此用户的用户
        SysAdmin updateAdmin = webUserInfo.getSysAdmin();
        if (sysAdminVo.getPassword() != null) {
            // 设置新的密码
            String password = Utils.toMD5(sysAdminVo.getPassword() + Constants.ADMINPWD);
            sysAdmin.setPassword(password);
        }
        sysAdmin.setUpdateAdminId(updateAdmin.getId());
        sysAdmin.setWechat(sysAdminVo.getWechat());
        sysAdmin.setRealName(sysAdminVo.getRealName());
        sysAdmin.setQq(sysAdminVo.getQq());
        sysAdmin.setIdNumber(sysAdminVo.getIdNumber());
        sysAdmin.setEmail(sysAdminVo.getEmail());
        sysAdmin.setAvatar(sysAdminVo.getAvatar());
        sysAdmin.setUpdated(new Date());
        sysAdmin.setGender(sysAdminVo.getGender());
        sysAdmin.setPhone(sysAdminVo.getPhone());
        // 修改用户信息
        sysAdminMapper.updateByPrimaryKey(sysAdmin);
        // 先删除该用户原有角色
        SysAdminRoleKey oldSysAdminRoleKey = new SysAdminRoleKey(sysAdminVo.getId(), sysAdminVo1.getRoleId());
        sysAdminRoleMapper.deleteByPrimaryKey(oldSysAdminRoleKey);
        SysAdminRole sysAdminRole = new SysAdminRole();
        sysAdminRole.setAdminId(sysAdminVo.getId());
        sysAdminRole.setRoleId(sysAdminVo.getRoleId());
        sysAdminRole.setCreated(new Date());
        // 添加新的角色
        int count = sysAdminRoleMapper.insert(sysAdminRole);
        if (count == 1) {
            return ResponseFactory.sucMsg("修改成功");
        }
        return ResponseFactory.err("修改失败");
    }

    /**
     * 改变后台用户装填
     *
     * @param: [id 后台用户id, status 后台用户状态, token]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @Override
    public Response changeStatus(Integer id, Integer status, String token) {
        SysAdmin sysAdmin = sysAdminMapper.selectByPrimaryKey(id);
        if (sysAdmin == null) {
            return ResponseFactory.err("用户不存在");
        }
        WebUserInfo webUserInfo = mRedisTemplate.get(token, new TypeReference<WebUserInfo>() {
        });
        if (webUserInfo == null) {
            return ResponseFactory.errNeedLogin(Constants.USER_TOKEN_ERROR);
        }
        // 获得修改此用户的用户
        SysAdmin updateAdmin = webUserInfo.getSysAdmin();
        sysAdmin.setActive(status.byteValue());
        sysAdmin.setUpdateAdminId(updateAdmin.getId());
        sysAdmin.setUpdated(new Date());
        int count = sysAdminMapper.updateByPrimaryKey(sysAdmin);
        if (count == 1) {
            return ResponseFactory.sucMsg("修改成功");
        }
        return ResponseFactory.err("修改失败");
    }

    /**
     * 删除后台用户
     *
     * @param: [id 用户id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @Override
    public Response delSysAdmin(Integer id) {
        // 删除用户
        int count = sysAdminMapper.deleteByPrimaryKey(id);
        if (count == 1) {
            // 删除用户角色关系
            SysAdminRole sysAdminRole = sysAdminRoleMapper.selectByAdminId(id);
            if (sysAdminRole != null) {
                sysAdminRoleMapper.deleteByPrimaryKey(new SysAdminRoleKey(sysAdminRole.getAdminId(), sysAdminRole.getRoleId()));
            }
            // 如果商铺的后台用户是此用户则设置此商铺的后台用户id为null
            Merchant merchant = merchantMapper.selectByAdminId(id);
            if (merchant != null) {
                merchant.setAdminId(null);
                merchantMapper.updateByPrimaryKey(merchant);
            }
            return ResponseFactory.sucMsg("删除成功");
        }
        return ResponseFactory.err("删除失败");
    }

    /**
     * 初始化后台用户(用于添加后台用户)
     *
     * @param: [sysAdminVo]
     * @return: com.chouchong.entity.webUser.SysAdmin
     * @author: yy
     * @Date: 2018/7/20
     */
    private SysAdmin createVo(SysAdminVo sysAdminVo) {
        SysAdmin sysAdmin = new SysAdmin();
        sysAdmin.setActive((byte) 1);
        sysAdmin.setWechat(sysAdminVo.getWechat());
        sysAdmin.setRealName(sysAdminVo.getRealName());
        sysAdmin.setQq(sysAdminVo.getQq());
        sysAdmin.setIdNumber(sysAdminVo.getIdNumber());
        sysAdmin.setEmail(sysAdminVo.getEmail());
        sysAdmin.setAvatar(sysAdminVo.getAvatar());
        sysAdmin.setUsername(sysAdminVo.getUsername());
        String password = Utils.toMD5(sysAdminVo.getPassword() + Constants.ADMINPWD);
        sysAdmin.setPassword(password);
        sysAdmin.setCreated(new Date());
        sysAdmin.setUpdated(new Date());
        sysAdmin.setGender(sysAdminVo.getGender());
        sysAdmin.setPhone(sysAdminVo.getPhone());
        sysAdmin.setLoginCount(0);
        return sysAdmin;
    }
}
