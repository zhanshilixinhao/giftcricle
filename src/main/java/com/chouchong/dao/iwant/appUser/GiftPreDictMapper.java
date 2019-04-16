package com.chouchong.dao.iwant.appUser;

import com.chouchong.entity.iwant.appUser.GiftPreDict;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GiftPreDictMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GiftPreDict record);

    int insertSelective(GiftPreDict record);

    GiftPreDict selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GiftPreDict record);

    int updateByPrimaryKey(GiftPreDict record);

    /**
     * 查询礼物偏好列表
     *
     * @param: [name 礼物偏好名称]
     * @return: java.util.List<com.chouchong.entity.iwant.appUser.GiftPreDict>
     * @author: yy
     * @Date: 2018/7/11
     */
    List<GiftPreDict> selectByName(@Param("name") String name);
}