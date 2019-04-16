package com.chouchong.controller.gift.themeItem;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.entity.gift.themeItem.Theme;
import com.chouchong.service.gift.themeItem.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yy
 * @date 2018/7/3
 **/

@RestController
@RequestMapping("manage/theme")
public class ThemeController {
    @Autowired
    private ThemeService themeService;

    /**
     * 获得主题列表
     *
     * @param: [page 分页信息, name 主题名称]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/3
     */
    @PostMapping("list")
    public Response getThemeList(PageQuery page, String name) {
        return themeService.getThemeList(page, name);
    }

    /**
     * 添加主题
     *
     * @param: [name 主题名称, cover 主题封面]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/5
     */
    @PostMapping("add")
    public Response addTheme(String name, String cover) {
        if (name == null || cover == null) {
            return ResponseFactory.errMissingParameter();
        }
        return themeService.addTheme(name, cover);
    }

    /**
     * 修改主题
     *
     * @param: [theme 主题信息]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/5
     */
    @PostMapping("update")
    public Response updateTheme(Theme theme) {
        if (theme.getCover() == null || theme.getName() == null || theme.getId() == null) {
            return ResponseFactory.errMissingParameter();
        }
        return themeService.updateTheme(theme);
    }

    /**
     * 删除主题
     *
     * @param: [id 主题id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/5
     */
    @PostMapping("del")
    public Response delTheme(Integer id) {
        if (id == null) {
            return ResponseFactory.errMissingParameter();
        }
        return themeService.delTheme(id);
    }

    /**
     * 修改主题状态
     *
     * @param: [id 主题id, status 主题状态]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/5
     */
    @PostMapping("status")
    public Response updateStatus(Integer id, Integer status) {
        if (status == null || id == null) {
            return ResponseFactory.errMissingParameter();
        }
        return themeService.updateStatus(id, status);
    }

    /**
     * 获得全部主题
     *
     * @param: []
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/5
     */
    @PostMapping("all")
    public Response getAllTheme() {
        return themeService.getAllTheme();
    }
}
