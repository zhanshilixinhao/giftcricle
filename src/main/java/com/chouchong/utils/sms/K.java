package com.chouchong.utils.sms;

/**
 * 生成某个东西的key
 *
 * @author yichenshanren
 * @date 2017/11/28
 */

public class K {

    public static final String PREFIX = "gic-"; // 优咖
    public static final String SPLIT = "-"; // 分隔符


    public static String genKey(String name, Object id) {
        return String.format("%s%s%s%s", PREFIX, name, SPLIT, String.valueOf(id));
    }

}
