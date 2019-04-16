package com.chouchong.service.amap;

import java.util.List;

/**
 * @author yichenshanren
 * @date 2017/10/17
 */

public class AroundResponse {

    private String count;
    private String info;
    private String infocode;
    private String agent_id;
    private int status;
    private List<DatasBean> datas;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public String getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(String agent_id) {
        this.agent_id = agent_id;
    }

    public static class DatasBean {

        private String _id;
        private String _location;
        private String _name;
        private String cover;
        private String _address;
        private String agent_id;
        private String _distance;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String get_location() {
            return _location;
        }

        public void set_location(String _location) {
            this._location = _location;
        }

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

        public String getAgent_id() {
            return agent_id;
        }

        public void setAgent_id(String agent_id) {
            this.agent_id = agent_id;
        }

        public String get_distance() {
            return _distance;
        }

        public void set_distance(String _distance) {
            this._distance = _distance;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }
    }
}
