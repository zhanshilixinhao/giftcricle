package com.chouchong.entity.webUser;

import java.util.Date;

public class SysRoleMenu extends SysRoleMenuKey {
    private String roleMenuNote;

    private Date created;

    public SysRoleMenu(Integer roleId, Integer menuId, String roleMenuNote, Date created) {
        super(roleId, menuId);
        this.roleMenuNote = roleMenuNote;
        this.created = created;
    }

    public SysRoleMenu() {
        super();
    }

    public String getRoleMenuNote() {
        return roleMenuNote;
    }

    public void setRoleMenuNote(String roleMenuNote) {
        this.roleMenuNote = roleMenuNote == null ? null : roleMenuNote.trim();
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}