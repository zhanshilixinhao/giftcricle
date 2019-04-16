package com.chouchong.controller.gift.label;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.entity.gift.label.Label;
import com.chouchong.service.gift.label.LabelService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 标签
 *
 * @author linqin
 * @date 2019/1/14 10:39
 */
@RestController
@RequestMapping("manage/label")
public class LabelController {

    @Autowired
    private LabelService labelService;

    /**
     * 获取标签列表
     *
     * @param pageQuery
     * @param name
     * @return
     * @author linqin
     * @date 2019/1/14 10:39
     */
    @PostMapping("list")
    public Response getLabelList(PageQuery pageQuery, String name) {
        return labelService.getLabelList(pageQuery, name);
    }

    /**
     * 添加标签
     *
     * @param name
     * @param cover
     * @return
     * @author linqin
     * @date 2019/1/14 10:39
     */
    @PostMapping("add")
    public Response addLabel(String name, String cover) {
        if (StringUtils.isAnyBlank(name, cover)) {
            return ResponseFactory.errMissingParameter();
        }
        return labelService.addLabel(name, cover);
    }

    /**
     * 修改标签
     *
     * @param label
     * @return
     * @author linqin
     * @date 2019/1/14 10:39
     */
    @PostMapping("update")
    public Response updateLabel(Label label) {
        if (StringUtils.isAnyBlank(label.getName(), label.getCover()) || label.getId() == null) {
            return ResponseFactory.errMissingParameter();
        }
        return labelService.updateLabel(label);
    }

    /**
     * 删除标签
     *
     * @param labelId 标签id
     * @return
     * @author linqin
     * @date 2019/1/14 10:39
     */
    @PostMapping("del")
    public Response deleteLabel(Integer labelId) {
        if (labelId == null) {
            return ResponseFactory.errMissingParameter();
        }
        return labelService.deleteLabel(labelId);
    }

    /**
     * 改变标签状态
     *
     * @param id 标签id
     * @return
     * @author linqin
     * @date 2019/1/14 10:39
     */
    @PostMapping("status")
    public Response updateStatus(Integer id) {
        if (id == null) {
            return ResponseFactory.errMissingParameter();
        }
        return labelService.updateStatus(id);
    }

    /**
     * 获取所有的标签
     *
     * @return
     * @author linqin
     * @date 2019/1/14 10:39
     */
    @PostMapping("all")
    public Response getAllList() {
        return labelService.getAllList();
    }

}
