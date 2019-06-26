package com.chouchong.dao.gift.article;


import com.chouchong.entity.gift.article.ArticleFestival;

import java.util.List;

public interface ArticleFestivalMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ArticleFestival record);

    int insertSelective(ArticleFestival record);

    ArticleFestival selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ArticleFestival record);

    int updateByPrimaryKey(ArticleFestival record);

    List<ArticleFestival> selectByAll();
}
