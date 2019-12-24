package com.chouchong.utils.sms;

/**
 * 短信验证码
 *
 * @author yichenshanren
 * @date 2017/11/25
 */

public class VerifyCode {

    private int type;
    // 短信验证码
    private String code;
    // 短信验证码过期时间
    private long expire;

    public VerifyCode() {
    }

    public VerifyCode(int type, String code, long expire) {
        this.code = code;
        this.type = type;
        this.expire = expire;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
