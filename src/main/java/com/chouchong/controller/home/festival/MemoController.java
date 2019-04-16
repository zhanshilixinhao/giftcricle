package com.chouchong.controller.home.festival;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.entity.home.memo.MemoFestival;
import com.chouchong.service.home.festival.MemoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author linqin
 * @date 2019/3/15 15:29
 */
@RestController
@RequestMapping("manage/memo")
public class MemoController {

    @Autowired
    private MemoService memoService;

    /**
     * 获取备忘录节日事件列表
     *
     * @param page
     * @param name
     * @return
     * @author linqin
     * @date 2019/3/15
     */
    @PostMapping("list")
    public Response getMemoFestivalList(PageQuery page, String name) {
        return memoService.getMemoFestivalList(page, name);
    }

    /**
     * 添加备忘录节日事件
     *
     * @param memoFestival
     * @return
     * @author linqin
     * @date 2019/3/15
     */
    @PostMapping("add")
    public Response addMemoFestival(MemoFestival memoFestival) {
        if (memoFestival == null) {
            return ResponseFactory.errMissingParameter();
        }
        if (StringUtils.isAnyBlank(memoFestival.getName(), memoFestival.getSummary(), memoFestival.getCover(), memoFestival.getPicture(),
                memoFestival.getTitle(), memoFestival.getDetail()) || memoFestival.getTargetTime() == null) {
            return ResponseFactory.errMissingParameter();
        }
        return memoService.addMemoFestival(memoFestival);
    }


    /**
     * 修改备忘录节日事件
     *
     * @param memoFestival
     * @return
     * @author linqin
     * @date 2019/3/15
     */
    @PostMapping("update")
    public Response updateMemoFestival(MemoFestival memoFestival) {
        if (memoFestival == null) {
            return ResponseFactory.errMissingParameter();
        }
        if (StringUtils.isAnyBlank(memoFestival.getName(), memoFestival.getSummary(), memoFestival.getCover(), memoFestival.getPicture(),
                memoFestival.getTitle(), memoFestival.getDetail()) || memoFestival.getTargetTime() == null || memoFestival.getId() == null) {
            return ResponseFactory.errMissingParameter();
        }
        return memoService.updateMemoFestival(memoFestival);
    }

    /**
     * 删除节日事件
     *
     * @param id 节日事件列表id
     * @return
     * @author linqin
     * @date 2019/3/15
     */
    @PostMapping("del")
    public Response delMemoFestival(Integer id) {
        if (id == null){
            return ResponseFactory.errMissingParameter();
        }
        return memoService.delMemoFestival(id);
    }



    /**
     * 节日事件详情
     *
     * @param id 节日事件列表id
     * @return
     * @author linqin
     * @date 2019/3/15
     */
    @PostMapping("detail")
    public Response detailMemoFestival(Integer id) {
        if (id == null){
            return ResponseFactory.errMissingParameter();
        }
        return memoService.detailMemoFestival(id);
    }


    /*---------------------------------商品管理-------------------------------------*/

    /**
     * 获取节日商品列表
     * @param id  节日事件id
     * @return
     * @author: linqin
     * @Date: 2019/1/16
     */
    @PostMapping("item_list")
    public Response getItemList(Integer id) {
        if (id == null){
            return ResponseFactory.errMissingParameter();
        }
        return memoService.getItemList(id);
    }

    /**
     * 删除商品
     * @param id  节日事件id
     * @return
     * @author: linqin
     * @Date: 2019/1/16
     */
    @PostMapping("del_item")
    public Response delFestivalItem( Integer id){
        if (id == null){
            return ResponseFactory.errMissingParameter();
        }
        return memoService.delFestivalItem(id);
    }



    /**
     * 添加节日事件商品
     *
     * @param festivalId  节日事件id
     * @param ids     商品id集合
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    @PostMapping("add_item")
    public Response addFestivalItem(Integer festivalId, String ids) {
        if (festivalId == null || ids == null) {
            return ResponseFactory.errMissingParameter();
        }
        if (ids.trim().equals("")) {
            return ResponseFactory.errMissingParameter();
        }
        return memoService.addFestivalItem(festivalId, ids);
    }





}
