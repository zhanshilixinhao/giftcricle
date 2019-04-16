package com.chouchong.dao.gift.article;

import com.chouchong.entity.gift.article.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Article record);

    int insertSelective(Article record);

    Article selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKeyWithBLOBs(Article record);

    int updateByPrimaryKey(Article record);

    /**
     * 获得文章列表
     *
     * @param: [name 文章名称]
     * @return: java.util.List<com.chouchong.entity.gift.article.Article>
     * @author: yy
     * @Date: 2018/7/4
     */
    List<Article> selectByName(@Param("name") String name,@Param("type") Byte type);

    /**
     * 查询所有banner文章
     * @return
     */
    List<Article> selectAllBanner();
}
