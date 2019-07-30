package com.chouchong.service.gift.article;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;

/**
 * @author linqin
 * @date 2019/7/30
 */

public interface ArFestivalService {

    /**
     * 获取文章节日列表
     *
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    Response getArticleFestival(PageQuery page, String title);

    /**
     * 添加文章节日
     *
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    Response addArFestival(String title);


    /**
     * 修改文章节日
     *
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    Response updateArFestival(Integer id, String title);


    /**
     * 删除文章节日
     *
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    Response delArFestival(Integer id);
}
