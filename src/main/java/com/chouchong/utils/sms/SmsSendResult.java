package com.chouchong.utils.sms;

/**
 * @author yichenshanren
 * @date 2017/12/10
 */

public class SmsSendResult {

    private int code;

    private String msg;

    public SmsSendResult(int code) {
        this.code = code;
        msg = "醋味";
    }

    public SmsSendResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
