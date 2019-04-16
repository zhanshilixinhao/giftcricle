package com.chouchong.controller.gift.themeItem;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.entity.gift.themeItem.Theme;
import com.chouchong.entity.gift.themeItem.ThemeItem;
import com.chouchong.service.gift.themeItem.ThemeItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yy
 * @date 2018/7/3
 **/

@RestController
@RequestMapping("manage/themeItem")
public class ThemeItemController {
    @Autowired
    private ThemeItemService themeItemService;

    /**
     * 获得主题商品列表
     *
     * @param: [page 分页信息, search 查询条件]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/5
     */
    @PostMapping("list")
    public Response getThemeItemList(PageQuery page, String search){
        return themeItemService.getThemeItemList(page, search);
    }

    /**
     * 修改主题商品状态
     *
     * @param: [id 主题商品id , status 商品状态]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/5
     */
    @PostMapping("status")
    public Response changeStatus(Integer id, Integer status) {
        if (id == null || status == null) {
            return ResponseFactory.errMissingParameter();
        }
        return themeItemService.changeStatus(id, status);
    }

    /**
     * 删除主题商品
     *
     * @param: [id 主题商品id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/5
     */
    @PostMapping("del")
    public Response delThemeItem(Integer id) {
        if (id == null) {
            return ResponseFactory.errMissingParameter();
        }
        return themeItemService.delThemeItem(id);
    }

    /**
     * 添加主题商品
     *
     * @param: [themeId 主题id, ids 商品id集合]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/5
     */
    @PostMapping("add")
    public Response addThemeItem(Integer themeId, String ids) {
        if (themeId == null || ids == null) {
            return ResponseFactory.errMissingParameter();
        }
        if (ids.trim().equals("")) {
            return ResponseFactory.errMissingParameter();
        }
        return themeItemService.addThemeItem(themeId, ids);
    }

    /**
     * 主题商品排序
     *
     * @param: [themeItem 主题商品信息]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/5
     */
    @PostMapping("sort")
    public Response setThemeItemSort(ThemeItem themeItem) {
        if (themeItem == null) {
            return ResponseFactory.errMissingParameter();
        }
        if (themeItem.getId() == null) {
            return ResponseFactory.errMissingParameter();
        }
        return themeItemService.setThemeItemSort(themeItem);
    }
}
