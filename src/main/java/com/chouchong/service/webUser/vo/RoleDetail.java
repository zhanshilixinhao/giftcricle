package com.chouchong.service.webUser.vo;

import com.chouchong.entity.webUser.SysRoleFunctionPermission;

import java.util.List;

/**
 * 用于获得角色的详情以及角色的权限
 *
 * @author yy
 * @date 2018/7/14
 **/
public class RoleDetail {
    private Integer id;

    private String name;

    private String description;

    private List<SysRoleFunctionPermission> permissions;

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
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SysRoleFunctionPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<SysRoleFunctionPermission> permissions) {
        this.permissions = permissions;
    }
}
