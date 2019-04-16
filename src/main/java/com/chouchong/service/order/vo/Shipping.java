package com.chouchong.service.order.vo;

import java.util.Date;

public class Shipping {
    private Integer id;

    private Integer userId;

    private String consigneeName;

    private String address;

    private String phone;

    private String addressDetail;

    private Integer adcode;

    private Byte status;

    private Date created;

    private Date updated;

    public Shipping(Integer id, Integer userId, String consigneeName, String address, String phone, String addressDetail, Integer adcode, Byte status, Date created, Date updated) {
        this.id = id;
        this.userId = userId;
        this.consigneeName = consigneeName;
        this.address = address;
        this.phone = phone;
        this.addressDetail = addressDetail;
        this.adcode = adcode;
        this.status = status;
        this.created = created;
        this.updated = updated;
    }

    public Shipping() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName == null ? null : consigneeName.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail == null ? null : addressDetail.trim();
    }

    public Integer getAdcode() {
        return adcode;
    }

    public void setAdcode(Integer adcode) {
        this.adcode = adcode;
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