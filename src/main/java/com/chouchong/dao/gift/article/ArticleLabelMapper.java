package com.chouchong.dao.gift.article;

import com.chouchong.entity.gift.article.ArticleLabel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleLabelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ArticleLabel record);

    int insertSelective(ArticleLabel record);

    ArticleLabel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ArticleLabel record);

    int updateByPrimaryKey(ArticleLabel record);

    List<ArticleLabel> selectBySearch(@Param("title")String title);

    ArticleLabel selectByTitle(@Param("title")String title);

    List<ArticleLabel> selectByAll();

}
