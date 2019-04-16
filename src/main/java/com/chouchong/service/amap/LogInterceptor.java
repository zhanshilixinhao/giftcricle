package com.chouchong.service.amap;

import com.chouchong.common.Utils;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Arrays;

/**
 * ok http 拦截器，打印日志
 *
 * @author yichenshanren on 2016/10/13.
 */

public class LogInterceptor implements Interceptor {

    private static final String TAG = "LogInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
//        NetResponse response = chain.proceed(request);
//        String content = response.body().string();
//        System.out.println(content);
        request = signParams(request);
        return chain.proceed(request);
    }

    private Request signParams(Request request) {
        if (request.body() instanceof FormBody) {
            FormBody formBody = (FormBody) request.body();
            FormBody.Builder builder = new FormBody.Builder();
            //把原来的参数添加到新的构造器，（因为没找到直接添加，所以就new新的）
            String[] paramArr = new String[formBody.size() + 2];
            String name;
            String key;
            for (int i = 0; i < formBody.size(); i++) {
                name = formBody.encodedName(i);
                key = formBody.encodedValue(i);
                builder.addEncoded(name, key);
                paramArr[i] = String.format("%s=%s", name, key);
                System.out.println(key + "||" + name);
            }
            // 装入key参数
            paramArr[paramArr.length - 2] = String.format("%s=%s", AMapConfig.KEY_NAME, AMapConfig.KEY_VALUE);
            // 装入tableid参数
            paramArr[paramArr.length - 1] = String.format("%s=%s", AMapConfig.TABLE_ID, AMapConfig.TABLE_ID_VALUE);
            // 排序参数
            Arrays.sort(paramArr, String.CASE_INSENSITIVE_ORDER);
            StringBuilder sb = new StringBuilder();
            for (String v : paramArr) {
                sb.append(v).append("&");
            }

            sb.delete(sb.length() - 1, sb.length()).append(AMapConfig.SIG_KEY);
            // p艾许值
            System.out.println("排序："+sb.toString());
            System.out.println("排序结束");
            formBody = builder
                    .addEncoded(AMapConfig.KEY_NAME, AMapConfig.KEY_VALUE)
                    .addEncoded(AMapConfig.TABLE_ID, AMapConfig.TABLE_ID_VALUE)
                    .addEncoded(AMapConfig.SIG_NAME, Utils.toMD5(sb.toString(), false))
                    .build();
            request = request.newBuilder().post(formBody).build();
        }

        return request;
    }
}
