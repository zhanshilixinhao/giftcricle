package com.chouchong.service.gift.label;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.entity.gift.label.Label;

/**
 * @author linqin
 * @date 2019/1/14 10:41
 */

public interface LabelService {


    /**
     * 获取标签列表
     * @param pageQuery
     * @param name
     * @return
     * @author linqin
     * @date 2019/1/14 10:39
     */
    Response getLabelList(PageQuery pageQuery, String name);

    /**
     * 添加标签
     *
     * @param name
     * @param cover
     * @return
     * @author linqin
     * @date 2019/1/14 10:39
     */
    Response addLabel(String name, String cover);

    /**
     * 修改标签
     *
     * @param label
     * @return
     * @author linqin
     * * @date 2019/1/14 10:39
     */
    Response updateLabel(Label label);

    /**
     * 删除标签
     * @param labelId 标签id
     * @return
     * @author linqin
     * @date 2019/1/14 10:39
     */
    Response deleteLabel(Integer labelId);

    /**
     * 改变标签状态
     * @param id 标签id
     * @return
     * @author linqin
     * @date 2019/1/14 10:39
     */
    Response updateStatus(Integer id);

    /**
     * 获取所有的标签
     *
     * @return
     * @author linqin
     * @date 2019/1/14 10:39
     */
    Response getAllList();
}
