package com.chouchong.service.home.banner;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.entity.home.banner.Banner;

public interface BannerService {
    /**
     * 获得banner列表
     *
     * @param: [page 分页信息, search 查询条件]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    Response getBannerList(PageQuery page, String search);

    /**
     * 添加banner
     *
     * @param: [banner 横幅信息]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    Response addBanner(Banner banner);

    /**
     * 获得banner详情
     *
     * @param: [id 横幅id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    Response getBannerDetail(Integer id);

    /**
     * 修改banner
     *
     * @param: [banner 横幅]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    Response updateBanner(Banner banner);

    /**
     * 改变banner状态
     *
     * @param: [id 横幅id, status 横幅状态]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    Response changeStatus(Integer id, Integer status);

    /**
     * 删除横幅
     *
     * @param: [id 横幅id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    Response delBanner(Integer id);
}
