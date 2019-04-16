package com.chouchong.service.gift.themeItem.impl;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.gift.themeItem.ThemeItemMapper;
import com.chouchong.dao.gift.themeItem.ThemeMapper;
import com.chouchong.entity.gift.themeItem.Theme;
import com.chouchong.service.gift.themeItem.ThemeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author yy
 * @date 2018/7/3
 **/

@Service
@Transactional(rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
public class ThemeServiceImpl implements ThemeService {
    @Autowired
    private ThemeMapper themeMapper;

    @Autowired
    private ThemeItemMapper themeItemMapper;

    /**
     * 获得主题列表
     *
     * @param: [page 分页信息, name 主题名称]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/3
     */
    @Override
    public Response getThemeList(PageQuery page, String name) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<Theme> themes= themeMapper.selectByName(name);
        PageInfo pageInfo = new PageInfo<>(themes);
        return ResponseFactory.page(themes, pageInfo.getTotal(),
                pageInfo.getPages(), pageInfo.getPageNum(), pageInfo.getPageSize());
    }

    /**
     * 添加主题
     *
     * @param: [name 主题名称, cover 主题封面]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/5
     */
    @Override
    public Response addTheme(String name, String cover) {
        Theme theme = new Theme();
        theme.setStatus((byte)2);
        theme.setUpdated(new Date());
        theme.setCreated(new Date());
        theme.setName(name);
        theme.setSort(0);
        theme.setCover(cover);
        int count = themeMapper.insert(theme);
        if (count == 1) {
            return ResponseFactory.sucMsg("添加成功");
        }
        return ResponseFactory.err("添加失败");
    }

    /**
     * 修改主题
     *
     * @param: [theme 主题信息]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/5
     */
    @Override
    public Response updateTheme(Theme theme) {
        Theme theme1 = themeMapper.selectByPrimaryKey(theme.getId());
        if (theme1 == null) {
            return ResponseFactory.err("无此主题");
        }
        theme1.setCover(theme.getCover());
        theme1.setUpdated(new Date());
        theme1.setSort(theme.getSort());
        theme1.setName(theme.getName());
        int count = themeMapper.updateByPrimaryKey(theme1);
        if (count == 1) {
            return ResponseFactory.sucMsg("修改成功");
        }
        return ResponseFactory.err("修改失败");
    }

    /**
     * 修改主题状态
     *
     * @param: [id 主题id, status 主题状态]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/5
     */
    @Override
    public Response updateStatus(Integer id, Integer status) {
        Theme theme = themeMapper.selectByPrimaryKey(id);
        if (theme == null) {
            return ResponseFactory.err("无此主题");
        }
        theme.setUpdated(new Date());
        theme.setStatus(status.byteValue());
        int count = themeMapper.updateByPrimaryKey(theme);
        if (count == 1) {
            return ResponseFactory.sucMsg("设置成功");
        }
        return ResponseFactory.err("设置失败");
    }

    /**
     * 删除主题
     *
     * @param: [id 主题id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/5
     */
    @Override
    public Response delTheme(Integer id) {
        int count = themeMapper.deleteByThemeId(id);
        if (count == 1) {
            count = themeItemMapper.deleteByThemeId(id);
            return ResponseFactory.sucMsg("删除成功");
        }
        return ResponseFactory.err("删除失败");
    }

    /**
     * 获得全部主题
     *
     * @param: []
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/5
     */
    @Override
    public Response getAllTheme() {
        List<Theme> themes = themeMapper.selectAllTheme();
        return ResponseFactory.sucData(themes);
    }
}
