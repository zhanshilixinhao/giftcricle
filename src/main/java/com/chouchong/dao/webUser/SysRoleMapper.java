package com.chouchong.dao.webUser;

import com.chouchong.entity.webUser.SysRole;

import java.util.List;
import java.util.Map;

public interface SysRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    /**
     * 通过查询条件获得角色列表
     *
     * @param: [map 查询条件]
     * @return: java.util.List<com.chouchong.entity.webUser.SysRole>
     * @author: yy
     * @Date: 2018/7/13
     */
    List<SysRole> selectBySearch(Map map);

    /**
     * 获得后台的所有的角色(不包括超级管理员)
     *
     * @param: []
     * @return: java.util.List<com.chouchong.entity.webUser.SysRole>
     * @author: yy
     * @Date: 2018/7/17
     */
    List<SysRole> selectAllRole();
    /**
     * 获得后台的所有的角色（包括超级管理员）
     *
     * @param: []
     * @return: java.util.List<com.chouchong.entity.webUser.SysRole>
     * @author: yy
     * @Date: 2018/7/17
     */
    List<SysRole> selectAllRoleList();
}
