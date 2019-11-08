package com.chouchong.dao.webUser;

import com.chouchong.entity.webUser.SysAdminRole;
import com.chouchong.entity.webUser.SysAdminRoleKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysAdminRoleMapper {
    int deleteByPrimaryKey(SysAdminRoleKey key);

    int insert(SysAdminRole record);

    int insertSelective(SysAdminRole record);

    SysAdminRole selectByPrimaryKey(SysAdminRoleKey key);

    int updateByPrimaryKeySelective(SysAdminRole record);

    int updateByPrimaryKey(SysAdminRole record);

    /**
     * 通过后台用户id获得用户角色关系
     *
     * @param: [id 后台用户id]
     * @return: com.chouchong.entity.webUser.SysAdminRole
     * @author: yy
     * @Date: 2018/7/20
     */
    SysAdminRole selectByAdminId(@Param("id") Integer id);

    /**
     * 通过角色id获得后台用户集合
     *
     * @param: [id 角色id]
     * @return: java.util.List<com.chouchong.entity.webUser.SysAdminRole>
     * @author: yy
     * @Date: 2018/7/20
     */
    List<SysAdminRole> selectByRoleId(@Param("id") Integer id);
    /**
     * 通过角色id获得后台用户id
     *
     * @param: [id 角色id]
     * @return: java.util.List<com.chouchong.entity.webUser.SysAdminRole>
     * @author: yy
     * @Date: 2018/7/20
     */
    List<Integer> selectIdByRoleId(@Param("roleId") Integer roleId);
}
