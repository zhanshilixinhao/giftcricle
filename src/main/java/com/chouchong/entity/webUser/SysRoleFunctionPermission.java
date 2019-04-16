package com.chouchong.entity.webUser;

import java.util.Date;

public class SysRoleFunctionPermission extends SysRoleFunctionPermissionKey {
    private String rolePermissionNote;

    private Date created;

    public SysRoleFunctionPermission(Integer roleId, Integer sysFunctionPermissionId, String rolePermissionNote, Date created) {
        super(roleId, sysFunctionPermissionId);
        this.rolePermissionNote = rolePermissionNote;
        this.created = created;
    }

    public SysRoleFunctionPermission() {
        super();
    }

    public String getRolePermissionNote() {
        return rolePermissionNote;
    }

    public void setRolePermissionNote(String rolePermissionNote) {
        this.rolePermissionNote = rolePermissionNote == null ? null : rolePermissionNote.trim();
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}