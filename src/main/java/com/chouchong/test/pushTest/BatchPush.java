package com.chouchong.test.pushTest;

import java.io.IOException;

import com.gexin.rp.sdk.base.IBatch;
import com.gexin.rp.sdk.base.IIGtPush;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;

public class BatchPush {
    /** appId **/
    private static final String appId = "TNw1LmkumR8KhrXKanPVo";
    /** appKey **/
    private static final String appKey = "MFUnmelkXW84mWlISpef74";
    /** 接口密钥 **/
    private static final String masterSecret = "Wspsa1FE6r9dl0Jpcsjgh9";
    /** 推送网关 **/
    private static final String host = "http://sdk.open.api.igexin.com/apiex.htm";

    public static void main(String[] args) throws IOException {

        IGtPush push = new IGtPush(host, appKey, masterSecret);
        IBatch batch = push.getBatch();

        try {
            //构建客户a的透传消息a
            constructClientTransMsg(push.queryClientId(appId, "1").getClientIdList().get(0),"msgA",batch);
            //构建客户B的点击通知打开网页消息b
            constructClientTransMsg(push.queryClientId(appId, "2").getClientIdList().get(0),"msgB",batch);
        } catch (Exception e) {
            e.printStackTrace();
        }
        batch.submit();
    }

    private static void constructClientTransMsg(String cid, String msg ,IBatch batch) throws Exception {

        SingleMessage message = new SingleMessage();
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(appId);
        template.setAppkey(appKey);
        template.setTransmissionContent(msg);
        template.setTransmissionType(1); // 这个Type为int型，填写1则自动启动app

        message.setData(template);
        message.setOffline(true);
        message.setOfflineExpireTime(1 * 1000);

        // 设置推送目标，填入appid和clientId
        Target target = new Target();
        target.setAppId(appId);
        target.setClientId(cid);
        batch.add(message, target);
    }

    private static void constructClientLinkMsg(String cid, String msg ,IBatch batch) throws Exception {

        SingleMessage message = new SingleMessage();
        LinkTemplate template = new LinkTemplate();
        template.setAppId(appId);
        template.setAppkey(appKey);
        template.setTitle("title");
        template.setText("msg");
        template.setLogo("push.png");
        template.setLogoUrl("https://cdn.goexplore.io/Fgwg3m9uP5cBlsOibAvF-KZV_Gt1");
        template.setUrl("https://cdn.goexplore.io/Fgwg3m9uP5cBlsOibAvF-KZV_Gt1");

        message.setData(template);
        message.setOffline(true);
        message.setOfflineExpireTime(1 * 1000);

        // 设置推送目标，填入appid和clientId
        Target target = new Target();
        target.setAppId(appId);
        target.setClientId(cid);
        batch.add(message, target);
    }
}
