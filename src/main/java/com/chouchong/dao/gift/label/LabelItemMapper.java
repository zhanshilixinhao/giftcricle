package com.chouchong.dao.gift.label;


import com.chouchong.entity.gift.label.LabelItem;
import com.chouchong.service.gift.label.vo.LabelItemVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

public interface LabelItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LabelItem record);

    int insertSelective(LabelItem record);

    LabelItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LabelItem record);

    int updateByPrimaryKey(LabelItem record);

    /**
     * 查询标签商品列表
     * @param map
     * @return
     */
    List<LabelItemVo> selectBySearch(Map map);

    /**
     * 通过标签id和商品id获得商品
     * @param labelId
     * @param id
     * @return
     */
    LabelItem selectByThemeIdAndItemId(@Param("labelId") Integer labelId,@Param("id") int id);
}
