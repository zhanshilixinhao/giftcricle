package com.chouchong.service.iwant;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;

public interface SuggestionService {
    /**
     * 获取意见反馈列表
     *
     * @param: [page 分页信息, search 查询条件]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/25
     */
    Response getSuggestionList(PageQuery page, String search);

    /**
     * 删除意见反馈
     *
     * @param: [id 意见反馈id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/25
     */
    Response delSuggestion(Integer id);
}
