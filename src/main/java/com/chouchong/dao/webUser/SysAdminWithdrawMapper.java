package com.chouchong.dao.webUser;

import com.chouchong.entity.webUser.SysAdminWithdraw;
import com.chouchong.service.gift.coupon.vo.SysWithdrawVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysAdminWithdrawMapper {
    int insert(SysAdminWithdraw record);

    int insertSelective(SysAdminWithdraw record);

    /**
     * 查询商户提现记录
     * @param adminId
     * @return
     */
    List<SysAdminWithdraw> selectByAdminId(Integer adminId);

    List<SysWithdrawVo> selectBySearch(@Param("username") String username,@Param("status") Byte status);

    SysAdminWithdraw selectById(Integer id);

    int updateByPrimaryKeySelective(SysAdminWithdraw record);

}
