package com.chouchong.service.amap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

/**
 * 高德地图api
 *
 * @author yichenshanren
 * @date 2017/10/16
 */

public interface AMapApi {

    /**
     * 添加一条数据到搞得地图
     *
     * @return
     */
    @FormUrlEncoded
    @POST("datamanage/data/create")
    Call<ResponseBody> create(@FieldMap Map<String, String> map);

    /**
     * 添加一条数据到搞得地图
     *
     * @param loctype 数据
     * @param data    数据
     * @return
     */
    @FormUrlEncoded
    @POST("datamanage/data/update")
    Call<ResponseBody> update(@FieldMap Map<String, String> map);

    /**
     * 添加一条数据到搞得地图
     *
     * @param ids 要删除的ids集合
     * @return
     */
    @FormUrlEncoded
    @POST("datamanage/data/delete")
    Call<ResponseBody> del(@FieldMap Map<String, String> map);

    /**
     * 查询行政区域
     *
     * @return
     */
    @GET("v3/config/district")
    Call<ResponseBody> district(@QueryMap Map<String, String> map);

    /**
     * 周边搜索
     *
     * @return
     */
    @GET("datasearch/around")
    Call<ResponseBody> aroundSearch(@QueryMap Map<String, String> map);
}
