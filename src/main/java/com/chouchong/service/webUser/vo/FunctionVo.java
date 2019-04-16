package com.chouchong.service.webUser.vo;

/**
 * @author yy
 * @date 2018/7/13
 **/
public class FunctionVo {
    private Integer id;

    private String name;

    // flag = 3 为 三级菜单
    private Integer flag = 3;

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
}
