package com.chouchong.controller.iwant;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.service.iwant.UserTagDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yy
 * @date 2018/7/10
 **/

@RestController
@RequestMapping("manage/tagDict")
public class UserTagDictController {
    @Autowired
    private UserTagDictService userTagDictService;

    /**
     * 获得标签列表
     *
     * @param: [page 分页信息, search 查询条件]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/10
     */
    @PostMapping("list")
    public Response getTagDictList(PageQuery page, String search) {
        return userTagDictService.getTagDictList(page, search);
    }

    /**
     * 添加印象标签
     *
     * @param: [tag 标签名称, type 标签类型]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/10
     */
    @PostMapping("add")
    public Response addTagDict(String tag, Integer type) {
        if (tag == null || type == null) {
            return ResponseFactory.errMissingParameter();
        }
        return userTagDictService.addTagDict(tag, type);
    }

    /**
     * 修改印象标签
     *
     * @param: [id 标签id, tag 标签内容, type 标签类型]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/10
     */
    @PostMapping("update")
    public Response updateTagDict(Integer id, String tag, Integer type) {
        if (id == null || tag == null || type == null) {
            return ResponseFactory.errMissingParameter();
        }
        return userTagDictService.updateTagDict(id, tag, type);
    }

    /**
     * 修改印象标签状态
     *
     * @param: [id 标签id, status 标签状态]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/11
     */
    @PostMapping("status")
    public Response changeStatus(Integer id, Integer status) {
        if (id == null || status == null) {
            return ResponseFactory.errMissingParameter();
        }
        return userTagDictService.changeStatus(id, status);
    }

    /**
     * 删除印象标签
     *
     * @param: [id 标签id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/11
     */
    @PostMapping("del")
    public Response delTag(Integer id) {
        if (id == null) {
            return ResponseFactory.errMissingParameter();
        }
        return userTagDictService.delTag(id);
    }
}
