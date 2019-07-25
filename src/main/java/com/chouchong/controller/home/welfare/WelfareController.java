package com.chouchong.controller.home.welfare;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.entity.home.Welfare;
import com.chouchong.service.home.welfare.WelfareService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author linqin
 * @date 2019/2/22 15:07
 */
@RestController
@RequestMapping("manage/welfare")
public class WelfareController {

    @Autowired
    private WelfareService welfareService;

    /**
     * 获取福利列表
     *
     * @param page
     * @param search
     * @return
     * @author linqin
     * @date 2019/2/22
     */
    @PostMapping("list")
    public Response getWelfareList(PageQuery page, Welfare welfare) {
        return welfareService.getWelfareList(page, welfare);
    }

    /**
     * 添加福利
     *
     * @param welfare
     * @return
     * @author linqin
     * @date 2019/2/22
     */
    @PostMapping("add")
    public Response addWelfare(Welfare welfare) {
        if (welfare == null) {
            return ResponseFactory.errMissingParameter();
        }
        if (StringUtils.isBlank(welfare.getTitle()) || welfare.getType() == null ||
                welfare.getTargetId() == null || welfare.getQuantity() == null || welfare.getTargetDate() == null ||
                welfare.getStartTime() == null || welfare.getEndTime() == null || welfare.getIsCode() == null) {
            return ResponseFactory.errMissingParameter();
        }
        return welfareService.addWelfare(welfare);
    }

    /**
     * 修改福利
     *
     * @param welfare
     * @return
     * @author linqin
     * @date 2019/2/22
     */
    @PostMapping("update")
    public Response updateWelfare(Welfare welfare) {
        if (welfare == null) {
            return ResponseFactory.errMissingParameter();
        }
        if (StringUtils.isAnyBlank(welfare.getTitle()) || welfare.getId() == null || welfare.getType() == null ||
                welfare.getTargetId() == null || welfare.getQuantity() == null || welfare.getTargetDate() == null ||
                welfare.getStartTime() == null || welfare.getEndTime() == null|| welfare.getIsCode() == null) {
            return ResponseFactory.errMissingParameter();
        }
        return welfareService.updateWelfare(welfare);
    }

    /**
     * 删除福利
     *
     * @param welfareId 福利id
     * @return
     * @author linqin
     * @date 2019/2/22
     */
    @PostMapping("delete")
    public Response deleteWelfare(Integer welfareId) {
        if (welfareId == null) {
            return ResponseFactory.errMissingParameter();
        }
        return welfareService.deleteWelfare(welfareId);
    }

    /**
     * 详情福利
     *
     * @param welfareId 福利id
     * @return
     * @author linqin
     * @date 2019/2/22
     */
    @PostMapping("detail")
    public Response detailWelfare(Integer welfareId) {
        if (welfareId == null) {
            return ResponseFactory.errMissingParameter();
        }
        return welfareService.detailWelfare(welfareId);
    }


    /**
     * 福利物品列表
     *
     * @param type
     * @return
     * @author linqin
     * @date 2019/2/22
     */
    @PostMapping("item_list")
    public Response getAllItemList(Byte type, PageQuery page, String title) {
        if (type == null || type <= 0 || type > 3) {
            return ResponseFactory.errMissingParameter();
        }
        return welfareService.getAllItemList(type, page, title);
    }

    /**
     * 商品sku列表
     * @param itemId 商品id
     * @return
     * @author linqin
     * @date 2019/2/22
     */
    @PostMapping("sku_list")
    public Response getSkuList(Integer itemId) {
        if (itemId == null){
            return ResponseFactory.errMissingParameter();
        }
        return welfareService.getSkuList(itemId);
    }


}
