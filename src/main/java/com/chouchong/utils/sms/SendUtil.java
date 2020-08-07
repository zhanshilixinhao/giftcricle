package com.chouchong.utils.sms;

import com.chouchong.common.ErrorCode;
import com.chouchong.common.Utils;
import com.chouchong.exception.ServiceException;
import com.chouchong.service.order.kdapi.OkHttpUtil;
import com.chouchong.service.order.kdapi.RequestParams;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 短信发送工具类
 *
 * @author linqin
 * @date 2018/11/8 9:48
 */

public class SendUtil {

    private final static String username = "liyuquan666hy";


    public static SmsSendResult smsSend(String mobile, String content) throws IOException {
        // 获取当前日期
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");//可以方便地修改日期格式
        String time = dateFormat.format(now);
        //密码
        String password = Utils.toMD5(Utils.toMD5("S79Yhe", false) + time, false);
        //调用接口
        RequestParams params = new RequestParams();
        params.put("username", username);
        params.put("tkey", time);
        params.put("password", password);
        params.put("mobile", mobile);
        params.put("content", content);
//        params.put("content", "【礼遇圈】您的验证码是"+content+"如非本人操作，请忽略本信息。 ");
        Response response = null;
        try {
            // 请求接口
//            response = OkHttpUtil.post("http://api.zthysms.com/sendSms.do", params);
            response = OkHttpUtil.post("http://hy.mix2.zthysms.com/sendSms.do", params);
            // 接口调用失败
            if (!response.isSuccessful()) {
                return new SmsSendResult(1, response.message());
            }
            // 报文body -1,xxxxxxxx
            String body = response.body().string();
            // 判断是否发送成功
            String[] split = body.split(",");
            if (StringUtils.equals(split[0],"1")){
                return new SmsSendResult(0,"发送成功");
            }
            throw new ServiceException(ErrorCode.ERROR.getCode(),"发送失败");

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

//    public static void main(String[] args) throws IOException {
//        smsSend("18288930304","【礼遇圈】尊敬的用户，您的" + "外婆味道储值卡 " + "在" + "木木家文具" + "成功存入：" + "100" + "元，" +
//                "赠送：" + "0" + "元，余额：" + "100" + "元，时间为" + "2020-08-07 14:39:39" + "。如有问题请咨询客服人员。");
////        smsSend("15752400657","【礼遇圈】尊敬的用户，您的外婆味道储值卡在木木家文具成功消费122元，余额：466.0元，消费时间为2020-08-07 14:39:39。如有问题请咨询客服人员。");
//    }
}
