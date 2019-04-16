package com.chouchong.controller.iwant;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.service.iwant.SuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yy
 * @date 2018/7/25
 **/

@RestController
@RequestMapping("manage/suggestion")
public class SuggestionController {
    @Autowired
    private SuggestionService suggestionService;

    /**
     * 获取意见反馈列表
     *
     * @param: [page 分页信息, search 查询条件]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/25
     */
    @PostMapping("list")
    public Response getSuggestionList(PageQuery page, String search) {
        return suggestionService.getSuggestionList(page, search);
    }

    /**
     * 删除意见反馈
     *
     * @param: [id 意见反馈id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/25
     */
    @PostMapping("del")
    public Response delSuggestion(Integer id) {
        if (id == null) {
            return ResponseFactory.errMissingParameter();
        }
        return suggestionService.delSuggestion(id);
    }
}
