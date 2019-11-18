package com.chouchong.dao.v3;


import com.chouchong.entity.v3.StoreTurnover;

public interface StoreTurnoverMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StoreTurnover record);

    int insertSelective(StoreTurnover record);

    StoreTurnover selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StoreTurnover record);

    int updateByPrimaryKey(StoreTurnover record);
}
