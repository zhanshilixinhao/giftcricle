package com.chouchong.service.order.vo;

/**
 * @author linqin
 * @date 2018/6/29
 */
public class LogisticsInfoVo {

    private String expressCompany; //快递公司

    private String com;//公司代码

    private String expressNo; //快递单号

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public String getExpressCompany() {
        return expressCompany;
    }

    public void setExpressCompany(String expressCompany) {
        this.expressCompany = expressCompany;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }
}
