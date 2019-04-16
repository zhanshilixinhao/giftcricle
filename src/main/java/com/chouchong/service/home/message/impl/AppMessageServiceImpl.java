package com.chouchong.service.home.message.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.chouchong.common.Constants;
import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.home.message.AppMessageMapper;
import com.chouchong.dao.home.message.AppMessageUserMapper;
import com.chouchong.dao.home.message.MessagePushMapper;
import com.chouchong.dao.iwant.appUser.AppUserMapper;
import com.chouchong.entity.home.message.AppMessage;
import com.chouchong.entity.home.message.AppMessageUser;
import com.chouchong.entity.home.message.MessagePush;
import com.chouchong.entity.iwant.appUser.AppUser;
import com.chouchong.entity.webUser.SysAdmin;
import com.chouchong.redis.MRedisTemplate;
import com.chouchong.service.home.message.AppMessageService;
import com.chouchong.service.home.message.vo.PushMsgVo;
import com.chouchong.service.webUser.vo.WebUserInfo;
import com.chouchong.utils.PushUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author yy
 * @date 2018/7/23
 **/
@Service
@Transactional(rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
public class AppMessageServiceImpl implements AppMessageService {
    @Autowired
    private AppMessageMapper appMessageMapper;

    @Autowired
    private AppUserMapper appUserMapper;

    @Autowired
    private AppMessageUserMapper appMessageUserMapper;

    @Autowired
    private MessagePushMapper messagePushMapper;

    @Autowired
    private MRedisTemplate mRedisTemplate;


    /**
     * 获得系统通知列表
     *
     * @param: [page 分页信息, search 查询条件]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/25
     */
    @Override
    public Response getAppPushMessage(PageQuery page, String search) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        JSONObject jsonObject = JSON.parseObject(search);
        Map map = new HashMap();
        map.put("title",jsonObject.getString("title"));
        map.put("summary",jsonObject.getString("summary"));
        List<PushMsgVo> pushMsgVos = appMessageMapper.selectBySearch(map);
        PageInfo pageInfo = new PageInfo<>(pushMsgVos);
        return ResponseFactory.page(pushMsgVos, pageInfo.getTotal(),
                pageInfo.getPages(), pageInfo.getPageNum(), pageInfo.getPageSize());
    }

    /**
     * 删除系统通知
     *
     * @param: [id 系统通知id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/25
     */
    @Override
    public Response delAppPushMessage(Integer id) {
        int count = appMessageMapper.deleteByPrimaryKey(id);
        if (count == 1) {
            appMessageUserMapper.deleteByMessageId(id);
            return ResponseFactory.sucMsg("删除成功!");
        }
        return ResponseFactory.err("无此系统通知!");
    }

    /**
     * 添加系统通知
     *
     * @param: [msgVo 系统通知信息]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/25
     */
    @Override
    public Response addAppPushMessage(PushMsgVo msgVo, String token) {
        AppMessage appMessage = new AppMessage();
        appMessage.setUpdated(new Date());
        appMessage.setTitle(msgVo.getTitle());
        String userIds[] = new String[1];
        // 如果用户电话为null则设置目标id为0
        if (msgVo.getPhone().equals("")) {
            appMessage.setTargetId(0);
            userIds = null;
        } else { // 否则，设置目标id为用户电话的用户id
            List<AppUser> appUsers = appUserMapper.selectByPhone(msgVo.getPhone());
            if (appUsers.size() == 0) {
                return ResponseFactory.err("无此用户电话, 请检查!");
            }
            userIds[0] = appUsers.get(0).getId().toString();
            appMessage.setTargetId(appUsers.get(0).getId());
        }

        // 根据token取出用户信息
        WebUserInfo webUserInfo = mRedisTemplate.get(token, new TypeReference<WebUserInfo>() {
        });
        if (webUserInfo == null) {
            return ResponseFactory.errNeedLogin(Constants.USER_TOKEN_ERROR);
        }
        SysAdmin sysAdmin = webUserInfo.getSysAdmin();

        appMessage.setSummary(msgVo.getSummary());
        appMessage.setContent(msgVo.getSummary());
        appMessage.setTargetType((byte)22);
        appMessage.setCreated(new Date());
        appMessage.setMessageType((byte)2);
        int count = appMessageMapper.insert(appMessage);


        /** 推送 暂时不用**/
      /*  if (PushUtil.batchPush1(msgVo.getTitle(), msgVo.getSummary(), msgVo.getSummary(), userIds)) {
            *//** 推送状态 **//*
            System.out.println("成功了");
            MessagePush messagePush = new MessagePush();
            messagePush.setAppMessageId(appMessage.getId());
            messagePush.setVibrate((byte)1);
            messagePush.setUpdated(new Date());
            messagePush.setUpdateAdminId(sysAdmin.getId());
            messagePush.setTypeValue("");
            messagePush.setTitle(msgVo.getTitle());
            messagePush.setStatus((byte)1);
            messagePush.setRing((byte)1);
            messagePush.setCreated(new Date());
            messagePush.setCreateAdminId(sysAdmin.getId());
            messagePush.setContent(msgVo.getSummary());
            messagePush.setClearable((byte)1);
            messagePushMapper.insert(messagePush);
        }
        else {
            *//** 推送失败！ **//*
            System.out.println("失败了");
            // pushMsg.setPushStatus(Constant.PUSHMSGNO);
        }*/

        if (count == 1) {
            if (msgVo.getPhone().equals("")) {
                List<AppMessageUser> appMessageUsers = new ArrayList<AppMessageUser>();
                Map map = new HashMap();
                List<AppUser> appUsers = appUserMapper.selectBySearch(map);
                for (AppUser appUser: appUsers) {
                    AppMessageUser appMessageUser = new AppMessageUser();
                    appMessageUser.setUserId(appUser.getId());
                    appMessageUser.setUpdated(new Date());
                    appMessageUser.setCreated(new Date());
                    appMessageUser.setAppMessageId(appMessage.getId());
                    appMessageUser.setIsRead((byte)2);
                    appMessageUsers.add(appMessageUser);
                }
                count = appMessageUserMapper.insertList(appMessageUsers);
            } else {
                List<AppUser> appUsers = appUserMapper.selectByPhone(msgVo.getPhone());
                AppMessageUser appMessageUser = new AppMessageUser();
                appMessageUser.setUserId(appUsers.get(0).getId());
                appMessageUser.setUpdated(new Date());
                appMessageUser.setCreated(new Date());
                appMessageUser.setAppMessageId(appMessage.getId());
                appMessageUser.setIsRead((byte)2);
                count = appMessageUserMapper.insert(appMessageUser);
            }
            return ResponseFactory.sucMsg("系统通知发送成功!");
        }
        return ResponseFactory.err("系统通知发送失败!");
    }
}
