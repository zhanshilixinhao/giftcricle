package com.chouchong.dao.webUser;

import com.chouchong.entity.webUser.SysFunctionPermission;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

public interface SysFunctionPermissionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysFunctionPermission record);

    int insertSelective(SysFunctionPermission record);

    SysFunctionPermission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysFunctionPermission record);

    int updateByPrimaryKey(SysFunctionPermission record);

    /**
     * 获得方法权限名称集合
     *
     * @param: [roleFunction 方法权限id]
     * @return: java.lang.String
     * @author: yy
     * @Date: 2018/7/14
     */
    List<String> selectByFunctionList(@Param("roleFunction") List<Integer> roleFunction);

    /**
     * 通过uri找到权限
     *
     * @param: [requestURI]
     * @return: com.chouchong.entity.webUser.SysFunctionPermission
     * @author: yy
     * @Date: 2018/7/19
     */
    SysFunctionPermission selectByUrl(@Param("url") String url);
}