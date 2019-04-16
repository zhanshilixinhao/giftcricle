package com.chouchong.entity.webUser;

public class SysAdminRoleKey {
    private Integer adminId;

    private Integer roleId;

    public SysAdminRoleKey(Integer adminId, Integer roleId) {
        this.adminId = adminId;
        this.roleId = roleId;
    }

    public SysAdminRoleKey() {
        super();
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}