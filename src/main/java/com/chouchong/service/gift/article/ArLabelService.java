package com.chouchong.service.gift.article;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;

/**
 * @author linqin
 * @date 2019/7/30
 */

public interface ArLabelService {

    /**
     * 获取文章对象列表
     *
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    Response getArticleLabel(PageQuery page, String title);

    /**
     * 添加文章对象
     *
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    Response addArLabel(String title);


    /**
     * 修改文章对象
     *
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    Response updateArLabel(Integer id, String title);


    /**
     * 删除文章对象
     *
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    Response delArLabel(Integer id);
}
