package com.chouchong.entity.webUser;

public class SysRoleMenuKey {
    private Integer roleId;

    private Integer menuId;

    public SysRoleMenuKey(Integer roleId, Integer menuId) {
        this.roleId = roleId;
        this.menuId = menuId;
    }

    public SysRoleMenuKey() {
        super();
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }
}