package com.chouchong.service.order.kdapi;

import com.alibaba.fastjson.JSON;
import okhttp3.Response;

import java.io.IOException;

/**
 * @author linqin
 * @date 2018/6/29
 */
public class ExpressApi {
    private static final String URL="http://www.kuaidi100.com/query";

    public static KdResult getLogisticsInfo(String com,String nu)  {
        RequestParams params = new RequestParams();
        params.put("type",com);
        params.put("postid",nu);
        params.put("temp",System.currentTimeMillis());
        KdResult result = new KdResult();
        try {
            Response response = OkHttpUtil.post(URL, params);
            if (response.isSuccessful()){
                String re = response.body().string();
                result = JSON.parseObject(re,KdResult.class);
            }else {
                result.setStatus(response.code());
                result.setMessage(response.message());
            }

        }catch (IOException e){
            e.printStackTrace();
            result.setStatus(-1);
            result.setMessage(e.getMessage());
        }
        return result;
    }



}
