package com.chouchong.service.webUser.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.chouchong.common.Constants;
import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.webUser.*;
import com.chouchong.entity.webUser.*;
import com.chouchong.redis.MRedisTemplate;
import com.chouchong.service.webUser.WebPermissionService;
import com.chouchong.service.webUser.vo.MenuVo;
import com.chouchong.service.webUser.vo.RoleDetail;
import com.chouchong.service.webUser.vo.RoleVo;
import com.chouchong.service.webUser.vo.WebUserInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author yy
 * @date 2018/7/13
 **/
@Service
@Transactional(rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
public class WebPermissionServiceImpl implements WebPermissionService {
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Autowired
    private SysAdminRoleMapper sysAdminRoleMapper;

    @Autowired
    private SysFunctionPermissionMapper sysFunctionPermissionMapper;

    @Autowired
    private SysRoleFunctionPermissionMapper sysRoleFunctionPermissionMapper;

    @Autowired
    private MRedisTemplate mRedisTemplate;

    /**
     * 获得后台角色列表
     *
     * @param: [page 分页信息, search 查询条件]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @Override
    public Response getRoleList(PageQuery page, String search) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        JSONObject jsonObject = JSON.parseObject(search);
        Map map = new HashMap();
        map.put("name",jsonObject.getString("name"));
        map.put("description",jsonObject.getString("description"));
        map.put("status",jsonObject.getInteger("status"));
        List<SysRole> sysRoles = sysRoleMapper.selectBySearch(map);
        PageInfo pageInfo = new PageInfo<>(sysRoles);
        return ResponseFactory.page(sysRoles, pageInfo.getTotal(),
                pageInfo.getPages(), pageInfo.getPageNum(), pageInfo.getPageSize());
    }

    /**
     * 获得所有的后台菜单
     *
     * @param: []
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @Override
    public Response getAllMenus() {
        List<MenuVo> menuVos = sysMenuMapper.selectAllMenu();
        return ResponseFactory.sucData(menuVos);
    }

    /**
     * 添加后台角色
     *
     * @param: [roleVo 角色信息]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @Override
    public Response addRole(RoleVo roleVo) {
        if (roleVo != null) {
            SysRole sysRole = new SysRole();
            sysRole.setUpdated(new Date());
            // 根据token取出用户信息
            WebUserInfo webUserInfo = mRedisTemplate.get(roleVo.getToken(), new TypeReference<WebUserInfo>() {
            });
            if (webUserInfo == null) {
                return ResponseFactory.errNeedLogin(Constants.USER_TOKEN_ERROR);
            }
            SysAdmin sysAdmin = webUserInfo.getSysAdmin();
            // 添加一个角色
            sysRole.setUpdateAdminId(sysAdmin.getId());
            sysRole.setName(roleVo.getName());
            sysRole.setPermissionNames(roleVo.getNames());
            sysRole.setDescription(roleVo.getDescription());
            sysRole.setCreated(new Date());
            sysRole.setCreateAdminId(sysAdmin.getId());
            sysRole.setActive((byte)1);
            int count = sysRoleMapper.insert(sysRole);

            // 添加角色可访问的方法权限
            List<SysRoleFunctionPermission> permissions = new ArrayList<SysRoleFunctionPermission>();
            for(int i = 0; i < roleVo.getRoleFunction().size(); i++) {
                SysRoleFunctionPermission permission = new SysRoleFunctionPermission();
                permission.setCreated(new Date());
                permission.setRolePermissionNote(roleVo.getName());
                permission.setRoleId(sysRole.getId());
                permission.setSysFunctionPermissionId(roleVo.getRoleFunction().get(i));
                permissions.add(permission);
            }
            count = sysRoleFunctionPermissionMapper.insertList(permissions);
            // 添加角色可访问的菜单
            List<SysRoleMenu> sysRoleMenus = new ArrayList<SysRoleMenu>();
            for(int i = 0; i < roleVo.getRoleMenu().size(); i++) {
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setCreated(new Date());
                sysRoleMenu.setRoleMenuNote(roleVo.getName());
                sysRoleMenu.setRoleId(sysRole.getId());
                sysRoleMenu.setMenuId(roleVo.getRoleMenu().get(i));
                sysRoleMenus.add(sysRoleMenu);
            }
            count = sysRoleMenuMapper.insertList(sysRoleMenus);
            if (count > 0) {
                return ResponseFactory.sucMsg("添加成功");
            }
        }
        return ResponseFactory.err("添加失败");
    }

    /**
     * 改变角色状态
     *
     * @param: [id 角色id, status 角色状态, token]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @Override
    public Response changeStatus(Integer id, Integer status, String token) {
        SysRole sysRole = sysRoleMapper.selectByPrimaryKey(id);
        if (sysRole == null) {
            return ResponseFactory.err("无此角色");
        }
        sysRole.setUpdated(new Date());
        sysRole.setActive(status.byteValue());
        // 根据token取出用户信息
        WebUserInfo webUserInfo = mRedisTemplate.get(token, new TypeReference<WebUserInfo>() {
        });
        if (webUserInfo == null) {
            return ResponseFactory.errNeedLogin(Constants.USER_TOKEN_ERROR);
        }
        SysAdmin sysAdmin = webUserInfo.getSysAdmin();
        sysRole.setUpdateAdminId(sysAdmin.getId());
        int count = sysRoleMapper.updateByPrimaryKey(sysRole);
        if (count == 1) {
            return ResponseFactory.sucMsg("设置成功");
        }
        return ResponseFactory.err("设置失败");
    }

    /**
     * 获得角色详情
     *
     * @param: [id 角色id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @Override
    public Response getRoleDetail(Integer id) {
        SysRole sysRole = sysRoleMapper.selectByPrimaryKey(id);
        if (sysRole == null) {
            return ResponseFactory.err("无此角色");
        }
        List<SysRoleFunctionPermission> permissions = sysRoleFunctionPermissionMapper.selectByRoleId(id);
        RoleDetail roleDetail = new RoleDetail();
        roleDetail.setDescription(sysRole.getDescription());
        roleDetail.setName(sysRole.getName());
        roleDetail.setPermissions(permissions);
        roleDetail.setId(sysRole.getId());
        return ResponseFactory.sucData(roleDetail);
    }

    /**
     * 修改角色
     *
     * @param: [roleVo 角色信息]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @Override
    public Response updateRole(RoleVo roleVo) {
        Integer roleId = roleVo.getId();
        SysRole sysRole = sysRoleMapper.selectByPrimaryKey(roleVo.getId());
        if (sysRole != null) {
            // 根据token取出用户信息
            WebUserInfo webUserInfo = mRedisTemplate.get(roleVo.getToken(), new TypeReference<WebUserInfo>() {
            });
            if (webUserInfo == null) {
                return ResponseFactory.errNeedLogin(Constants.USER_TOKEN_ERROR);
            }
            SysAdmin sysAdmin = webUserInfo.getSysAdmin();
            if (roleVo.getIsChangeTree().intValue() == 0) {
                sysRole.setUpdateAdminId(sysAdmin.getId());
                sysRole.setUpdated(new Date());
                sysRole.setName(roleVo.getName());
                sysRole.setDescription(roleVo.getDescription());
                int count = sysRoleMapper.updateByPrimaryKey(sysRole);
                if (count == 1) {
                    return ResponseFactory.sucMsg("修改成功");
                }
            } else {
                sysRole.setUpdateAdminId(sysAdmin.getId());
                sysRole.setUpdated(new Date());
                sysRole.setName(roleVo.getName());
                sysRole.setDescription(roleVo.getDescription());
                sysRole.setPermissionNames(roleVo.getNames());
                int count = sysRoleMapper.updateByPrimaryKey(sysRole);
                // 删除该角色原有菜单
                sysRoleMenuMapper.deleteByRoleId(roleId);
                // 删除该角色原有方法权限
                sysRoleFunctionPermissionMapper.deleteByRoleId(roleId);

                // 添加角色可访问的方法权限
                List<SysRoleFunctionPermission> permissions = new ArrayList<SysRoleFunctionPermission>();
                for(int i = 0; i < roleVo.getRoleFunction().size(); i++) {
                    SysRoleFunctionPermission permission = new SysRoleFunctionPermission();
                    permission.setCreated(new Date());
                    permission.setRolePermissionNote(roleVo.getName());
                    permission.setRoleId(sysRole.getId());
                    permission.setSysFunctionPermissionId(roleVo.getRoleFunction().get(i));
                    permissions.add(permission);
                }
                sysRoleFunctionPermissionMapper.insertList(permissions);
                // 添加角色可访问的菜单
                List<SysRoleMenu> sysRoleMenus = new ArrayList<SysRoleMenu>();
                for(int i = 0; i < roleVo.getRoleMenu().size(); i++) {
                    SysRoleMenu sysRoleMenu = new SysRoleMenu();
                    sysRoleMenu.setCreated(new Date());
                    sysRoleMenu.setRoleMenuNote(roleVo.getName());
                    sysRoleMenu.setRoleId(sysRole.getId());
                    sysRoleMenu.setMenuId(roleVo.getRoleMenu().get(i));
                    sysRoleMenus.add(sysRoleMenu);
                }
                sysRoleMenuMapper.insertList(sysRoleMenus);

                return ResponseFactory.sucMsg("修改成功");
            }

        }
        return ResponseFactory.err("修改失败");
    }

    /**
     * 获得所有的角色
     *
     * @param: []
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @Override
    public Response getAllRole() {
        List<SysRole> sysRoles = sysRoleMapper.selectAllRole();
        return ResponseFactory.sucData(sysRoles);
    }

    /**
     * 获得所有的角色（包括超级管理员）
     *
     * @param: []
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @Override
    public Response getAllRoleList() {
        List<SysRole> sysRoles = sysRoleMapper.selectAllRoleList();
        return ResponseFactory.sucData(sysRoles);
    }

    /**
     * 删除角色
     *
     * @param: [id 角色id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @Override
    public Response delRole(Integer id) {
        SysRole sysRole = sysRoleMapper.selectByPrimaryKey(id);
        if (sysRole == null) {
            return ResponseFactory.err("角色不存在");
        }
        // 先检查该角色下是否存在账号
        List<SysAdminRole> sysAdminRoles = sysAdminRoleMapper.selectByRoleId(id);
        if (sysAdminRoles.size() > 0) {
            return ResponseFactory.err("该角色下已存在后台用户,请先删除所有与之对应的后台用户!");
        }
        // 删除角色
        sysRoleMapper.deleteByPrimaryKey(id);
        // 删除角色权限
        sysRoleFunctionPermissionMapper.deleteByRoleId(id);
        // 删除角色菜单
        sysRoleMenuMapper.deleteByRoleId(id);
        return ResponseFactory.sucMsg("删除成功");
    }

}
