package com.chouchong.controller.v4;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.entity.v4.ElCouponList;
import com.chouchong.service.v4.PartyPackService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Description: 礼包控制层
 * @Author Lxh
 * @Date 2020/9/30 15:00
 */
@RestController
@RequestMapping("manage/v4/partyPack")
public class PartyPackController {

    @Resource
    private PartyPackService partyPackService;

    /**
     * @Description: 后台添加大礼包
     * @Author: LxH
     * @Date: 2020/9/30 16:44
     */
    @PostMapping("addPartyPack")
    public Response addPartyPack(ElCouponList elCouponList,String couponJson){
        if (couponJson==null || elCouponList.getTitle()==null) {
            return ResponseFactory.errMissingParameter();
        }
        return partyPackService.addPartyPack(elCouponList,couponJson);
    }

    /**
     * @Description: 后台修改大礼包
     * @Author: LxH
     * @Date: 2020/9/30 17:17
     */
    @PostMapping("updatePartyPack")
    public Response updatePartyPack(ElCouponList elCouponList,String couponJson){
        if (couponJson==null || elCouponList.getTitle()==null) {
            return ResponseFactory.errMissingParameter();
        }
        return partyPackService.updatePartyPack(elCouponList,couponJson);
    }

    /**
     * @Description: 后台删除礼包
     * @Author: LxH
     * @Date: 2020/9/30 17:38
     */
    @PostMapping("deletePartyPack")
    public Response deletePartyPack(Integer elCouponListId){
        if (elCouponListId==null) {
            return ResponseFactory.errMissingParameter();
        }
        return partyPackService.deletePartyPack(elCouponListId);
    }

    /**
     * @Description: 可以添加到礼包的优惠券
     * @Author: LxH
     * @Date: 2020/10/6 14:43
     */
    @PostMapping("findCoupons")
    public Response findCoupons(){
        return partyPackService.findCoupons();
    }

    /**
     * @Description: 礼包分页查询
     * @Author: LxH
     * @Date: 2020/10/6 15:06
     */
    @PostMapping("findPartyPackByPage")
    public Response findPartyPackByPage(String title, PageQuery page){
        return partyPackService.findPartyPackByPage(title,page);
    }

    /**  新增 ------------------------------------------------------------------------------------------------------------
     * @Description: 后台赠送用户优惠券礼包
     * @Author: LxH
     * @Date: 2020/10/6 16:10
     */
    @PostMapping("givePartyPack")
    public Response givePartyPack(String phone,String partyPackJson){
        return partyPackService.givePartyPack(phone,partyPackJson);
    }

    /**
     *
     *
     *@description: 后台赠送礼包记录
     *@author: LxH
     *@time: 2020/10/15 0015 上午 10:14
     *
     */
    @PostMapping("partyPackRecord")
    public Response partyPackRecord(PageQuery page, String title, String phone, Integer storeId,
                                    Date startTime, Date endTime){
        return partyPackService.partyPackRecord(page,title,phone,storeId,startTime,endTime);
    }
}
