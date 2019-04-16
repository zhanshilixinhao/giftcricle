package com.chouchong.service.order;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;

/**
 * @author linqin
 * @date 2018/12/17 16:18
 */

public interface CommentService {

    /**
     * 获取提货订单列表
     *
     * @param pageQuery
     * @param search
     * @return
     * @author linqin
     * @date 2018/12/17 15:48
     */
    Response getCommentList(PageQuery pageQuery, String search);

    /**
     * 删除评论
     * @param id
     * @return
     * @author linqin
     * @date 2018/12/17 15:48
     */
    Response deleteComment(Integer id);

    /**
     * 获取评价详情
     * @param id 评价id
     * @return
     * @author linqin
     * @date 2018/12/17 15:48
     */
    Response getContentDetail(Integer id);
}
