package com.chouchong.controller.webUser;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.service.webUser.WebPermissionService;
import com.chouchong.service.webUser.vo.RoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yy
 * @date 2018/7/13
 **/

@RestController
@RequestMapping("manage/permission")
public class WebPermissionController {
    @Autowired
    private WebPermissionService webPermissionService;

    /**
     * 获得后台角色列表
     *
     * @param: [page 分页信息, search 查询条件]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @PostMapping("roles")
    private Response getRoleList(PageQuery page, String search) {
        return webPermissionService.getRoleList(page, search);
    }

    /**
     * 获得所有的后台菜单
     *
     * @param: []
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @PostMapping("menus")
    private Response getAllMenus() {
        return webPermissionService.getAllMenus();
    }

    /**
     * 添加后台角色
     *
     * @param: [roleVo 角色信息]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @PostMapping("addRole")
    private Response addRole(RoleVo roleVo) {
        if (roleVo == null) {
            return ResponseFactory.errMissingParameter();
        }
        return webPermissionService.addRole(roleVo);
    }

    /**
     * 改变角色状态
     *
     * @param: [id 角色id, status 角色状态, token]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @PostMapping("status")
    private Response changeStatus(Integer id, Integer status, String token) {
        return webPermissionService.changeStatus(id, status, token);
    }

    /**
     * 获得角色详情
     *
     * @param: [id 角色id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @PostMapping("detail")
    private Response getRoleDetail(Integer id) {
        return webPermissionService.getRoleDetail(id);
    }

    /**
     * 修改角色
     *
     * @param: [roleVo 角色信息]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @PostMapping("update")
    private Response updateRole(RoleVo roleVo) {
        return webPermissionService.updateRole(roleVo);
    }

    /**
     * 删除角色
     *
     * @param: [id 角色id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @PostMapping("del")
    private Response delRole(Integer id) {
        if (id == null) {
            return ResponseFactory.errMissingParameter();
        }
        return webPermissionService.delRole(id);
    }

    /**
     * 获得角色(除去超级管理员)
     *
     * @param: []
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @PostMapping("allRole")
    private Response getAllRole() {
        return webPermissionService.getAllRole();
    }

    /**
     * 获得所有的角色（包括超级管理员）
     *
     * @param: []
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @PostMapping("role_list")
    private Response getAllRoleList() {
        return webPermissionService.getAllRoleList();
    }
}
