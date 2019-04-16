package com.chouchong.controller.iwant;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.service.iwant.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yy
 * @date 2018/7/6
 **/

@RestController
@RequestMapping("manage/merchant")
public class MerchantController {
    @Autowired
    private MerchantService merchantService;

    /**
     * 获得商铺列表
     *
     * @param: [page 分页信息, search 查询条件]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @PostMapping("list")
    public Response GetMerchantList(PageQuery page, String search) {
        return merchantService.GetMerchantList(page, search);
    }

    /**
     * 审核通过
     *
     * @param: [id 商家id, status 商家状态, username 后台用户名]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @PostMapping("pass")
    public Response changePassStatus(Integer id, Integer status, String username) {
        if (id == null || status == null || username == null) {
            return ResponseFactory.errMissingParameter();
        }
        return merchantService.changePassStatus(id, status, username);
    }

    /**
     * 审核失败
     *
     * @param: [id 商家id, status 商家状态]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @PostMapping("fail")
    public Response changeFailStatus(Integer id, Integer status) {
        if (id == null || status == null) {
            return ResponseFactory.errMissingParameter();
        }
        return merchantService.changeFailStatus(id, status);
    }
}
