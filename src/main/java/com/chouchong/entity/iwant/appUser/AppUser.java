package com.chouchong.entity.iwant.appUser;


import lombok.ToString;

import javax.persistence.Id;
import java.util.Date;


/**
 * @Description:
 * @Author: LxH
 * @Date: 2020/9/24 15:54
 */
@ToString
public class AppUser {
    @Id
    private Integer id;

    private String account;

    private String password;

    private String phone;

    private String avatar;

    private String nickname;

    private Integer age;

    private Byte gender;

    private String signature;

    private String district;

    private Byte status;

    private String sentPwd;

    private String wxid;

    private Date created;

    private Date updated;

    //private Byte isHide;

    public AppUser(Integer id, String account, String password, String phone, String avatar, String nickname, Integer age, Byte gender, String signature, String district, Byte status, String sentPwd, String wxid, Date created, Date updated) {
        this.id = id;
        this.account = account;
        this.password = password;
        this.phone = phone;
        this.avatar = avatar;
        this.nickname = nickname;
        this.age = age;
        this.gender = gender;
        this.signature = signature;
        this.district = district;
        this.status = status;
        this.sentPwd = sentPwd;
        this.wxid = wxid;
        this.created = created;
        this.updated = updated;
    }

    public AppUser() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature == null ? null : signature.trim();
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district == null ? null : district.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getSentPwd() {
        return sentPwd;
    }

    public void setSentPwd(String sentPwd) {
        this.sentPwd = sentPwd == null ? null : sentPwd.trim();
    }

    public String getWxid() {
        return wxid;
    }

    public void setWxid(String wxid) {
        this.wxid = wxid == null ? null : wxid.trim();
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}