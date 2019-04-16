package com.chouchong.service.order.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.gift.item.ItemCommentMapper;
import com.chouchong.entity.gift.item.ItemComment;
import com.chouchong.service.order.CommentService;
import com.chouchong.service.order.vo.CommentDetail;
import com.chouchong.service.order.vo.CommentVo;
import com.chouchong.service.webUser.vo.WebUserInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author linqin
 * @date 2018/12/17 16:20
 */
@Service
public class CommentServiceImpl  implements CommentService {

    @Autowired
    private ItemCommentMapper  itemCommentMapper;

    @Autowired
    private HttpServletRequest httpServletRequest;

    /**
     * 获取提货订单列表
     *
     * @param pageQuery
     * @param search
     * @return
     * @author linqin
     * @date 2018/12/17 15:48
     */
    @Override
    public Response getCommentList(PageQuery pageQuery, String search) {
        //分页
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize());
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        //查询列表
        JSONObject object = JSON.parseObject(search);
        Map map = new HashMap();
        map.put("phone",object.getString("phone"));
        map.put("nickname",object.getString("nickname"));
        map.put("title",object.getString("title"));
        map.put("orderNo",object.getString("orderNo"));
        if (webUserInfo.getRoleId() == 3){
            map.put("adminId",webUserInfo.getSysAdmin().getId());
        }
        List<CommentVo> list = itemCommentMapper.selectBySearch(map);
        PageInfo pageInfo = new PageInfo<>(list);
        return ResponseFactory.page(list,pageInfo.getTotal(),
                pageInfo.getPages(), pageInfo.getPageNum(), pageInfo.getPageSize());
    }


    /**
     * 获取评价详情
     * @param id 评价id
     * @return
     * @author linqin
     * @date 2018/12/17 15:48
     */
    @Override
    public Response getContentDetail(Integer id) {
        // 根据id查询评价详情
        CommentDetail comment = itemCommentMapper.selectById(id);
        return ResponseFactory.sucData(comment);
    }

    /**
     * 删除评论
     * @param id
     * @return
     * @author linqin
     * @date 2018/12/17 15:48
     */
    @Override
    public Response deleteComment(Integer id) {
        int count = itemCommentMapper.deleteByPrimaryKey(id);
        if (count == 1) {
            return ResponseFactory.sucMsg("删除成功");
        }
        return ResponseFactory.err("删除失败");
    }
}
