package com.chouchong.service.home.welfare;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.entity.home.Welfare;

/**
 * @author linqin
 * @date 2019/2/22 15:07
 */

public interface WelfareService {

    /**
     * 获取福利列表
     *
     * @param page
     * @param search
     * @return
     * @author linqin
     * @date 2019/2/22
     */
    Response getWelfareList(PageQuery page,Welfare welfare);

    /**
     * 添加福利
     * @param welfare
     * @return
     * @author linqin
     * @date 2019/2/22
     */
    Response addWelfare(Welfare welfare);

    /**
     * 添加福利
     * @param welfare
     * @return
     * @author linqin
     * @date 2019/2/22
     */
    Response updateWelfare(Welfare welfare);
    /**
     * 删除福利
     * @param welfareId 福利id
     * @return
     * @author linqin
     * @date 2019/2/22
     */
    Response deleteWelfare(Integer welfareId);

    /**
     * 福利物品列表
     *
     * @param type
     * @return
     * @author linqin
     * @date 2019/2/22
     */
    Response getAllItemList(Byte type,PageQuery page,String title);

    /**
     * 商品sku列表
     * @param itemId 商品id
     * @return
     * @author linqin
     * @date 2019/2/22
     */
    Response getSkuList(Integer itemId);

    /**
     * 详情福利
     *
     * @param welfareId 福利id
     * @return
     * @author linqin
     * @date 2019/2/22
     */
    Response detailWelfare(Integer welfareId);
}
