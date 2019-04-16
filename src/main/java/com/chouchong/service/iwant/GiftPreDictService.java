package com.chouchong.service.iwant;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;

public interface GiftPreDictService {
    /**
     * 获得礼物偏好列表
     *
     * @param: [page 分页信息, name 礼物偏好名称]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/11
     */
    Response getGiftPreList(PageQuery page, String name);

    /**
     * 添加礼物偏好
     *
     * @param: [name 礼物偏好名称]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/11
     */
    Response addGiftPre(String name);

    /**
     * 修改礼物偏好
     *
     * @param: [id 礼物偏好id, name 礼物偏好名称]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    Response updateGiftPre(Integer id, String name);

    /**
     * 删除礼物偏好
     *
     * @param: [id 礼物偏好id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    Response delGiftPre(Integer id);
}
