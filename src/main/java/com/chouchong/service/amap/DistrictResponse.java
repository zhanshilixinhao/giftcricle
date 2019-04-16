package com.chouchong.service.amap;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

/**
 * @author yichenshanren
 * @date 2017/10/17
 */

@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class DistrictResponse {

    private int status;
    private String info;
    private String infocode;
    private String count;
    private List<District> districts;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfocode() {
        return infocode;
    }

    public void setInfocode(String infocode) {
        this.infocode = infocode;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<District> getDistricts() {
        return districts;
    }

    public void setDistricts(List<District> districts) {
        this.districts = districts;
    }

    @JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
    public static class District {

        private Integer adcode;
        private String name;
        private String level;
        private List<District> districts;

        public Integer getAdcode() {
            return adcode;
        }

        public void setAdcode(Integer adcode) {
            this.adcode = adcode;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<District> getDistricts() {
            return districts;
        }

        public void setDistricts(List<District> districts) {
            this.districts = districts;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }
    }
}
