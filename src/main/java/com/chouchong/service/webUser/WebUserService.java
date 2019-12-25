package com.chouchong.service.webUser;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.service.webUser.vo.SysAdminVo;

public interface WebUserService {
    /**
     * 后台用户登录
     *
     * @param: [username 用户名, password 密码]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    Response login(String username, String password,Integer client);

    /**
     * 获取用户信息
     *
     * @param: [token]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    Response info(String token);

    /**
     * 修改密码
     *
     * @param: [oldPassword 老密码, newPassword 新密码, token]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    Response updatePassword(String oldPassword, String newPassword, String token);

    /**
     * 退出登录
     *
     * @param: [token]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    Response logOut(String token);

    /**
     * 获得后台用户列表
     *
     * @param: [page 分页信息, search 查询条件]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    Response getWebUserList(PageQuery page, String search);

    /**
     * 添加后台用户
     *
     * @param: [sysAdminVo 用户信息]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    Response addWebUser(SysAdminVo sysAdminVo);

    /**
     * 获得后台用户详情
     *
     * @param: [id 后台用户id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    Response getSysAdminDetail(Integer id);

    /**
     * 修改后台用户
     *
     * @param: [sysAdminVo 后台用户信息]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    Response updateSysAdmin(SysAdminVo sysAdminVo);

    /**
     * 改变后台用户装填
     *
     * @param: [id 后台用户id, status 后台用户状态, token]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    Response changeStatus(Integer id, Integer status, String token);

    /**
     * 删除后台用户
     *
     * @param: [id 用户id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    Response delSysAdmin(Integer id);

    /**
     * 获得后台用户列表（新加）
     *
     * @param: [page 分页信息, search 查询条件]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    Response getWebUserList1(PageQuery page, String username,  String phone, Integer gender, Integer status);
}
