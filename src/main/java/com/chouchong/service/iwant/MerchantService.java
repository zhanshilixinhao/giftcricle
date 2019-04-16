package com.chouchong.service.iwant;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;

public interface MerchantService {
    /**
     * 获得商铺列表
     *
     * @param: [page 分页信息, search 查询条件]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    Response GetMerchantList(PageQuery page, String search);

    /**
     * 审核通过
     *
     * @param: [id 商家id, status 商家状态, username 后台用户名]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    Response changePassStatus(Integer id, Integer status, String username);

    /**
     * 审核失败
     *
     * @param: [id 商家id, status 商家状态]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    Response changeFailStatus(Integer id, Integer status);
}
