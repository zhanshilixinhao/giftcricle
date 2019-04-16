package com.chouchong.service.iwant;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;

/**
 * @author yy
 * @date 2018/7/9
 **/
public interface AppUserService {
    /**
     * 获得APP用户列表
     *
     * @param: [page 分页信息, search 查询条件]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    Response getAppUserList(PageQuery page, String search);

    /**
     * 通过电话查询APP用户
     *
     * @param: [page, phone]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    Response getAppUserByPhone(PageQuery page, String phone);

    /**
     * 改变APP用户状态
     *
     * @param: [id APP用户id, status APP用户状态]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    Response changeStatus(Integer id, Integer status);
}
