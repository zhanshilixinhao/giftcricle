package com.chouchong.dao.v4;

import com.chouchong.entity.v4.RebateCouponLog;

import com.chouchong.service.v4.vo.RebateCouponLogVo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.ArrayList;

/**
 * @Description:
 * @Author Lxh
 * @Date 2020/10/21 17:21
 */
public interface RebateCouponLogMapper extends Mapper<RebateCouponLog>, MySqlMapper<RebateCouponLog> {


    ArrayList<RebateCouponLogVo> findLogByWeek(@Param(value = "phone")String phone,
                                               @Param(value = "adminId") Integer adminId,
                                               @Param(value = "creatAdminId") Integer creatAdminId);


    ArrayList<RebateCouponLogVo> findLogByDay(@Param(value = "phone")String phone,
                                              @Param(value = "adminId") Integer adminId,
                                              @Param(value = "creatAdminId") Integer creatAdminId);

    ArrayList<RebateCouponLogVo> findLogByMonth(@Param(value = "phone")String phone,
                                                @Param(value = "adminId") Integer adminId,
                                                @Param(value = "creatAdminId") Integer creatAdminId);

    ArrayList<RebateCouponLogVo> findAllLog(@Param(value = "phone")String phone,
                                            @Param(value = "adminId") Integer adminId,
                                            @Param(value = "creatAdminId") Integer creatAdminId);

    ArrayList<RebateCouponLogVo> findAllLog2(@Param(value = "phone")String phone,
                                             @Param(value = "adminId") Integer adminId,
                                             @Param(value = "creatAdminId") Integer creatAdminId);
}
