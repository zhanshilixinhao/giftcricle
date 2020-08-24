package com.chouchong.utils.sms;

import com.yunpian.sdk.YunpianClient;
import com.yunpian.sdk.model.Result;
import com.yunpian.sdk.model.SmsSingleSend;

import java.util.Map;

/**
 * @author linqin
 * @date 2020/8/22下午1:49
 **/
public class SentUtil2 {

    public final static String APIKEY = "df9b064d3963ecfe22d6ab4b195b79ff";



    /**
     * 使用SDK发送单条短信,智能匹配短信模板
     *
     * @param text   需要使用已审核通过的模板或者默认模板
     * @param mobile 接收的手机号,仅支持单号码发送
     */
    public static SmsSingleSend testSendSms( String mobile, String text) {
        //初始化client,apikey作为所有请求的默认值(可以为空)
        YunpianClient clnt = new YunpianClient(APIKEY).init();
        Map<String, String> param = clnt.newParam(2);

        param.put(YunpianClient.MOBILE, mobile);
        param.put(YunpianClient.TEXT, text);
        param.put(YunpianClient.APIKEY, APIKEY);
        Result<SmsSingleSend> r = clnt.sms().single_send(param);
        //获取返回结果，返回码:r.getCode(),返回码描述:r.getMsg(),API结果:r.getData(),其他说明:r.getDetail(),调用异常:r.getThrowable()
        SmsSingleSend data = r.getData();
        //最后释放client
        clnt.close();
        //账户:clnt.user().* 签名:clnt.sign().* 模版:clnt.tpl().* 短信:clnt.sms().* 语音:clnt.voice().* 流量:clnt.flow().* 隐私通话:clnt.call().*
        return data;
    }

    public static void main(String[] args) throws Exception {
//        testSendSms("15752400657","【礼遇圈】您的" + "content" + "在" + "storeName" + "成功充值" + "recharge" + "元，" +
//                "赠送" + "send" + "元，余额" + "card.getBalance()" + "元，充值时间为" + "time" + "。");
                testSendSms("15752400657","【礼遇圈】您的会员卡在外婆味道成功充值0元，赠送0元，余额0元，充值时间为2020-08-07 14:39:39。");
//
    }

}
