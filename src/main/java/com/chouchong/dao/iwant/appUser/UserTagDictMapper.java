package com.chouchong.dao.iwant.appUser;

import com.chouchong.entity.iwant.appUser.UserTagDict;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserTagDictMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserTagDict record);

    int insertSelective(UserTagDict record);

    UserTagDict selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserTagDict record);

    int updateByPrimaryKey(UserTagDict record);

    /**
     * 通过查询条件获得印象标签列表
     *
     * @param: [map 查询条件]
     * @return: java.util.List<com.chouchong.entity.iwant.appUser.UserTagDict>
     * @author: yy
     * @Date: 2018/7/10
     */
    List<UserTagDict> selectBySearch(Map map);
}