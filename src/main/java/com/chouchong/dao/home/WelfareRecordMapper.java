package com.chouchong.dao.home;


import com.chouchong.entity.home.WelfareRecord;
import org.apache.ibatis.annotations.Param;

public interface WelfareRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WelfareRecord record);

    int insertSelective(WelfareRecord record);

    WelfareRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WelfareRecord record);

    int updateByPrimaryKey(WelfareRecord record);

    /**
     * 根基用户id和福利id查看是否领取过
     * @param userId
     * @param id
     * @return
     */
    WelfareRecord selectByUserIdWelfareId(@Param("userId") Integer userId, @Param("id") Integer id);
}
