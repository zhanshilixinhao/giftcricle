package com.chouchong.redis;

/**
 * redis缓存读取回调
 *
 * @author yichenshanren
 * @date 2017/10/9
 */

public interface CacheCallback<T> {

    T load();

}
