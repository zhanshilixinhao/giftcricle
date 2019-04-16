package com.chouchong.entity.webUser;

import java.util.Date;

public class SysMenu {
    private Integer id;

    private Integer parentId;

    private String name;

    private String description;

    private String url;

    private Byte active;

    private Date created;

    private Integer createAdminId;

    private Date updated;

    private Integer updateAdminId;

    public SysMenu(Integer id, Integer parentId, String name, String description, String url, Byte active, Date created, Integer createAdminId, Date updated, Integer updateAdminId) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.description = description;
        this.url = url;
        this.active = active;
        this.created = created;
        this.createAdminId = createAdminId;
        this.updated = updated;
        this.updateAdminId = updateAdminId;
    }

    public SysMenu() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
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

    public Integer getCreateAdminId() {
        return createAdminId;
    }

    public void setCreateAdminId(Integer createAdminId) {
        this.createAdminId = createAdminId;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Integer getUpdateAdminId() {
        return updateAdminId;
    }

    public void setUpdateAdminId(Integer updateAdminId) {
        this.updateAdminId = updateAdminId;
    }
}