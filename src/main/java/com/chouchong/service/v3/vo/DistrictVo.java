package com.chouchong.service.v3.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

/**
 * @author linqin
 * @date 2018/12/25 20:55
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DistrictVo {

    public Integer id;
    public String name;
    public List<DistrictVo> children;

    public DistrictVo() {
        children = new ArrayList<>();
    }
}
