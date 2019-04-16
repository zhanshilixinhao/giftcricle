package com.chouchong.service.webUser.vo;

import com.chouchong.entity.webUser.SysAdmin;
import com.chouchong.entity.webUser.SysFunctionPermission;

import java.util.List;

/**
 * @author yy
 * @date 2018/7/16
 **/
public class WebUserInfo {
    private SysAdmin sysAdmin;

    private List<String> menus;

    private List<String> permissions;

    private List<SysFunctionPermission> permissionList;

    private Integer roleId; //角色id

    public List<SysFunctionPermission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<SysFunctionPermission> permissionList) {
        this.permissionList = permissionList;
    }

    public SysAdmin getSysAdmin() {
        return sysAdmin;
    }

    public void setSysAdmin(SysAdmin sysAdmin) {
        this.sysAdmin = sysAdmin;
    }

    public List<String> getMenus() {
        return menus;
    }

    public void setMenus(List<String> menus) {
        this.menus = menus;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
