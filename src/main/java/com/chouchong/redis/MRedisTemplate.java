package com.chouchong.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author yichenshanren
 * @date 2017/12/4
 */

@Component("mRedisTemplate")
public class MRedisTemplate {

    private final StringRedisTemplate stringRedisTemplate;

    @Autowired
    public MRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
//        stringRedisTemplate.setEnableTransactionSupport(true);
    }

    /**
     * 判断是否存在一个key
     *
     * @param key key
     * @return 是否错在
     */
    public Boolean isExist(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    /**
     * 获取过期时间
     *
     * @param key  key
     * @param unit 时间单位
     * @return 过期时间
     */
    public Long getExpire(String key, TimeUnit unit) {
        return stringRedisTemplate.getExpire(key, unit);
    }

    /**
     * 取出redis中的缓存
     *
     * @param key      缓存的key
     * @param expire   缓存的过期时间 秒
     * @param clzz     缓存实体class
     * @param callback 缓存加载回调
     * @param <T>      返回的反序列化对象的泛型
     * @return 返回反序列化对象
     */
    public <T> T get(String key, int expire, TimeUnit timeUnit, TypeReference<T> clzz, CacheCallback<T> callback) {
        /* 取出缓存 */
        String json = stringRedisTemplate.opsForValue().get(key);
        /* 如果缓存不存在 */
        if (StringUtils.isBlank(json) || "null".equalsIgnoreCase(json)) {
            synchronized (this) {
                /* 再次取出缓存 */
                json = stringRedisTemplate.opsForValue().get(key);
                /* 如果缓存还是不存在 */
                if (StringUtils.isBlank(json) || "null".equalsIgnoreCase(json)) {
                    /* 查询数据库 */
                    T t = callback.load();
                    /* 设置缓存 */
                    if (t != null) {
                        stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(t), expire, timeUnit);
                    }
                    return t;
                }
            }
        }
        return parseObject(key, clzz, json);
    }

    public <T> T get(String key, TypeReference<T> clzz) {
        String json = stringRedisTemplate.opsForValue().get(key);
        if (StringUtils.isBlank(json) || "null".equalsIgnoreCase(json)) {
            return null;
        }
        return parseObject(key, clzz, json);
    }

    private <T> T parseObject(String key, TypeReference<T> clzz, String json) {
//        try {
        return JSON.parseObject(json, clzz);
//        } catch (Throwable e) {
//            e.printStackTrace();
//            stringRedisTemplate.delete(key);
//        }
//        return null;
    }

    public String get(String key, int expire, TimeUnit timeUnit, CacheCallback<String> callback) {
        /* 取出缓存 */
        String json = stringRedisTemplate.opsForValue().get(key);
        /* 如果缓存不存在 */
        if (StringUtils.isBlank(json) || "null".equalsIgnoreCase(json)) {
            synchronized (this) {
                /* 再次取出缓存 */
                json = stringRedisTemplate.opsForValue().get(key);
                /* 如果缓存还是不存在 */
                if (StringUtils.isBlank(json) || "null".equalsIgnoreCase(json)) {
                    /* 查询数据库 */
                    String t = callback.load();
                    /* 设置缓存 */
                    if (StringUtils.isNotBlank(t)) {
                        stringRedisTemplate.opsForValue().set(key, t, expire, timeUnit);
                    }
                    return t;
                }
            }
        }
        return json;
    }

    public <T> void set(String key, T data, Date expire) {
        stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(data), expire.getTime(), TimeUnit.MILLISECONDS);
    }

    public <T> void set(String key, T data, long expire) {
        stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(data), expire, TimeUnit.SECONDS);
    }

    public <T> void set(String key, T data) {
        stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(data));
    }


    public void setString(String key, String data, long expire) {
        stringRedisTemplate.opsForValue().set(key, data, expire, TimeUnit.SECONDS);
    }

    public void setString(String key, String data) {
        stringRedisTemplate.opsForValue().set(key, data);
    }


    public String getString(String key) {
        String json = stringRedisTemplate.opsForValue().get(key);
        if (StringUtils.isBlank(json) || "null".equalsIgnoreCase(json)) {
            return null;
        }
        return json;
    }

    public void del(String key) {
        stringRedisTemplate.delete(key);
    }

    public StringRedisTemplate template() {
        return stringRedisTemplate;
    }
}
