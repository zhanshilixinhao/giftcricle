package com.chouchong.service.home.festival;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.entity.home.Festival;

/**
 * @author linqin
 * @date 2019/1/18 11:02
 */

public interface FestivalService {

    /**
     * 获取节日列表
     * @param name 节日名称
     * @param time  节日日期
     * @return
     * @author linqin
     * * @date 2019/1/18 10:59
     */
    Response getFestivalList(PageQuery page, String name, Long time);

    /**
     * 修改节日
     * @param festival
     * @return
     * @author linqin
     * @date 2019/1/18 10:59
     */
    Response updateFestival(Festival festival);

    /**
     * 添加节日
     *
     * @param festival
     * @return
     * @author linqin
     * @date 2019/1/18 10:59
     */
    Response addFestival(Festival festival);

    /**
     * 删除节日
     *
     * @param id
     * @return
     * @author linqin
     * @date 2019/1/18 10:59
     */
    Response delFestival(Integer id);

    /**
     * 节日详情
     *
     * @param id 节日id
     * @return
     * @author linqin
     * @date 2019/1/18 10:59
     */
    Response festivalDetail(Integer id);
}
