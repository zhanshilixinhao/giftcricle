package com.chouchong.service.order.kdapi;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.Response;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author linqin
 * @date 2019/4/17
 */

public class ExpressApi2 {


    private static final String URL = "https://poll.kuaidi100.com/poll/query.do";

    private static final String key = "bjrJZumR3499";

    private static final String customer = "6343B44205FE4494A76FEBB89BC751C4";


    public static void main(String[] args) {
        KdResult2 shentong = checkLogisticsInfo("shentong", "3704668285858");
        System.out.println(shentong.toString());
    }

    public static KdResult2 checkLogisticsInfo(String com, String num) {
//        String param = "{\"com\": \"" + com + "\",\"num\":\""+num+"\"}";
        JSONObject data = new JSONObject();
        data.put("com", com);
        data.put("num", num);
        String sign = MD5Utils.encode(data + key + customer);
        RequestParams params = new RequestParams();
        params.put("param", data.toJSONString());
        params.put("sign", sign);
        params.put("customer", customer);
        KdResult2 result = new KdResult2();
        try {
            Response response = OkHttpUtil.post(URL, params);
            if (response.isSuccessful()) {
                String re = response.body().string();
                result = JSON.parseObject(re, KdResult2.class);
            } else {
                result.setStatus(String.valueOf(response.code()));
                result.setMessage(response.message());
            }

        } catch (IOException e) {
            e.printStackTrace();
            result.setStatus("-1");
            result.setMessage(e.getMessage());
        }
        return result;

    }


}


/**
 * md5加密
 */
class MD5Utils {
    private static MessageDigest mdigest = null;
    private static char digits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private static MessageDigest getMdInst() {
        if (null == mdigest) {
            try {
                mdigest = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return mdigest;
    }

    public static String encode(String s) {
        if (null == s) {
            return "";
        }

        try {
            byte[] bytes = s.getBytes();
            getMdInst().update(bytes);
            byte[] md = getMdInst().digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = digits[byte0 >>> 4 & 0xf];
                str[k++] = digits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
