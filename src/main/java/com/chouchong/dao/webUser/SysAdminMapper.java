package com.chouchong.dao.webUser;

import com.chouchong.entity.webUser.SysAdmin;
import com.chouchong.entity.webUser.SysFunctionPermission;
import com.chouchong.service.gift.coupon.vo.SysWalletVo;
import com.chouchong.service.webUser.vo.SysAdminVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SysAdminMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysAdmin record);

    int insertSelective(SysAdmin record);

    SysAdmin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysAdmin record);

    int updateByPrimaryKey(SysAdmin record);

    SysAdmin selectByUsernameAndPwd(@Param("username") String username, @Param("password") String password);

    /**
     * 通过管理员id获得 菜单列表
     *
     * @param: [id 管理员id]
     * @return: java.util.List<java.lang.String>
     * @author: yy
     * @Date: 2018/7/16
     */
    List<String> selectMenuById(@Param("id") Integer id);

    /**
     * 通过管理员id获得 权限列表
     *
     * @param: [id 管理员id]
     * @return: java.util.List<java.lang.String>
     * @author: yy
     * @Date: 2018/7/16
     */
    List<String> selectPermissionsById(@Param("id") Integer id);

    /**
     * 通过查询条件获得后台用户列表
     *
     * @param: [map 查询条件]
     * @return: java.util.List<com.chouchong.entity.webUser.SysAdmin>
     * @author: yy
     * @Date: 2018/7/17
     */
    List<SysAdminVo> selectBySearch(Map map);

    /**
     * 通过用户名查找用户
     *
     * @param: [username 用户名]
     * @return: com.chouchong.entity.webUser.SysAdmin
     * @author: yy
     * @Date: 2018/7/17
     */
    SysAdmin selectByUserName(@Param("username") String username);

    /**
     * 通过后台用户id得到用户信息
     *
     * @param: [id]
     * @return: com.chouchong.service.webUser.vo.SysAdminVo
     * @author: yy
     * @Date: 2018/7/18
     */
    SysAdminVo selectByAdminId(@Param("id") Integer id);

    /**
     * 通过用户id获得该用户权限的所有信息
     *
     * @param: [id 后台用户id]
     * @return: java.util.List<com.chouchong.entity.webUser.SysFunctionPermission>
     * @author: yy
     * @Date: 2018/7/19
     */
    List<SysFunctionPermission> selectAllPermissionsById(@Param("id") Integer id);

    /**
     * 查询商户钱包详情
     * @param id
     * @return
     */
    SysWalletVo selectDetailByAdminId(Integer adminId);
}
