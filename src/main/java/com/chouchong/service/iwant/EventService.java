package com.chouchong.service.iwant;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;

public interface EventService {
    /**
     * 送礼事件列表
     *
     * @param: [page 分页信息, name 事件名称]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    Response getEventList(PageQuery page, String name);

    /**
     * 添加送礼事件
     *
     * @param: [name 送礼事件名称]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    Response addEvent(String name);

    /**
     * 修改送礼事件
     *
     * @param: [id 送礼事件id, name 送礼事件名称]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    Response updateEvent(Integer id, String name);

    /**
     * 删除送礼事件
     *
     * @param: [id 送礼事件id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    Response delEvent(Integer id);
}
