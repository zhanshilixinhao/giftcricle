package com.chouchong.dao.gift.label;


import com.chouchong.entity.gift.label.Label;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LabelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Label record);

    int insertSelective(Label record);

    Label selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Label record);

    int updateByPrimaryKey(Label record);

    /**
     * 商城标签列表
     * @return
     */
    List<Label> selectAll();

    /**
     * 根据名称获取列表
     * @param name
     * @return
     */
    List<Label> selectByName(@Param("name") String name);

    /**
     * 根据id删除
     * @return
     */
    int updateStatusById(@Param("id") Integer id, @Param("status") Byte status);

    /**
     * 根据id查询标签
     * @param id
     * @return
     */
    Label selectById(Integer id);
}
