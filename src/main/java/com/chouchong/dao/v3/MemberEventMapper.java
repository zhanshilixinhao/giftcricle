package com.chouchong.dao.v3;


import com.chouchong.entity.v3.MemberEvent;
import com.chouchong.service.v3.vo.EventVo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface MemberEventMapper  {
    int deleteByPrimaryKey(Integer id);

    int insert(MemberEvent record);

    int insertSelective(MemberEvent record);

    MemberEvent selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MemberEvent record);

    int updateByPrimaryKey(MemberEvent record);

    List<EventVo> selectByAll(@Param("adminId") Integer adminId, @Param("title") String title, @Param("type") Byte type);

    /**
     * 所有礼遇圈添加的活动
     * @param list
     * @return
     */
    List<EventVo> selectAllByAdminIds(@Param("list") List<Integer> list, @Param("title") String title, @Param("type") Byte type);

    List<EventVo> selectByCardId(Integer cardId);
}
