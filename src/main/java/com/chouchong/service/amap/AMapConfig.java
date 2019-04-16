package com.chouchong.service.amap;

/**
 * @author yichenshanren
 * @date 2017/10/16
 */

public class AMapConfig {

    public static final String BASE_USRL = "http://yuntuapi.amap.com/";
    public static final String BASE_USRL_WEB = "http://restapi.amap.com/";
    /* key */
    public static final String KEY_NAME = "key";
    /* key value */
    public static final String KEY_VALUE = "6c8289c5f0c1aeb58c19417748b90773";
    /* 签名 */
    public static final String SIG_NAME = "sig";
    /* 签名key */
    public static final String SIG_KEY = "598f8d02cc62aa53f21c66a0ba0a6d84";
    /* tableid */
    public static final String TABLE_ID = "tableid";
    /* table id value */
    public static final String TABLE_ID_VALUE = "59e49e197bbf190cbdf33226";

    /* 定位方式 */
    /**
     * 设置是以请求中的经纬度参数（_location）还是地址参数（_address）
     来计算最终的坐标值。
     可选值：
     1：经纬度；格式示例：104.394729,31.125698
     2：地址；标准格式示例：北京市朝阳区望京阜通东大街6号院3号楼
     */
    public static final int LOC_ADDRESS = 2;
    public static final int LOC_LATLNG = 1;

}
