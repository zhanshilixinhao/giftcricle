package com.chouchong.utils.sms.impl;

import com.alibaba.fastjson.JSON;
import com.chouchong.utils.sms.K;
import com.chouchong.utils.sms.VerifyCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author yichenshanren
 * @date 2017/11/28
 */
@Component("verifyCodeRepository")
public class VerifyCodeRepositoryImpl implements VerifyCodeRepository {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * @param code 验证码
     * @param phone  保存的key
     */
    @Override
    public void save(String code, String phone) {
        stringRedisTemplate.opsForValue().set(K.genKey(phone,5),
               code, 300, TimeUnit.SECONDS);
    }

    /**
     * @param key  保存的key
     * @param type 验证码类型
     * @return
     */
    @Override
    public String get(String key, int type) {
        String code = stringRedisTemplate.opsForValue().get(K.genKey(key, type));
        return code;
    }

    /**
     * @param key  保存的key
     * @param type 验证码类型
     */
    @Override
    public void remove(String key, int type) {
        stringRedisTemplate.delete(K.genKey(key, type));
    }
}
