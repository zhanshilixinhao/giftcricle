package com.chouchong.entity.webUser;

import java.util.Date;

public class SysAdmin {
    private Integer id;

    private String username;

    private String password;

    private Byte active;

    private String avatar;

    private String realName;

    private String phone;

    private String idNumber;

    private Byte gender;

    private String email;

    private String qq;

    private String wechat;

    private Date created;

    private Date updated;

    private Integer createAdminId;

    private Integer updateAdminId;

    private String createIp;

    private Integer loginCount;

    private Date lastLoginTime;

    private String lastLoginIp;

    public SysAdmin(Integer id, String username, String password, Byte active, String avatar, String realName, String phone, String idNumber, Byte gender, String email, String qq, String wechat, Date created, Date updated, Integer createAdminId, Integer updateAdminId, String createIp, Integer loginCount, Date lastLoginTime, String lastLoginIp) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.active = active;
        this.avatar = avatar;
        this.realName = realName;
        this.phone = phone;
        this.idNumber = idNumber;
        this.gender = gender;
        this.email = email;
        this.qq = qq;
        this.wechat = wechat;
        this.created = created;
        this.updated = updated;
        this.createAdminId = createAdminId;
        this.updateAdminId = updateAdminId;
        this.createIp = createIp;
        this.loginCount = loginCount;
        this.lastLoginTime = lastLoginTime;
        this.lastLoginIp = lastLoginIp;
    }

    public SysAdmin() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Byte getActive() {
        return active;
    }

    public void setActive(Byte active) {
        this.active = active;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber == null ? null : idNumber.trim();
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq == null ? null : qq.trim();
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat == null ? null : wechat.trim();
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

    public Integer getCreateAdminId() {
        return createAdminId;
    }

    public void setCreateAdminId(Integer createAdminId) {
        this.createAdminId = createAdminId;
    }

    public Integer getUpdateAdminId() {
        return updateAdminId;
    }

    public void setUpdateAdminId(Integer updateAdminId) {
        this.updateAdminId = updateAdminId;
    }

    public String getCreateIp() {
        return createIp;
    }

    public void setCreateIp(String createIp) {
        this.createIp = createIp == null ? null : createIp.trim();
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp == null ? null : lastLoginIp.trim();
    }
}