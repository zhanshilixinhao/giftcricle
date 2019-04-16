package com.chouchong.entity.webUser;

import java.util.Date;

public class SysAdminRole extends SysAdminRoleKey {
    private String adminRoleNote;

    private Date created;

    public SysAdminRole(Integer adminId, Integer roleId, String adminRoleNote, Date created) {
        super(adminId, roleId);
        this.adminRoleNote = adminRoleNote;
        this.created = created;
    }

    public SysAdminRole() {
        super();
    }

    public String getAdminRoleNote() {
        return adminRoleNote;
    }

    public void setAdminRoleNote(String adminRoleNote) {
        this.adminRoleNote = adminRoleNote == null ? null : adminRoleNote.trim();
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}