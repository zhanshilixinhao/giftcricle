package com.chouchong.dao.v3;

import com.chouchong.entity.v3.UserMemberCard;
import com.chouchong.service.v3.vo.UserCardVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface UserMemberCardMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserMemberCard record);

    int insertSelective(UserMemberCard record);

    UserMemberCard selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserMemberCard record);

    int updateByPrimaryKey(UserMemberCard record);

    /**
     * 商家查询会员卡用户
     * @param cardNo
     * @param phone
     * @param adminId
     * @return
     */
    List<UserCardVo> selectBySearch(@Param("cardNo") String cardNo, @Param("phone") String phone, @Param("adminId") Integer adminId);

    List<UserCardVo> selectBySearch1(@Param("cardNo") String cardNo, @Param("phone") String phone, @Param("list") List<Integer> list);

    UserMemberCard selectByUseridcardId(@Param("userId") Integer userId, @Param("cardId") Integer cardId);

}
