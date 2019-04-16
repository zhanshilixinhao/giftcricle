package com.chouchong.dao.home.memo;


import com.chouchong.entity.home.memo.MemoFestivalItem;
import com.chouchong.service.gift.article.ArticleVo;
import com.chouchong.service.home.welfare.vo.ItemListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MemoFestivalItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MemoFestivalItem record);

    int insertSelective(MemoFestivalItem record);

    MemoFestivalItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MemoFestivalItem record);

    int updateByPrimaryKey(MemoFestivalItem record);

    /**
     * 根据节日事件id查询商品
     * @param id
     * @return
     */
    List<ArticleVo> selectByMemoFestivalId(Integer id);

    /**
     * 通过节日事件id和商品id获得商品
     * @param festivalId
     * @param id
     * @return
     */
    MemoFestivalItem selectByFestivalIdAndItemId(@Param("festivalId") Integer festivalId,@Param("id") int id);
}
