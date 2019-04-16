package com.chouchong.service.amap;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author yichenshanren
 * @date 2017/10/17
 */

public class AMapVo {

    @JSONField(name = "_name")
    private String _name;
    @JSONField(name = "_address")
    private String _address;
    @JSONField(name = "_location")
    private String _location;
    @JSONField(name = "agent_id")
    private Integer agentId;
    @JSONField(name = "_id")
    private Integer _id;
    private Integer adcode;
    private String cover;
    private Integer views;
    private Double star;
    private Byte active;

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_address() {
        return _address;
    }

    public void set_address(String _address) {
        this._address = _address;
    }

    public Integer getAdcode() {
        return adcode;
    }

    public void setAdcode(Integer adcode) {
        this.adcode = adcode;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Double getStar() {
        return star;
    }

    public void setStar(Double star) {
        this.star = star;
    }

    public Byte getActive() {
        return active;
    }

    public void setActive(Byte active) {
        this.active = active;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public String get_location() {
        return _location;
    }

    public void set_location(String _location) {
        this._location = _location;
    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }
}
