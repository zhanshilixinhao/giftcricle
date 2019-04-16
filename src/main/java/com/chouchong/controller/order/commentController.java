package com.chouchong.controller.order;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.service.order.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author linqin
 * @date 2018/12/17 15:48
 */
@RestController
@RequestMapping("manage/order/comment")
public class commentController {

    @Autowired
    private CommentService commentService;

    /**
     * 获取订单评价列表
     *
     * @param pageQuery
     * @param search
     * @return
     * @author linqin
     * @date 2018/12/17 15:48
     */
    @PostMapping("list")
    public Response getCommentList(PageQuery pageQuery, String search) {
        return commentService.getCommentList(pageQuery, search);
    }

    /**
     * 获取评价详情
     * @param id 评价id
     * @return
     * @author linqin
     * @date 2018/12/17 15:48
     */
    @PostMapping("detail")
    public Response getContentDetail(Integer id) {
        if (id == null){
            return ResponseFactory.errMissingParameter();
        }
        return commentService.getContentDetail(id);
    }

    /**
     * 删除评论
     * @param id 评价id
     * @return
     * @author linqin
     * @date 2018/12/17 15:48
     */
    @PostMapping("del")
    public Response deleteComment(Integer id) {
        if (id == null){
            return ResponseFactory.errMissingParameter();
        }
        return commentService.deleteComment(id);
    }
}
