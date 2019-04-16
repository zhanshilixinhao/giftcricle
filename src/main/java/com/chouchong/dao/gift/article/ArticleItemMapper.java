package com.chouchong.dao.gift.article;


import com.chouchong.entity.gift.article.ArticleItem;
import com.chouchong.service.gift.article.ArticleVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface ArticleItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ArticleItem record);

    int insertSelective(ArticleItem record);

    ArticleItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ArticleItem record);

    int updateByPrimaryKey(ArticleItem record);

    /**
     * 根据文章id查询商品
     * @param id
     * @return
     */
    List<ArticleVo> selectByArticleId(Integer id);

    /**
     * 通过文章id和商品id获得商品
     * @param articleId
     * @param id
     * @return
     */
    ArticleItem selectByThemeIdAndItemId(@Param("articleId") Integer articleId,@Param("id") int id);
}
