package com.chouchong.service.iwant;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;

public interface UserTagDictService {
    /**
     * 获得标签列表
     *
     * @param: [page 分页信息, search 查询条件]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/10
     */
    Response getTagDictList(PageQuery page, String search);

    /**
     * 添加印象标签
     *
     * @param: [tag 标签名称, type 标签类型]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/10
     */
    Response addTagDict(String tag, Integer type);

    /**
     * 修改印象标签
     *
     * @param: [id 标签id, tag 标签内容, type 标签类型]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/10
     */
    Response updateTagDict(Integer id, String tag, Integer type);

    /**
     * 修改印象标签状态
     *
     * @param: [id 标签id, status 标签状态]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/11
     */
    Response changeStatus(Integer id, Integer status);

    /**
     * 删除印象标签
     *
     * @param: [id 标签id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/11
     */
    Response delTag(Integer id);
}
