package com.chouchong.controller.v4;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.entity.v4.RebateCouponManage;
import com.chouchong.service.v4.RebateCouponManageService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description:
 * @Author Lxh
 * @Date 2020/12/30 9:38
 */
@RestController
@RequestMapping("manage/v4/RebateCoupon")
public class RebateCouponManageController {

    @Resource
    private RebateCouponManageService rebateCouponManageService;

    /**
     * @Description: 折扣券查询
     * @Author: LxH
     * @Date: 2020/12/30 10:27
     */
    @RequestMapping("list")
    public Response getList(String title , PageQuery page){
        return rebateCouponManageService.getList(title,page);
    }

    /**
     * @Description: 后台添加折扣券
     * @Author: LxH
     * @Date: 2020/12/30 9:43
     */
    @RequestMapping("addRebate")
    public Response addRebateCouponManage(RebateCouponManage rebateCouponManage){
        if (rebateCouponManage.getStoreIds()==null ||
                rebateCouponManage.getRebateNew()==null ||
                rebateCouponManage.getRebateOld()==null) {
            return ResponseFactory.err("参数不能为空");
        }
        return rebateCouponManageService.addRebateCouponManage(rebateCouponManage);
    }

    /**
     * @Description: 后台修改折扣券
     * @Author: LxH
     * @Date: 2020/12/30 9:59
     */
    @RequestMapping("updateRebate")
    public Response updateRebateCouponManage(RebateCouponManage rebateCouponManage){
        if (rebateCouponManage.getStoreIds()==null ||
                rebateCouponManage.getRebateNew()==null ||
                rebateCouponManage.getRebateOld()==null) {
            return ResponseFactory.err("参数不能为空");
        }
        return rebateCouponManageService.updateRebateCouponManage(rebateCouponManage);
    }

    /**
     * @Description: 后台删除折扣券
     * @Author: LxH
     * @Date: 2020/12/30 10:19
     */
    @RequestMapping("deleteRebate")
    public Response deleteRebateCouponManage(Integer id){
        if (id == null) {
            return ResponseFactory.err("参数不能为空");
        }
        return rebateCouponManageService.deleteRebateCouponManage(id);
    }
}
