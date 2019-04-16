package com.chouchong.service.webUser.vo;

import java.util.List;

/**
 * @author yy
 * @date 2018/7/13
 **/
public class MenuSon {
    private Integer id;

    private String name;

    // flag = 2 为 二级菜单
    private Integer flag = 2;

    private List<FunctionVo> functionVo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FunctionVo> getFunctionVo() {
        return functionVo;
    }

    public void setFunctionVo(List<FunctionVo> functionVo) {
        this.functionVo = functionVo;
    }
}
