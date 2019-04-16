package com.chouchong.dao.webUser;

import com.chouchong.entity.webUser.SysRoleMenu;
import com.chouchong.entity.webUser.SysRoleMenuKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleMenuMapper {
    int deleteByPrimaryKey(SysRoleMenuKey key);

    int insert(SysRoleMenu record);

    int insertSelective(SysRoleMenu record);

    SysRoleMenu selectByPrimaryKey(SysRoleMenuKey key);

    int updateByPrimaryKeySelective(SysRoleMenu record);

    int updateByPrimaryKey(SysRoleMenu record);

    /**
     * 批量插入菜单权限
     *
     * @param: [sysRoleMenus 菜单权限集合]
     * @return: int
     * @author: yy
     * @Date: 2018/7/14
     */
    int insertList(@Param("sysRoleMenus") List<SysRoleMenu> sysRoleMenus);

    /**
     * 删除菜单权限通过角色id
     *
     * @param: [roleId]
     * @return: int
     * @author: yy
     * @Date: 2018/7/16
     */
    int deleteByRoleId(@Param("roleId") Integer roleId);
}