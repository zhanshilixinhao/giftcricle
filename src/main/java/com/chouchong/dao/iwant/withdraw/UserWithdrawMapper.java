package com.chouchong.dao.iwant.withdraw;

import com.chouchong.entity.iwant.withdraw.UserWithdraw;
import com.chouchong.service.iwant.vo.UserWithdrawVo;

import java.util.List;
import java.util.Map;

public interface UserWithdrawMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserWithdraw record);

    int insertSelective(UserWithdraw record);

    UserWithdraw selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserWithdraw record);

    int updateByPrimaryKey(UserWithdraw record);

    /**
     * 获得用户提现信息
     *
     * @param: [map 查询条件]
     * @return: java.util.List<com.chouchong.service.iwant.vo.UserWithdrawVo>
     * @author: yy
     * @Date: 2018/7/24
     */
    List<UserWithdrawVo> selectBySearch(Map map);
}