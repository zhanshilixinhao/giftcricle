package com.chouchong.dao.gift.discount;

import com.chouchong.entity.webUser.Discounting;
import com.chouchong.service.gift.discount.vo.DiscountVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

public interface DiscountingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Discounting record);

    int insertSelective(Discounting record);

    Discounting selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Discounting record);

    int updateByPrimaryKey(Discounting record);

    List<Map> selectAll(@Param("nickname") String nickname,
                        @Param("title")String title,
                        @Param("type") Byte type,
                        @Param("status")Byte status);

    int updateStatusById(Integer id);

    DiscountVo selectById(Integer id);

}