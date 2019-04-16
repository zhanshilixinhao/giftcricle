package com.chouchong.service.home.message;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.service.home.message.vo.PushMsgVo;

public interface AppMessageService {
    /**
     * 获得系统通知列表
     *
     * @param: [page 分页信息, search 查询条件]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/25
     */
    Response getAppPushMessage(PageQuery page, String search);

    /**
     * 删除系统通知
     *
     * @param: [id 系统通知id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/25
     */
    Response delAppPushMessage(Integer id);

    /**
     * 添加系统通知
     *
     * @param: [msgVo 系统通知信息]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/25
     */
    Response addAppPushMessage(PushMsgVo msgVo, String token);
}
