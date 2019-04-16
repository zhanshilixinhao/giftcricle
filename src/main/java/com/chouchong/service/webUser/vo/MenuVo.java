package com.chouchong.service.webUser.vo;

import java.util.List;

/**
 * @author yy
 * @date 2018/7/13
 **/
public class MenuVo {
    private Integer id;

    private String name;
    // flag = 1 为 一级菜单
    private Integer flag = 1;

    private List<MenuSon> menuSon;

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
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
        this.name = name;
    }

    public List<MenuSon> getMenuSon() {
        return menuSon;
    }

    public void setMenuSon(List<MenuSon> menuSon) {
        this.menuSon = menuSon;
    }
}
