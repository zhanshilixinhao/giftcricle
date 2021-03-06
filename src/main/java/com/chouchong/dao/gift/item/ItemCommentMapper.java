package com.chouchong.dao.gift.item;


import com.chouchong.entity.gift.item.ItemComment;
import com.chouchong.service.order.vo.CommentDetail;
import com.chouchong.service.order.vo.CommentVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemCommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItemComment record);

    int insertSelective(ItemComment record);

    ItemComment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ItemComment record);

    int updateByPrimaryKey(ItemComment record);

    /**
     * 评价列表
     * @param map
     * @return
     */
    List<CommentVo> selectBySearch(Map map);

    /**
     * 根据id查询评价详情
     * @param id
     * @return
     */
    CommentDetail selectById(Integer id);

    /**
     * 查询未读的订单数量
     * @param adminId
     * @param start
     * @return
     */
    int selectUnredCount(@Param("adminId") Integer adminId,@Param("start") Long start);
}
