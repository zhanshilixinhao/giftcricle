package com.chouchong.common;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author yichenshanren
 * @date 2017/9/28
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"successful"})
public class ResponseImpl<T> implements Response<T> {

    private int errCode;
    private int result;
    private String msg;
    private String path;
    private long time;
    private T data;

    public ResponseImpl() {
    }

    ResponseImpl(int errCode) {
        this.errCode = errCode;
        time = System.currentTimeMillis();
    }

    ResponseImpl(int errCode, String msg) {
        this.errCode = errCode;
        this.msg = msg;
        time = System.currentTimeMillis();
    }

    ResponseImpl(int errCode, T data) {
        this.errCode = errCode;
        this.data = data;
        time = System.currentTimeMillis();
    }

    ResponseImpl(int errCode, String msg, T data) {
        this.errCode = errCode;
        this.msg = msg;
        this.data = data;
        time = System.currentTimeMillis();
    }

    ResponseImpl(int errCode, String msg, T data, String imgHost) {
        this.errCode = errCode;
        this.msg = msg;
        this.data = data;
        time = System.currentTimeMillis();
    }

    ResponseImpl(int errCode, String msg, String path) {
        this.errCode = errCode;
        this.msg = msg;
        this.path = path;
        time = System.currentTimeMillis();
    }

    ResponseImpl(int errCode, T data, String imgHost) {
        this.errCode = errCode;
        this.data = data;
        time = System.currentTimeMillis();
    }

    public int getErrCode() {
        return errCode;
    }

    @Override
    public int getResult() {
        return result;
    }

    public ResponseImpl result(int result) {
        this.result = result;
        return this;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @JSONField(serialize = false)
    public boolean isSuccessful() {
        return errCode == ErrorCode.SUCCESS.getCode();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
