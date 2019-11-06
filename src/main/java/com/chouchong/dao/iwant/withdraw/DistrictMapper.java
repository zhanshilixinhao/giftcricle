package com.chouchong.dao.iwant.withdraw;

import com.chouchong.entity.iwant.withdraw.District;
import com.chouchong.service.v3.vo.DistrictVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DistrictMapper {
    int insert(District record);

    int insertSelective(District record);

    List<DistrictVo> selectSimple(@Param("level") String level, @Param("pAdcode") Integer pAdcode);
}
