package com.chouchong.service.webUser.vo;

import java.util.List;

/**
 * 用于接收前端传递的角色的菜单和角色的权限 添加角色和修改角色时使用
 *
 * @author yy
 * @date 2018/7/14
 **/
public class RoleVo {
    // 角色id, 修改角色时使用
    private Integer id;

    private String name;

    // 权限集合名称
    private String names;

    private String description;

    // 菜单id集合
    private List<Integer> roleMenu;

    // 权限id集合
    private List<Integer> roleFunction;

    private String token;

    // 是否修改了树， 修改角色的时候会用到这个参数
    private Integer isChangeTree;

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIsChangeTree() {
        return isChangeTree;
    }

    public void setIsChangeTree(Integer isChangeTree) {
        this.isChangeTree = isChangeTree;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public List<Integer> getRoleMenu() {
        return roleMenu;
    }

    public void setRoleMenu(List<Integer> roleMenu) {
        this.roleMenu = roleMenu;
    }

    public List<Integer> getRoleFunction() {
        return roleFunction;
    }

    public void setRoleFunction(List<Integer> roleFunction) {
        this.roleFunction = roleFunction;
    }
}
