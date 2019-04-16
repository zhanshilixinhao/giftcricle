package com.chouchong.service.iwant.impl;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.iwant.appUser.EventMapper;
import com.chouchong.entity.iwant.appUser.Event;
import com.chouchong.service.iwant.EventService;
import com.chouchong.service.iwant.vo.EventVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author yy
 * @date 2018/7/11
 **/

@Service
public class EventServiceImpl implements EventService{
    @Autowired
    private EventMapper eventMapper;

    /**
     * 送礼事件列表
     *
     * @param: [page 分页信息, name 事件名称]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @Override
    public Response getEventList(PageQuery page, String name) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<EventVo> events = eventMapper.selectByName(name);
        PageInfo pageInfo = new PageInfo<>(events);
        return ResponseFactory.page(events, pageInfo.getTotal(),
                pageInfo.getPages(), pageInfo.getPageNum(), pageInfo.getPageSize());
    }

    /**
     * 添加送礼事件
     *
     * @param: [name 送礼事件名称]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @Override
    public Response addEvent(String name) {
        Event event = new Event();
        event.setCreated(new Date());
        event.setUpdated(new Date());
        event.setEventName(name);
        event.setUserId(0);
        int count = eventMapper.insert(event);
        if (count == 1) {
            return ResponseFactory.sucMsg("添加成功");
        }
        return ResponseFactory.err("添加失败");
    }




    /**
     * 修改送礼事件
     *
     * @param: [id 送礼事件id, name 送礼事件名称]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @Override
    public Response updateEvent(Integer id, String name) {
        Event event = eventMapper.selectByPrimaryKey(id);
        if (event != null) {
            event.setEventName(name);
            event.setUpdated(new Date());
            int count = eventMapper.updateByPrimaryKey(event);
            if (count == 1) {
                return ResponseFactory.sucMsg("修改成功");
            }
        }
        return ResponseFactory.err("修改失败");
    }

    /**
     * 删除送礼事件
     *
     * @param: [id 送礼事件id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @Override
    public Response delEvent(Integer id) {
        int count = eventMapper.deleteByPrimaryKey(id);
        if (count == 1) {
            return ResponseFactory.sucMsg("删除成功");
        }
        return ResponseFactory.err("删除失败");
    }
}
