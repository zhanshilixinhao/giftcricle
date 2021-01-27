package com.chouchong.service.v4.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description:
 * @Author Lxh
 * @Date 2020/12/3 16:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cascader {
    private Integer value;
    private String label;
    private List<CascaderVo> children;
}
