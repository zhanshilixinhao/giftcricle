package com.chouchong.controller.gift.article;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.service.gift.article.ArFestivalService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author linqin
 * @date 2019/7/30
 */
@RestController
@RequestMapping("manage/ar/Festival")
public class ArfestivalController {

    @Autowired
    private ArFestivalService arFestivalService;

    /**
     * 获取文章节日列表
     *
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    @PostMapping("list")
    public Response getArticleFestival(PageQuery page, String title) {

        return arFestivalService.getArticleFestival(page, title);
    }


    /**
     * 添加文章节日
     *
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    @PostMapping("add")
    public Response addArFestival(String title) {
        if (StringUtils.isBlank(title)){
            return ResponseFactory.errMissingParameter();
        }
        return arFestivalService.addArFestival( title);
    }


    /**
     * 修改文章节日
     *
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    @PostMapping("update")
    public Response updateArFestival(Integer id,String title) {
        if (StringUtils.isBlank(title) || id == null){
            return ResponseFactory.errMissingParameter();
        }
        return arFestivalService.updateArFestival(id, title);
    }



    /**
     * 删除文章节日
     *
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    @PostMapping("del")
    public Response delArFestival(Integer id) {
        if ( id == null){
            return ResponseFactory.errMissingParameter();
        }
        return arFestivalService.delArFestival(id);
    }
}
