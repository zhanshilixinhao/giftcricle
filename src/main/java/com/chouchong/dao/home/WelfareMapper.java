package com.chouchong.dao.home;


import com.chouchong.entity.home.Welfare;
import com.chouchong.service.home.welfare.vo.WelfareVo;

import java.util.List;
import java.util.Map;

public interface WelfareMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Welfare record);

    int insertSelective(Welfare record);

    Welfare selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Welfare record);

    int updateByPrimaryKey(Welfare record);


    /**
     * 获取福利商品详情
     * @return
     */
    Welfare selectAllByTime();

    /**
     * 后台福利列表
     * @return
     */
    List<WelfareVo> selectAllBySearch(Welfare welfare);

    /**
     * 查询所有福利
     * @return
     */
    List<Welfare> selectAll();
}
