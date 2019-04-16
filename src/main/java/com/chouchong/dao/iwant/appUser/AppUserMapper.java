package com.chouchong.dao.iwant.appUser;

import com.chouchong.entity.iwant.appUser.AppUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AppUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AppUser record);

    int insertSelective(AppUser record);

    AppUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AppUser record);

    int updateByPrimaryKey(AppUser record);

    /**
     * 通过查询条件获取用户列表
     *
     * @param: [map 查询条件]
     * @return: java.util.List<com.chouchong.entity.iwant.appUser.AppUser>
     * @author: yy
     * @Date: 2018/7/9
     */
    List<AppUser> selectBySearch(Map map);

    /**
     * 通过电话号码获得用户信息
     *
     * @param: [phone]
     * @return: java.util.List<com.chouchong.entity.iwant.appUser.AppUser>
     * @author: yy
     * @Date: 2018/7/9
     */
    List<AppUser> selectByPhone(@Param("phone") String phone);

    /**
     * 按天统计用户注册
     *
     * @param: [phone]
     * @return: java.util.List<com.chouchong.entity.iwant.appUser.AppUser>
     * @author: linqin
     * @Date: 2018/7/31
     */
    List<Map> selectByCreated(@Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 按月统计用户注册
     *
     * @param: [phone]
     * @return: java.util.List<com.chouchong.entity.iwant.appUser.AppUser>
     * @author: linqin
     * @Date: 2018/7/31
     */
    List<Map> selectByCreatedMonth(@Param("startMonth") String startMonth,@Param("endMonth") String endMonth);

    /**
     * 统计用户注册
     *
     * @param: [phone]
     * @return: java.util.List<com.chouchong.entity.iwant.appUser.AppUser>
     * @author: linqin
     * @Date: 2018/7/31
     */
    List<Map> selectAllByCreated();
}