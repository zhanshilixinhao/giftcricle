package com.chouchong.service.amap;

/**
 * @author yichenshanren
 * @date 2017/11/3
 */

public class BaseResponse {

    private String info;
    private int status;
    private int success;
    private int fail;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getFail() {
        return fail;
    }

    public void setFail(int fail) {
        this.fail = fail;
    }
}
