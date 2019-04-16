package com.chouchong.entity.iwant.merchant;

import java.util.Date;

public class Merchant {
    private Integer id;

    private String name;

    private Integer userId;

    private Integer adminId;

    private String address;

    private String registrationNo;

    private String legalPerson;

    private String phone;

    private String licensePic;

    private String otherPics;

    private Byte status;

    private Date created;

    private Date updated;

    public Merchant(Integer id, String name, Integer userId, Integer adminId, String address, String registrationNo, String legalPerson, String phone, String licensePic, String otherPics, Byte status, Date created, Date updated) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.adminId = adminId;
        this.address = address;
        this.registrationNo = registrationNo;
        this.legalPerson = legalPerson;
        this.phone = phone;
        this.licensePic = licensePic;
        this.otherPics = otherPics;
        this.status = status;
        this.created = created;
        this.updated = updated;
    }

    public Merchant() {
        super();
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
        this.name = name == null ? null : name.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo == null ? null : registrationNo.trim();
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson == null ? null : legalPerson.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getLicensePic() {
        return licensePic;
    }

    public void setLicensePic(String licensePic) {
        this.licensePic = licensePic == null ? null : licensePic.trim();
    }

    public String getOtherPics() {
        return otherPics;
    }

    public void setOtherPics(String otherPics) {
        this.otherPics = otherPics == null ? null : otherPics.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
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