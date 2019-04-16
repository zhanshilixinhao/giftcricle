package com.chouchong.controller.home.festival;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.entity.home.Festival;
import com.chouchong.service.home.festival.FestivalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author linqin
 * @date 2019/1/18 10:59
 */
@RestController
@RequestMapping("manage/festival")
public class FestivalController {

    @Autowired
    private FestivalService festivalService;

    /**
     * 获取节日列表
     *
     * @param name 节日名称
     * @param time 节日日期
     * @return
     * @author linqin
     * * @date 2019/1/18 10:59
     */
    @PostMapping("list")
    public Response getFestivalList(PageQuery page, String name, Long time) {
        return festivalService.getFestivalList(page, name, time);
    }



    /**
     * 添加节日
     *
     * @param festival
     * @return
     * @author linqin
     * @date 2019/1/18 10:59
     */
    @PostMapping("add")
    public Response addFestival(Festival festival) {
        return festivalService.addFestival(festival);
    }


    /**
     * 修改节日
     *
     * @param festival
     * @return
     * @author linqin
     * @date 2019/1/18 10:59
     */
    @PostMapping("update")
    public Response updateFestival(Festival festival) {
        if (festival.getId() == null) {
            return ResponseFactory.errMissingParameter();
        }
        return festivalService.updateFestival(festival);
    }

    /**
     * 删除节日
     *
     * @param id 节日id
     * @return
     * @author linqin
     * @date 2019/1/18 10:59
     */
    @PostMapping("del")
    public Response delFestival(Integer id) {
        if (id == null){
            return ResponseFactory.errPermissionDenied();
        }
        return festivalService.delFestival(id);
    }


   /**
     * 节日详情
     *
     * @param id 节日id
     * @return
     * @author linqin
     * @date 2019/1/18 10:59
     */
    @PostMapping("detail")
    public Response festivalDetail(Integer id) {
        if (id == null){
            return ResponseFactory.errPermissionDenied();
        }
        return festivalService.festivalDetail(id);
    }

}
