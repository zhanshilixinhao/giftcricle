package com.chouchong.utils;

import com.gexin.rp.sdk.base.IAliasResult;
import com.gexin.rp.sdk.base.IBatch;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.Message;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.base.payload.APNPayload.DictionaryAlertMsg;
import com.gexin.rp.sdk.base.uitls.AppConditions;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>项目名称</b>：children<br>
 * <b>类名称</b>：PushUtils<br>
 * <b>类描述</b>：第三方推送工具类【个推】<br>
 * <b>创建人</b>：SAM QZL<br>
 * <b>创建时间</b>：2017-4-10 下午7:38:52<br>
 * <b>修改人</b>：SAM QZL<br>
 * <b>修改时间</b>：2017-4-10 下午7:38:52<br>
 * <b>修改备注</b>：<br>
 * @author SAM QZL<br>
 * @version
 * 
 */
public class PushUtil {

    /** appId **/
    private static final String appId = "TNw1LmkumR8KhrXKanPVo";
    /** appKey **/
    private static final String appKey = "MFUnmelkXW84mWlISpef74";
    /** 接口密钥 **/
    private static final String masterSecret = "Wspsa1FE6r9dl0Jpcsjgh9";
    /** 推送网关 **/
    private static final String host = "http://sdk.open.api.igexin.com/apiex.htm";
    /** 推送客户端 **/
    private static IGtPush igtPushClient = null;

    /** 私有构造器--不能被实例化 **/
    private PushUtil() {

    }

    /**
     * @功能说明:
     * @param args
     * @throws Exception
     * @返回类型:void
     * @方法名称:main
     * @类名称:PushUtils
     * @文件名称:PushUtils.java
     * @所属包名:com.children.utils
     * @项目名称:children
     * @创建时间:2017-4-10 下午7:38:39
     * @作者:SAM QZL
     * @版本:1.0
     */
    public static void main(String[] args) throws Exception {

        // System.out.println(bindAlias("5652b736f997cae4fa89522fb25972d0",
        // "1"));
        List<String> userIds = new ArrayList<String>();
        userIds.add("17");
        String[] userIdsArr = userIds.toArray(new String[userIds.size()]);
        boolean b = batchPush("我是一个标题！", "我是内容", "{\"content\":\"推送测试喽！\",\"logo\":\"default.png\",\"title\":\"推送测试喽！\",\"isClearable\":\"1\",\"pushMsgType\":\"3\",\"isVibrate\":\"1\",\"logoUrl\":\"http://children.2blab.com:80/bgmanager/img/logo/default.png\",\"isRing\":\"1\"}", userIdsArr);
        System.out.println(b);
    }

    /**
     * @功能说明:获取单例个推客户端.
     * @return IGtPush
     * @返回类型:IGtPush
     * @方法名称:getIgPushClient
     * @类名称:PushUtils
     * @文件名称:PushUtils.java
     * @所属包名:com.children.utils
     * @项目名称:children
     * @创建时间:2017-4-10 下午8:00:47
     * @作者:SAM QZL
     * @版本:1.0
     */
    public synchronized static IGtPush getIgPushClient() {

        if (igtPushClient == null) {
            igtPushClient = new IGtPush(host, appKey, masterSecret);
        }
        return igtPushClient;
    }

    /**
     * @功能说明:绑定用户别名【一对一】
     * @param cid
     *            推送客户端id
     * @param appUserId
     *            自己平台用户id
     * @return true/false true：绑定成功
     * @返回类型:boolean
     * @方法名称:bindAlias
     * @类名称:PushUtils
     * @文件名称:PushUtils.java
     * @所属包名:com.children.utils
     * @项目名称:children
     * @创建时间:2017-4-10 下午8:30:32
     * @作者:SAM QZL
     * @版本:1.0
     */
    public static boolean bindAlias(String cid, String appUserId) {

        /** 绑定别名 **/
        IAliasResult result = getIgPushClient().bindAlias(appId, appUserId, cid);
        /** 返回是否成功 **/
        return result.getResult();
    }

    /**
     * @功能说明:根据别名获取clientId.
     * @param alias
     *            用户别名【即自己平台用户id】
     * @return clientIds
     * @返回类型:List<String>
     * @方法名称:queryClientId
     * @类名称:PushUtils
     * @文件名称:PushUtils.java
     * @所属包名:com.children.utils
     * @项目名称:children
     * @创建时间:2017-4-10 下午8:43:33
     * @作者:SAM QZL
     * @版本:1.0
     */
    public static List<String> queryClientId(String alias) {

        /** 查询clientId **/
        IAliasResult result = getIgPushClient().queryClientId(appId, alias);
        /** 返回别名结果 **/
        return result.getClientIdList();
    }

    /**
     * @功能说明:批量推送【支持推送全部与指定用户推送-有提示的透传】
     * @param title
     *            标题
     * @param content
     *            内容
     * @param transmissionContent
     *            代码
     * @param userIds
     *            用户集合 空或空集合则推送全部
     * @throws Exception
     * @返回类型:void
     * @方法名称:batchPush
     * @类名称:PushUtils
     * @文件名称:PushUtils.java
     * @所属包名:com.children.utils
     * @项目名称:children
     * @创建时间:2017-4-11 下午2:59:49
     * @作者:SAM QZL
     * @版本:1.0
     */
    public static boolean batchPush(String title, String content, String transmissionContent, String[] userIds) throws Exception {

        /** 推送模板-透传模板 **/
        // TransmissionTemplate iTemplate = new TransmissionTemplate();
        LinkTemplate iTemplate = new LinkTemplate();
        /** 消息模版参数设置 **/
        /** appId **/
        iTemplate.setAppId(appId);
        /** appKey **/
        iTemplate.setAppkey(appKey);
        /** 透传消息模板 **/
        // iTemplate.setTransmissionContent(transmissionContent);
        /** 广播等待启动 **/
        // iTemplate.setTransmissionType(2);
        /** -------------ios------------------- **/
        APNPayload payload = new APNPayload();
        /** 角标+1 **/
        payload.setAutoBadge("+1");
        /** IOS透传数据 **/
        payload.setContentAvailable(1);
        /** 默认声音 **/
        payload.setSound("default");
        /** 在客户端通知栏触发特定的action和button显示 **/
        payload.setCategory("");
        /** 字典样式new APNPayload.DictionaryAlertMsg() **/
        DictionaryAlertMsg dictionaryAlertMsg = new APNPayload.DictionaryAlertMsg();
        /** 通知内容 **/
        dictionaryAlertMsg.setBody(content);
        System.out.println("content = " + content);
        /** 通知标题 **/
        dictionaryAlertMsg.setTitle(title);
        /** 设置提示 **/
        payload.setAlertMsg(dictionaryAlertMsg);
        /** 多媒体 **/
        // payload.addMultiMedia(new
        // MultiMedia().setResType(MultiMedia.MediaType.pic).setResUrl("http://children.2blab.com/bgmanager/img/logo/default.png").setOnlyWifi(false));
        /** 添加推送 **/
        iTemplate.setAPNInfo(payload);
        /** -------------ios------------------- **/
        /** 消息体 **/
        Message message = null;
        /** 指定用户推送 **/
        if (userIds != null && userIds.length > 0) {
            /** 指定用户单推 **/
            message = new SingleMessage();
            iTemplate.setTitle("title");
            iTemplate.setText("msg");
            iTemplate.setLogo("push.png");
            iTemplate.setLogoUrl("https://cdn.goexplore.io/Fgwg3m9uP5cBlsOibAvF-KZV_Gt1");
            iTemplate.setUrl("https://cdn.goexplore.io/Fgwg3m9uP5cBlsOibAvF-KZV_Gt1");
            /** 设置推送模板 **/
            message.setData(iTemplate);
            /** 可以离线 **/
            message.setOffline(true);
            /** 离线有效时间，单位为毫秒，可选 **/
           // message.setOfflineExpireTime(24 * 1000 * 3600);
            message.setOfflineExpireTime(3 * 24 * 1000 * 3600);
            /** 批量推送 **/
            IBatch batch = getIgPushClient().getBatch();
            /** 遍历用户id **/
            for (String userId : userIds) {
                /** 用户id换cid **/
                List<String> cids = queryClientId(userId);
                /** 非空校验 **/
                if (cids != null && !cids.isEmpty()) {
                    /** 遍历 **/
                    for (String cid : cids) {
                        /** 推送目标 **/
                        Target target = new Target();
                        /** appid **/
                        target.setAppId(appId);
                        /** 推送设备唯一id **/
                        target.setClientId(cid);
                        /** 添加到批量单推 **/
                        batch.add((SingleMessage) message, target);
                    }
                }
            }
            /** 批量单推 **/
            IPushResult result = batch.submit();
            System.out.println("批量推送");
            /** 返回结果 **/
            return result.getResponse().get("result").equals("ok");
        }
        /** 非指定用户推送 **/
        else {
            /** 实例化消息体 **/
            message = new AppMessage();
            /** 设置推送模板 **/
            iTemplate.setTitle("title");
            iTemplate.setText("msg");
            iTemplate.setLogo("push.png");
            iTemplate.setLogoUrl("logoUrl");
            // 必须设置
            iTemplate.setUrl("https://www.baidu.com/");
            message.setData(iTemplate);
            /** 可以离线 **/
            message.setOffline(true);
            /** 离线有效时间，单位为毫秒，可选 **/
            message.setOfflineExpireTime(24 * 1000 * 3600);
            /** app条件对象 **/
            AppConditions cdt = new AppConditions();
            /** appid集合 **/
            List<String> appIdList = new ArrayList<String>();
            /** 设置appId **/
            appIdList.add(appId);
            /** 设置推送条件 **/
            ((AppMessage) message).setAppIdList(appIdList);
            /** 推送条件 **/
            ((AppMessage) message).setConditions(cdt);
            /** 推送获取结果 **/
            IPushResult result = getIgPushClient().pushMessageToApp((AppMessage) message);
            System.out.println("推送所有用户");
            /** 返回结果 **/
            return result.getResponse().get("result").equals("ok");
        }
    }

    /**
     * 透传消息推送
     * 说明: 消息推送只能使用这个, 因为iOS只能使用透传推送, 但是使用透传消息 安卓就需要自定义通知栏了
     * @param: [title, content, transmissionContent, userIds]
     * @return: boolean
     * @author: yy
     * @Date: 2018/7/27
     */
    public static boolean batchPush1(String title, String content, String transmissionContent, String[] userIds) throws Exception {
        /** 推送模板-透传模板 **/
        TransmissionTemplate iTemplate = new TransmissionTemplate();
        /** 消息模版参数设置 **/
        /** appId **/
        iTemplate.setAppId(appId);
        /** appKey **/
        iTemplate.setAppkey(appKey);
        /** 透传消息模板 **/
        iTemplate.setTransmissionContent(transmissionContent);
        /** 广播等待启动 **/
        iTemplate.setTransmissionType(2);
        /** -------------ios------------------- 这里只针对iOS 与安卓完全无关 安卓的弹窗是需要自定义的title和content的**/
        APNPayload payload = new APNPayload();
        /** 角标+1 **/
        payload.setAutoBadge("+1");
        /** IOS透传数据 **/
        payload.setContentAvailable(1);
        /** 默认声音 **/
        payload.setSound("default");
        /** 在客户端通知栏触发特定的action和button显示 **/
        payload.setCategory("");
        /** 字典样式new APNPayload.DictionaryAlertMsg() **/
        DictionaryAlertMsg dictionaryAlertMsg = new APNPayload.DictionaryAlertMsg();
        /** 通知内容 **/
        dictionaryAlertMsg.setBody(content);
        /** 通知标题 **/
        dictionaryAlertMsg.setTitle(title);
        /** 设置提示 **/
        payload.setAlertMsg(dictionaryAlertMsg);
        /** 多媒体 **/
        // payload.addMultiMedia(new
        // MultiMedia().setResType(MultiMedia.MediaType.pic).setResUrl("http://children.2blab.com/bgmanager/img/logo/default.png").setOnlyWifi(false));
        /** 添加推送 **/
        iTemplate.setAPNInfo(payload);
        /** -------------ios------------------- **/
        /** 消息体 **/
        Message message = null;
        /** 指定用户推送 **/
        if (userIds != null && userIds.length > 0) {
            /** 指定用户单推 **/
            message = new SingleMessage();
            /** 设置推送模板 **/
            message.setData(iTemplate);
            /** 可以离线 **/
            message.setOffline(true);
            /** 离线有效时间，单位为毫秒，可选 **/
            message.setOfflineExpireTime(24 * 1000 * 3600);
            message.setOfflineExpireTime(3 * 24 * 1000 * 3600);
            /** 批量推送 **/
            IBatch batch = getIgPushClient().getBatch();
            /** 遍历用户id **/
            for (String userId : userIds) {
                /** 用户id换cid **/
                List<String> cids = queryClientId(userId);
                /** 非空校验 **/
                if (cids != null && !cids.isEmpty()) {
                    /** 遍历 **/
                    for (String cid : cids) {
                        /** 推送目标 **/
                        Target target = new Target();
                        /** appid **/
                        target.setAppId(appId);
                        /** 推送设备唯一id **/
                        target.setClientId(cid);
                        /** 添加到批量单推 **/
                        batch.add((SingleMessage) message, target);
                    }
                }
            }
            /** 批量单推 **/
            IPushResult result = batch.submit();
            /** 返回结果 **/
            return result.getResponse().get("result").equals("ok");
        }
        /** 非指定用户推送 **/
        else {
            /** 实例化消息体 **/
            message = new AppMessage();
            /** 设置推送模板 **/
            message.setData(iTemplate);
            /** 可以离线 **/
            // message.setOffline(false);
            message.setOffline(true);
            /** 离线有效时间，单位为毫秒，可选 **/
            message.setOfflineExpireTime(24 * 1000 * 3600);
            /** app条件对象 **/
            AppConditions cdt = new AppConditions();
            /** appid集合 **/
            List<String> appIdList = new ArrayList<String>();
            /** 设置appId **/
            appIdList.add(appId);
            /** 设置推送条件 **/
            ((AppMessage) message).setAppIdList(appIdList);
            /** 推送条件 **/
            ((AppMessage) message).setConditions(cdt);
            /** 推送获取结果 **/
            IPushResult result = getIgPushClient().pushMessageToApp((AppMessage) message);
            /** 返回结果 **/
            return result.getResponse().get("result").equals("ok");
        }
    }
}
