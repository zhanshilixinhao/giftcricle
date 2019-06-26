package com.chouchong.dao.gift.article;


import com.chouchong.entity.gift.article.ArticleScene;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleSceneMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ArticleScene record);

    int insertSelective(ArticleScene record);

    ArticleScene selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ArticleScene record);

    int updateByPrimaryKey(ArticleScene record);

    List<ArticleScene> selectBySearch(@Param("title") String title);

    ArticleScene selectByTitle(@Param("title") String title);


    List<ArticleScene> selectByAll();

}
