package com.chouchong.service.home.festival;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.entity.home.memo.MemoFestival;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author linqin
 * @date 2019/3/15 15:30
 */

public interface MemoService {

    /**
     * 获取备忘录节日事件列表
     *
     * @param page
     * @param name
     * @return
     * @author linqin
     * @date 2019/3/15
     */
    Response getMemoFestivalList(PageQuery page, String name);
    /**
     * 添加备忘录节日事件
     *
     * @param memoFestival
     * @return
     * @author linqin
     * @date 2019/3/15
     */
    Response addMemoFestival(MemoFestival memoFestival);

    /**
     * 修改备忘录节日事件
     *
     * @param memoFestival
     * @return
     * @author linqin
     * @date 2019/3/15
     */
    Response updateMemoFestival(MemoFestival memoFestival);

    /**
     * 删除节日事件
     *
     * @param id 节日事件列表
     * @return
     * @author linqin
     * @date 2019/3/15
     */
    Response delMemoFestival(Integer id);
    /**
     * 节日事件详情
     *
     * @param id 节日事件列表id
     * @return
     * @author linqin
     * @date 2019/3/15
     */
    Response detailMemoFestival(Integer id);
    /**
     * 获取节日商品列表
     * @param id  节日事件列表id
     * @return
     * @author: linqin
     * @Date: 2019/1/16
     */
    Response getItemList(Integer id);
    /**
     * 删除商品
     * @param id  节日事件id
     * @return
     * @author: linqin
     * @Date: 2019/1/16
     */
    Response delFestivalItem(Integer id);

    /**
     * 添加节日事件商品
     *
     * @param festivalId  节日事件id
     * @param ids     商品id集合
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    Response addFestivalItem(Integer festivalId, String ids);
}
