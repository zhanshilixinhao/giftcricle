package com.chouchong.entity.webUser;

public class SysRoleFunctionPermissionKey {
    private Integer roleId;

    private Integer sysFunctionPermissionId;

    public SysRoleFunctionPermissionKey(Integer roleId, Integer sysFunctionPermissionId) {
        this.roleId = roleId;
        this.sysFunctionPermissionId = sysFunctionPermissionId;
    }

    public SysRoleFunctionPermissionKey() {
        super();
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getSysFunctionPermissionId() {
        return sysFunctionPermissionId;
    }

    public void setSysFunctionPermissionId(Integer sysFunctionPermissionId) {
        this.sysFunctionPermissionId = sysFunctionPermissionId;
    }
}