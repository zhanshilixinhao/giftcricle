package com.chouchong.service.amap;

/**
 * @author yichenshanren
 * @date 2017/10/16
 */

public class AMapResponse {


    /**
     * info : OK
     * status : 1
     * success : 1
     * fail : 1
     * _id : 283
     */

    private String info;
    private int status;
    private int success;
    private int fail;
    private int _id;
    private String infocode;
    private String sec_code_debug;
    private String key;
    private String sec_code;


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

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getInfocode() {
        return infocode;
    }

    public void setInfocode(String infocode) {
        this.infocode = infocode;
    }

    public String getSec_code_debug() {
        return sec_code_debug;
    }

    public void setSec_code_debug(String sec_code_debug) {
        this.sec_code_debug = sec_code_debug;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSec_code() {
        return sec_code;
    }

    public void setSec_code(String sec_code) {
        this.sec_code = sec_code;
    }
}
