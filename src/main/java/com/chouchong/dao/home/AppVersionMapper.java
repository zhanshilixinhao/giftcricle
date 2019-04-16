package com.chouchong.dao.home;

import com.chouchong.entity.home.AppVersion;

public interface AppVersionMapper {
    int insert(AppVersion record);

    int insertSelective(AppVersion record);

    AppVersion selectAllList();


    AppVersion selectById(Integer id);

//    int deleteById(Integer id);

    int deleteByAll();
}
