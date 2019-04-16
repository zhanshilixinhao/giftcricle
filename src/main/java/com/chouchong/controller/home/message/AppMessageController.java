package com.chouchong.controller.home.message;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.service.home.message.AppMessageService;
import com.chouchong.service.home.message.vo.PushMsgVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * app_message 中message_type = 2 为后台消息推送
 *
 * @author yy
 * @date 2018/7/23
 **/

@RestController
@RequestMapping("manage/message")
public class AppMessageController {
    @Autowired
    private AppMessageService appMessageService;

    /**
     * 获得系统通知列表
     *
     * @param: [page 分页信息, search 查询条件]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/25
     */
    @RequestMapping("list")
    public Response getAppPushMessage(PageQuery page, String search) {
        return appMessageService.getAppPushMessage(page, search);
    }

    /**
     * 删除系统通知
     *
     * @param: [id 系统通知id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/25
     */
    @RequestMapping("del")
    public Response delAppPushMessage(Integer id) {
        if (id == null) {
            return ResponseFactory.errMissingParameter();
        }
        return appMessageService.delAppPushMessage(id);
    }

    /**
     * 添加系统通知
     *
     * @param: [msgVo 系统通知信息]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/25
     */
    @RequestMapping("add")
    public Response addAppPushMessage(PushMsgVo msgVo, String token) {
        if (msgVo == null || token == null) {
            return ResponseFactory.errMissingParameter();
        }
        return appMessageService.addAppPushMessage(msgVo, token);
    }
}
