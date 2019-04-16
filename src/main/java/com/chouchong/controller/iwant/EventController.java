package com.chouchong.controller.iwant;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.service.iwant.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yy
 * @date 2018/7/11
 **/

@RestController
@RequestMapping("manage/event")
public class EventController {
    @Autowired
    private EventService eventService;

    /**
     * 送礼事件列表
     *
     * @param: [page 分页信息, name 事件名称]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @PostMapping("list")
    public Response getEventList(PageQuery page, String name) {
        return eventService.getEventList(page, name);
    }

    /**
     * 添加送礼事件
     *
     * @param: [name 送礼事件名称]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @PostMapping("add")
    public Response addEvent(String name) {
        if (name == null) {
            return ResponseFactory.errMissingParameter();
        }
        return eventService.addEvent(name);
    }

    /**
     * 修改送礼事件
     *
     * @param: [id 送礼事件id, name 送礼事件名称]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @PostMapping("update")
    public Response updateEvent(Integer id, String name) {
        if (id == null || name == null) {
            return ResponseFactory.errMissingParameter();
        }
        return eventService.updateEvent(id, name);
    }

    /**
     * 删除送礼事件
     *
     * @param: [id 送礼事件id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @PostMapping("del")
    public Response delEvent(Integer id) {
        if (id == null) {
            return ResponseFactory.errMissingParameter();
        }
        return eventService.delEvent(id);
    }
}
