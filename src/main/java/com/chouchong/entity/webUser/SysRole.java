package com.chouchong.entity.webUser;

import java.util.Date;

public class SysRole {
    private Integer id;

    private String name;

    private String description;

    private String permissionNames;

    private Byte active;

    private Date created;

    private Date updated;

    private Integer createAdminId;

    private Integer updateAdminId;

    public SysRole(Integer id, String name, String description, String permissionNames, Byte active, Date created, Date updated, Integer createAdminId, Integer updateAdminId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.permissionNames = permissionNames;
        this.active = active;
        this.created = created;
        this.updated = updated;
        this.createAdminId = createAdminId;
        this.updateAdminId = updateAdminId;
    }

    public SysRole() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getPermissionNames() {
        return permissionNames;
    }

    public void setPermissionNames(String permissionNames) {
        this.permissionNames = permissionNames == null ? null : permissionNames.trim();
    }

    public Byte getActive() {
        return active;
    }

    public void setActive(Byte active) {
        this.active = active;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Integer getCreateAdminId() {
        return createAdminId;
    }

    public void setCreateAdminId(Integer createAdminId) {
        this.createAdminId = createAdminId;
    }

    public Integer getUpdateAdminId() {
        return updateAdminId;
    }

    public void setUpdateAdminId(Integer updateAdminId) {
        this.updateAdminId = updateAdminId;
    }
}