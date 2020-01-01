package com.chouchong.dao.v3;

import com.chouchong.entity.v3.UserMemberCard;
import com.chouchong.service.v3.vo.UserCardVo;
import com.chouchong.service.v3.vo.UserCardVo1;
import com.chouchong.service.v3.vo.UserCardVo2;
import com.chouchong.service.v3.vo.UserCardVos;
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
     * @param title
     * @return
     */
    List<UserCardVo> selectBySearch(@Param("cardNo") String cardNo, @Param("phone") String phone,
                                    @Param("adminId") Integer adminId, @Param("type") Byte type, @Param("title") String title);

    List<UserCardVo> selectBySearch1(@Param("cardNo") String cardNo, @Param("phone") String phone,
                                     @Param("list") List<Integer> list, @Param("title") String title);

    UserMemberCard selectByUseridcardId(@Param("userId") Integer userId, @Param("cardId") Integer cardId);

    List<UserCardVo1> selectUserCard(@Param("phone") String phone, @Param("list") List<Integer> list);

    List<UserCardVo2> selectByUserId(@Param("userId") Integer userId,@Param("list") List<Integer> list);

    UserCardVos selectBySearchs(@Param("cardNo") String cardNo, @Param("phone") String phone, @Param("list") List<Integer> list, @Param("title") String title);

    UserCardVos selectBySearchs1(@Param("cardNo") String cardNo, @Param("phone") String phone, @Param("adminId") Integer adminId, @Param("type") Byte type, @Param("title") String title);
}
