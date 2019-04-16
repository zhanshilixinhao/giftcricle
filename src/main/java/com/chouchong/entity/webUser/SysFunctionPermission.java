package com.chouchong.entity.webUser;

import java.util.Date;

public class SysFunctionPermission {
    private Integer id;

    private String name;

    private String desciption;

    private String url;

    private Integer menuId;

    private Byte active;

    private Date created;

    private Integer createAdminId;

    private Date updated;

    private Integer updateAdminId;

    public SysFunctionPermission(Integer id, String name, String desciption, String url, Integer menuId, Byte active, Date created, Integer createAdminId, Date updated, Integer updateAdminId) {
        this.id = id;
        this.name = name;
        this.desciption = desciption;
        this.url = url;
        this.menuId = menuId;
        this.active = active;
        this.created = created;
        this.createAdminId = createAdminId;
        this.updated = updated;
        this.updateAdminId = updateAdminId;
    }

    public SysFunctionPermission() {
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

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption == null ? null : desciption.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
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