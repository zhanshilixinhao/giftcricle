package com.chouchong.test.pushTest;

import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.template.TransmissionTemplate;

/**
 * @author yy
 * @date 2018/7/27
 **/
public class xxx {
    public static TransmissionTemplate getTemplate() {
        TransmissionTemplate template = new TransmissionTemplate();
//        template.setAppId(appId);
//        template.setAppkey(appkey);
        template.setTransmissionContent("透传内容");
        template.setTransmissionType(2);
        APNPayload payload = new APNPayload();
        //在已有数字基础上加1显示，设置为-1时，在已有数字上减1显示，设置为数字时，显示指定数字
        payload.setAutoBadge("+1");
        payload.setContentAvailable(1);
        payload.setSound("default");
        payload.setCategory("$由客户端定义");

        //简单模式APNPayload.SimpleMsg
        payload.setAlertMsg(new APNPayload.SimpleAlertMsg("hello"));

        //字典模式使用APNPayload.DictionaryAlertMsg
        //payload.setAlertMsg(getDictionaryAlertMsg());

        // 添加多媒体资源
/*
        payload.addMultiMedia(new MultiMedia().setResType(MultiMedia.MediaType.video)
                .setResUrl("http://ol5mrj259.bkt.clouddn.com/test2.mp4")
                .setOnlyWifi(true));
*/
//需要使用IOS语音推送，请使用VoIPPayload代替APNPayload
// VoIPPayload payload = new VoIPPayload();
// JSONObject jo = new JSONObject();
// jo.put("key1","value1");
//         payload.setVoIPPayload(jo.toString());
//
        template.setAPNInfo(payload);
        return template;
    }
    private static APNPayload.DictionaryAlertMsg getDictionaryAlertMsg(){
        APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
        alertMsg.setBody("body");
        alertMsg.setActionLocKey("ActionLockey");
        alertMsg.setLocKey("LocKey");
        alertMsg.addLocArg("loc-args");
        alertMsg.setLaunchImage("launch-image");
        // iOS8.2以上版本支持
        alertMsg.setTitle("Title");
        alertMsg.setTitleLocKey("TitleLocKey");
        alertMsg.addTitleLocArg("TitleLocArg");
        return alertMsg;
    }

}
