package com.chouchong.utils.sms;

/**
 * 定义验证码的存储
 *
 * @author yichenshanren
 * @date 2017/11/28
 */

public interface VerifyCodeRepository {

    /**
     * 保存验证码
     *
     * @param code 验证码
     * @param key  保存的key
     * @author yichenshanren
     * @date 2017/11/28
     */
    void save(String code, String key);

    /**
     * 获取验证码
     *
     * @param key  保存的key
     * @param type 验证码类型
     * @return 验证码
     * @author yichenshanren
     * @date 2017/11/28
     */
    VerifyCode get(String key, int type);

    /**
     * 移除验证码
     *
     * @param key  保存的key
     * @param type 验证码类型
     * @author yichenshanren
     * @date 2017/11/28
     */
    void remove(String key, int type);
}
