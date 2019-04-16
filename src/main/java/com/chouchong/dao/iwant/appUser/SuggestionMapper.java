package com.chouchong.dao.iwant.appUser;

import com.chouchong.entity.iwant.appUser.Suggestion;
import com.chouchong.service.iwant.vo.SuggestionVo;

import java.util.List;
import java.util.Map;

public interface SuggestionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Suggestion record);

    int insertSelective(Suggestion record);

    Suggestion selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Suggestion record);

    int updateByPrimaryKey(Suggestion record);

    /**
     * 获取意见反馈列表
     *
     * @param: [map]
     * @return: java.util.List<com.chouchong.service.iwant.vo.SuggestionVo>
     * @author: yy
     * @Date: 2018/7/25
     */
    List<SuggestionVo> selectBySearch(Map map);
}