package com.chouchong.controller.webUser;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.service.webUser.WebUserService;
import com.chouchong.service.webUser.vo.SysAdminVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 *
 * @author yy
 * @date 2018/6/21
 **/

@RestController
@RequestMapping("manage/user")
public class WebUserController {

    @Autowired
    private WebUserService webUserService;

    /**
     * 获得后台用户列表
     *
     * @param: [page 分页信息, search 查询条件]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @PostMapping("list")
    public Response getWebUserList(PageQuery page, String search) {
        return webUserService.getWebUserList(page, search);
    }

    /**
     * 获得后台用户列表（新加）
     *
     * @param: [page 分页信息, search 查询条件]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @PostMapping("list1")
    public Response getWebUserList1(PageQuery page, String username,String phone,
                                    Integer gender, Integer status) {
        return webUserService.getWebUserList1(page,username,phone,gender,status);
    }





    /**
     * 添加后台用户
     *
     * @param: [sysAdminVo 用户信息]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @PostMapping("add")
    public Response addWebUser(SysAdminVo sysAdminVo) {
        if (sysAdminVo.getAvatar() == null || sysAdminVo.getGender() == null || sysAdminVo.getUsername() == null
                || sysAdminVo.getPassword() == null || sysAdminVo.getRoleId() == null) {
            return ResponseFactory.errMissingParameter();
        }
        return webUserService.addWebUser(sysAdminVo);
    }

    /**
     * 修改后台用户
     *
     * @param: [sysAdminVo 后台用户信息]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @PostMapping("update")
    public Response updateSysAdmin(SysAdminVo sysAdminVo) {
        if (sysAdminVo.getAvatar() == null || sysAdminVo.getGender() == null || sysAdminVo.getUsername() == null
                || sysAdminVo.getRoleId() == null || sysAdminVo.getId() == null) {
            return ResponseFactory.errMissingParameter();
        }
        return webUserService.updateSysAdmin(sysAdminVo);
    }

    /**
     * 后台用户登录
     *
     * @param: [username 用户名, password 密码]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @PostMapping("login")
    public Response login(String username, String password){
        if (StringUtils.isAnyBlank(username, password)) {
            return ResponseFactory.errMissingParameter();
        }
        return webUserService.login(username, password);
    }

    /**
     * 获取用户信息
     *
     * @param: [token]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @PostMapping("info")
    public Response info(String token){
        if (StringUtils.isAnyBlank(token)) {
            return ResponseFactory.errMissingParameter();
        }
        return webUserService.info(token);
    }

    /**
     * 修改密码
     *
     * @param: [oldPassword 老密码, newPassword 新密码, token]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @PostMapping("updatePass")
    public Response updatePassword(String oldPassword, String newPassword, String token){
        if (StringUtils.isAnyBlank(oldPassword, newPassword)) {
            return ResponseFactory.errMissingParameter();
        }
        return webUserService.updatePassword(oldPassword, newPassword, token);
    }

    /**
     * 退出登录
     *
     * @param: [token]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @PostMapping("logout")
    public Response logOut(String token){
        if (StringUtils.isAnyBlank(token)) {
            return ResponseFactory.errMissingParameter();
        }
        return webUserService.logOut(token);
    }

    /**
     * 获得后台用户详情
     *
     * @param: [id 后台用户id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @PostMapping("detail")
    public Response getSysAdminDetail(Integer id){
        if (id == null) {
            return ResponseFactory.errMissingParameter();
        }
        return webUserService.getSysAdminDetail(id);
    }

    /**
     * 改变后台用户装填
     *
     * @param: [id 后台用户id, status 后台用户状态, token]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @PostMapping("status")
    public Response changeStatus(Integer id, Integer status, String token){
        if (id == null || status == null) {
            return ResponseFactory.errMissingParameter();
        }
        return webUserService.changeStatus(id, status, token);
    }

    /**
     * 删除后台用户
     *
     * @param: [id 用户id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @PostMapping("del")
    public Response delSysAdmin(Integer id){
        if (id == null) {
            return ResponseFactory.errMissingParameter();
        }
        return webUserService.delSysAdmin(id);
    }
}
