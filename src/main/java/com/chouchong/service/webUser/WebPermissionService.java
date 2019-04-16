package com.chouchong.service.webUser;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.service.webUser.vo.RoleVo;

public interface WebPermissionService {
    /**
     * 获得后台角色列表
     *
     * @param: [page 分页信息, search 查询条件]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    Response getRoleList(PageQuery page, String search);

    /**
     * 获得所有的后台菜单
     *
     * @param: []
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    Response getAllMenus();

    /**
     * 添加后台角色
     *
     * @param: [roleVo 角色信息]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    Response addRole(RoleVo roleVo);

    /**
     * 改变角色状态
     *
     * @param: [id 角色id, status 角色状态, token]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    Response changeStatus(Integer id, Integer status, String token);

    /**
     * 获得角色详情
     *
     * @param: [id 角色id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    Response getRoleDetail(Integer id);

    /**
     * 修改角色
     *
     * @param: [roleVo 角色信息]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    Response updateRole(RoleVo roleVo);

    /**
     * 获得所有的角色
     *
     * @param: []
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    Response getAllRole();

    /**
     * 删除角色
     *
     * @param: [id 角色id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    Response delRole(Integer id);
    /**
     * 获得所有的角色（包括超级管理员）
     *
     * @param: []
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    Response getAllRoleList();
}
