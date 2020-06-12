package com.chouchong.service.iwant.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author linqin
 * @date 2020/6/12下午1:15
 **/
@Getter
@Setter
public class MerchantApplyVo {
    // 企业名称
    private String name;
    // 企业地址
    private String address;
    // 企业注册号
    private String registrationNo;
    // 法人代表
    private String legalPerson;
    // 联系电话
    private String phone;
    // 营业执照照片
    private String licensePic;
    // 其他证件图片
    private List<String> otherPics;
}
