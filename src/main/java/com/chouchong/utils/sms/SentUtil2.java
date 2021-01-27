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
        System.out.println(r);
        //获取返回结果，返回码:r.getCode(),返回码描述:r.getMsg(),API结果:r.getData(),其他说明:r.getDetail(),调用异常:r.getThrowable()
        SmsSingleSend data = r.getData();
        //最后释放client
        clnt.close();
        //账户:clnt.user().* 签名:clnt.sign().* 模版:clnt.tpl().* 短信:clnt.sms().* 语音:clnt.voice().* 流量:clnt.flow().* 隐私通话:clnt.call().*
        return data;
    }

    public static void main(String[] args) throws Exception {
        SmsSingleSend smsSingleSend = testSendSms("18208799641", "【礼遇圈】您本次的退款金额是336.22元，如非本人操作，请忽略本短信");
        System.out.println(smsSingleSend);
//
    }

}
