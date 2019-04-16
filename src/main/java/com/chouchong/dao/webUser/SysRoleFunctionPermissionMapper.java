package com.chouchong.dao.webUser;

import com.chouchong.entity.webUser.SysRoleFunctionPermission;
import com.chouchong.entity.webUser.SysRoleFunctionPermissionKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleFunctionPermissionMapper {
    int deleteByPrimaryKey(SysRoleFunctionPermissionKey key);

    int insert(SysRoleFunctionPermission record);

    int insertSelective(SysRoleFunctionPermission record);

    SysRoleFunctionPermission selectByPrimaryKey(SysRoleFunctionPermissionKey key);

    int updateByPrimaryKeySelective(SysRoleFunctionPermission record);

    int updateByPrimaryKey(SysRoleFunctionPermission record);

    /**
     * 批量插入方法权限
     *
     * @param: [permissions 方法权限]
     * @return: int
     * @author: yy
     * @Date: 2018/7/14
     */
    int insertList(@Param("permissions") List<SysRoleFunctionPermission> permissions);

    /**
     * 通过角色id获得角色方法权限
     *
     * @param: [id 角色id]
     * @return: com.chouchong.entity.webUser.SysRoleFunctionPermission
     * @author: yy
     * @Date: 2018/7/14
     */
    List<SysRoleFunctionPermission> selectByRoleId(@Param("id") Integer id);

    /**
     * 通过角色id删除角色权限
     *
     * @param: [roleId 角色id]
     * @return: int
     * @author: yy
     * @Date: 2018/7/16
     */
    int deleteByRoleId(@Param("roleId") Integer roleId);
}