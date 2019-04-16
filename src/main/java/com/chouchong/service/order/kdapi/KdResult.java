package com.chouchong.service.order.kdapi;

import java.util.List;

/**
 * @author linqin
 * @date 2018/6/29
 */
public class KdResult {


    /**
     * message : ok
     * nu : 078174215686
     * ischeck : 0
     * condition : H100
     * com : shunfeng
     * status : 200
     * state : 5
     * data : [{"time":"2018-06-25 11:45:00","ftime":"2018-06-25 11:45:00",
     * "context":"[昆明市]已与收方客户约定新派送时间 201806301010,待派送","location":"昆明市"},{"time":"2018-06-25 10:10:00","ftime":"2018-06-25 10:10:00","context":"[昆明市]快件派送不成功(已与收方客户约定新派送时间 ),待再次派送","location":"昆明市"},{"time":"2018-06-25 08:50:00","ftime":"2018-06-25 08:50:00","context":"[昆明市]快件交给李志才，正在派送途中（联系电话：13888709412）","location":"昆明市"},{"time":"2018-06-25 08:08:00","ftime":"2018-06-25 08:08:00","context":"[昆明市]正在派送途中,请您准备签收(派件人:设定用户,电话:12345678911)","location":"昆明市"},{"time":"2018-06-25 07:58:00","ftime":"2018-06-25 07:58:00","context":"[昆明市]快件到达 【昆明正大紫都营业点】","location":"昆明市"},{"time":"2018-06-25 06:52:00","ftime":"2018-06-25 06:52:00","context":"[昆明市]快件已发车","location":"昆明市"},{"time":"2018-06-25 05:07:00","ftime":"2018-06-25 05:07:00","context":"[昆明市]快件在【昆明王家营集散中心】已装车,准备发往 【昆明正大紫都营业点】","location":"昆明市"},{"time":"2018-06-25 03:21:00","ftime":"2018-06-25 03:21:00","context":"[昆明市]快件到达 【昆明王家营集散中心】","location":"昆明市"},{"time":"2018-06-24 03:51:00","ftime":"2018-06-24 03:51:00","context":"[东莞市]快件已发车","location":"东莞市"},{"time":"2018-06-24 02:10:00","ftime":"2018-06-24 02:10:00","context":"[东莞市]快件在【东莞沙田集散中心】已装车,准备发往 【昆明王家营集散中心】","location":"东莞市"},{"time":"2018-06-24 01:13:00","ftime":"2018-06-24 01:13:00","context":"[东莞市]快件到达 【东莞沙田集散中心】","location":"东莞市"},{"time":"2018-06-23 23:48:00","ftime":"2018-06-23 23:48:00","context":"[中山市]快件已发车","location":"中山市"},{"time":"2018-06-23 23:24:00","ftime":"2018-06-23 23:24:00","context":"[中山市]快件在【中山东升集散中心】已装车,准备发往 【东莞沙田集散中心】","location":"中山市"},{"time":"2018-06-23 22:36:00","ftime":"2018-06-23 22:36:00","context":"[中山市]快件到达 【中山东升集散中心】","location":"中山市"},{"time":"2018-06-23 21:24:00","ftime":"2018-06-23 21:24:00","context":"[中山市]快件已发车","location":"中山市"},{"time":"2018-06-23 20:36:00","ftime":"2018-06-23 20:36:00","context":"[中山市]快件在【中山石岐汇智大厦营业部】已装车,准备发往下一站","location":"中山市"},{"time":"2018-06-23 12:32:00","ftime":"2018-06-23 12:32:00","context":"[中山市]顺丰速运 已收取快件","location":"中山市"},{"time":"2018-06-23 12:22:00","ftime":"2018-06-23 12:22:00","context":"[中山市]顺丰速运 已收取快件","location":"中山市"}]
     */

    private String message;
    private String nu;
    private String ischeck;
    private String condition;
    private String com;
    private int status;
    private String state;
    private List<DataBean> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNu() {
        return nu;
    }

    public void setNu(String nu) {
        this.nu = nu;
    }

    public String getIscheck() {
        return ischeck;
    }

    public void setIscheck(String ischeck) {
        this.ischeck = ischeck;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * time : 2018-06-25 11:45:00
         * ftime : 2018-06-25 11:45:00
         * context : [昆明市]已与收方客户约定新派送时间 201806301010,待派送
         * location : 昆明市
         */

        private String time;
        private String ftime;
        private String context;
        private String location;

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

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }
}
