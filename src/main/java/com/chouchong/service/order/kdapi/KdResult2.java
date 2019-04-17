package com.chouchong.service.order.kdapi;

import java.util.List;

/**
 * @author linqin
 * @date 2019/4/17
 */

public class KdResult2 {


    /**
     * message : ok
     * state : 0
     * status : 200
     * condition : F00
     * ischeck : 0
     * com : yuantong
     * nu : V030344422
     * data : [{"context":"上海分拨中心/装件入车扫描 ","time":"2012-08-28 16:33:19","ftime":"2012-08-28 16:33:19"},{"context":"上海分拨中心/下车扫描 ","time":"2012-08-27 23:22:42","ftime":"2012-08-27 23:22:42"}]
     */

    private String message;
    private String state;
    private String status;
    private String condition;
    private String ischeck;
    private String com;
    private String nu;
    private List<DataBean> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getIscheck() {
        return ischeck;
    }

    public void setIscheck(String ischeck) {
        this.ischeck = ischeck;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public String getNu() {
        return nu;
    }

    public void setNu(String nu) {
        this.nu = nu;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * context : 上海分拨中心/装件入车扫描
         * time : 2012-08-28 16:33:19
         * ftime : 2012-08-28 16:33:19
         */

        private String context;
        private String time;
        private String ftime;

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getFtime() {
            return ftime;
        }

        public void setFtime(String ftime) {
            this.ftime = ftime;
        }
    }
}
