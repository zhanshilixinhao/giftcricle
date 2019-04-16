package com.chouchong.dao.webUser;

import com.chouchong.entity.webUser.SysMenu;
import com.chouchong.service.webUser.vo.MenuVo;

import java.util.List;

public interface SysMenuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysMenu record);

    int insertSelective(SysMenu record);

    SysMenu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysMenu record);

    int updateByPrimaryKey(SysMenu record);

    /**
     * 获得所有的菜单
     *
     * @param: []
     * @return: java.util.List<com.chouchong.service.webUser.vo.MenuVo>
     * @author: yy
     * @Date: 2018/7/13
     */
    List<MenuVo> selectAllMenu();

    List<SysMenu> selectByParentId();
}
