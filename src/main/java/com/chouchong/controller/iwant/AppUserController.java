package com.chouchong.controller.iwant;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.service.iwant.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yy
 * @date 2018/7/9
 **/

@RestController
@RequestMapping("manage/appUser")
public class AppUserController {
    @Autowired
    private AppUserService appUserService;

    /**
     * 获得APP用户列表
     *
     * @param: [page 分页信息, search 查询条件]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @PostMapping("list")
    public Response getAppUserList(PageQuery page, String search) {
        return appUserService.getAppUserList(page, search);
    }

    /**
     * 通过电话查询APP用户
     *
     * @param: [page, phone]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @PostMapping("phone")
    public Response getAppUserByPhone(PageQuery page, String phone) {
        if (phone.trim().equals("")) {
            return ResponseFactory.err("请输入电话号码");
        }
        return appUserService.getAppUserByPhone(page, phone);
    }

    /**
     * 改变APP用户状态
     *
     * @param: [id APP用户id, status APP用户状态]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @PostMapping("status")
    public Response changeStatus(Integer id, Integer status) {
        if (id == null || status == null) {
            return ResponseFactory.errMissingParameter();
        }
        return appUserService.changeStatus(id, status);
    }
}
