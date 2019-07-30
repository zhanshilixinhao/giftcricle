package com.chouchong.controller.gift.article;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.service.gift.article.ArLabelService;
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
@RequestMapping("manage/ar/label")
public class ArLabelController {

    @Autowired
    private ArLabelService arLabelService;

    /**
     * 获取文章对象列表
     *
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    @PostMapping("list")
    public Response getArticleLabel(PageQuery page, String title) {

        return arLabelService.getArticleLabel(page, title);
    }


    /**
     * 添加文章对象
     *
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    @PostMapping("add")
    public Response addArLabel(String title) {
        if (StringUtils.isBlank(title)){
            return ResponseFactory.errMissingParameter();
        }
        return arLabelService.addArLabel( title);
    }


    /**
     * 修改文章对象
     *
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    @PostMapping("update")
    public Response updateArLabel(Integer id,String title) {
        if (StringUtils.isBlank(title) || id == null){
            return ResponseFactory.errMissingParameter();
        }
        return arLabelService.updateArLabel(id, title);
    }



    /**
     * 删除文章对象
     *
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    @PostMapping("del")
    public Response delArLabel(Integer id) {
        if ( id == null){
            return ResponseFactory.errMissingParameter();
        }
        return arLabelService.delArLabel(id);
    }
}
