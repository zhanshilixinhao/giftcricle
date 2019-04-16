package com.chouchong.service.amap;

import com.alibaba.fastjson.JSON;
import com.chouchong.common.Utils;
import okhttp3.ResponseBody;
import org.apache.commons.lang3.StringUtils;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yichenshanren
 * @date 2017/10/16
 */

public class AMapApiImpl {

    private AMapApi api;

    /**
     * 创建api
     */
    private AMapApiImpl(String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .build();
        api = retrofit.create(AMapApi.class);
    }

    /**
     * 创建云图api
     *
     * @return
     */
    public static AMapApiImpl createYuntuApi() {
        return new AMapApiImpl(AMapConfig.BASE_USRL);
    }

    /**
     * 创建web服务api
     *
     * @return
     */
    public static AMapApiImpl createWebApi() {
        return new AMapApiImpl(AMapConfig.BASE_USRL_WEB);
    }

    /**
     * 在云图中添加一条数据
     *
     * @param info 数据
     * @return
     */
    public AMapResponse create(AMapVo info) throws IOException {
        Map<String, String> map = new HashMap<>();
        map.put("loctype", String.valueOf(AMapConfig.LOC_LATLNG));
        map.put("data", JSON.toJSONString(info));
        createSig(map);
        Response<ResponseBody> response = api.create(map).execute();
        if (!response.isSuccessful()) return null;
        return JSON.parseObject(response.body().string(), AMapResponse.class);
    }

    /**
     * 修改云图中的一条数据
     *
     * @param info 数据
     * @return
     */
    public AMapResponse update(AMapVo info) throws IOException {
        Map<String, String> map = new HashMap<>();
        map.put("loctype", String.valueOf(AMapConfig.LOC_LATLNG));
        map.put("data", JSON.toJSONString(info));
        createSig(map);
        Response<ResponseBody> response = api.update(map).execute();
        if (!response.isSuccessful()) return null;
        return JSON.parseObject(response.body().string(), AMapResponse.class);
    }

    /**
     * 删除一条高德数据
     *
     * @param yuntuId yuntuid
     */
    public BaseResponse del(Integer yuntuId) throws IOException {
        Map<String, String> map = new HashMap<>();
        map.put("ids", String.valueOf(yuntuId));
        createSig(map);
        Response<ResponseBody> response = api.del(map).execute();
        if (!response.isSuccessful()) return null;
        String str = response.body().string();
        System.out.println(str);
        return JSON.parseObject(str, BaseResponse.class);
    }

    /**
     * 获取行政区域
     *
     * @param adcode 行政区编码
     * @return
     */
    public DistrictResponse getDistricts(String adcode) throws IOException {
       return getDistricts(adcode, 1);
    }

    public DistrictResponse getDistricts(String adcode, int level) throws IOException {
        Map<String, String> map = new HashMap<>();
        // 装入高德地图key参数
        map.put(AMapConfig.KEY_NAME, AMapConfig.KEY_VALUE);
        String keyword = adcode == null ? "" : adcode;
        map.put("keywords", keyword);
        map.put("subdistrict", String.valueOf(level)); // 只获取一个层级
        map.put("offset", "1"); // 只获取一个层级
        createSig(map, false);
        Response<ResponseBody> response = api.district(map).execute();
        if (!response.isSuccessful()) return null;
        String json = response.body().string();
//        System.out.println(json);
        DistrictResponse res = JSON.parseObject(json, DistrictResponse.class);
        return res;
    }

    /**
     * 搜索附近的代理商
     *
     * @param latitude  纬度
     * @param longitude 经度
     * @param adcode    行政区域代码
     * @param order     排序 1 距离，2 人气， 3 评价
     * @param pageNo
     * @param pageSize
     * @return
     */
    public AroundResponse aroundSearch(double latitude, double longitude, String adcode,
                                       Integer order, Integer pageNo, Integer pageSize) throws IOException {
        Map<String, String> map = new HashMap<>();
        // 装入高德地图key参数
        map.put(AMapConfig.KEY_NAME, AMapConfig.KEY_VALUE);
        // 中心坐标
        map.put("center", String.format("%s,%s", longitude, latitude));
        // 半径默认最大半径
        map.put("radius", "50000");
        // 过滤条件
        // 首先过滤被禁用的
        String filter = "active:1";
        // 过滤地区
        if (StringUtils.isNotBlank(adcode)) {
            filter = filter + "+adcode:" + adcode;
        }
        map.put("filter", filter);
        if (order == null) {
            order = 1;
        }
        // 排序方式
        String str = "distance:1"; // 默认按距离排序
        switch (order) {
            case 2: // 人气
                str = "views:1";
                break;
            case 3: // 评价
                str = "star:1";
                break;
            default:
                break;
        }
        map.put("sortrule", str);
        // 分页
        map.put("limit", String.valueOf(pageSize));
        map.put("page", String.valueOf(pageNo));
        // 签名
        createSig(map);
        Response<ResponseBody> response = api.aroundSearch(map).execute();
        if (!response.isSuccessful()) {
            return null;
        }
        return JSON.parseObject(response.body().string(), AroundResponse.class);
    }

    /**
     * 高德地图接口签名
     *
     * @param map
     * @param tableId
     */
    public void createSig(Map<String, String> map, boolean tableId) {
        // 装入高德地图key参数
        map.put(AMapConfig.KEY_NAME, AMapConfig.KEY_VALUE);
        // 装入tableid参数
        if (tableId) {
            map.put(AMapConfig.TABLE_ID, AMapConfig.TABLE_ID_VALUE);
        }
        String[] arr = new String[map.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            arr[i++] = String.format("%s=%s", entry.getKey(), entry.getValue());
        }

        // 排序参数
        Arrays.sort(arr, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for (String v : arr) {
            sb.append(v).append("&");
        }
        sb.delete(sb.length() - 1, sb.length()).append(AMapConfig.SIG_KEY);
        // p艾许值
        System.out.println("排序：" + sb.toString());
        System.out.println("排序结束");
        // 加入签名参数
        map.put(AMapConfig.SIG_NAME, Utils.toMD5(sb.toString(), false));
    }

    public void createSig(Map<String, String> map) {
        createSig(map, true);
    }

    public static void main(String[] args) throws IOException {
        DistrictResponse response = AMapApiImpl.createWebApi().getDistricts("100000", 3);
        System.out.println(response);
    }
}
