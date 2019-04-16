package com.chouchong.controller.home.banner;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.entity.home.banner.Banner;
import com.chouchong.service.home.banner.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yy
 * @date 2018/7/12
 **/

@RestController
@RequestMapping("manage/banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;

    /**
     * 获得banner列表
     *
     * @param: [page 分页信息, search 查询条件]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @RequestMapping("list")
    public Response getBannerList(PageQuery page, String search) {
        return bannerService.getBannerList(page, search);
    }

    /**
     * 添加banner
     *
     * @param: [banner 横幅信息]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @RequestMapping("add")
    public Response addBanner(Banner banner) {
        if (banner == null) {
            return ResponseFactory.errMissingParameter();
        }
        if (banner.getCover() == null || banner.getType() == null || banner.getTitle() == null) {
            return ResponseFactory.errMissingParameter();
        }
        return bannerService.addBanner(banner);
    }

    /**
     * 获得banner详情
     *
     * @param: [id 横幅id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @RequestMapping("detail")
    public Response getBannerDetail(Integer id) {
        if (id == null) {
            return ResponseFactory.errMissingParameter();
        }
        return bannerService.getBannerDetail(id);
    }

    /**
     * 修改banner
     *
     * @param: [banner 横幅]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @RequestMapping("update")
    public Response updateBanner(Banner banner) {
        if (banner == null) {
            return ResponseFactory.errMissingParameter();
        }
        if (banner.getCover() == null || banner.getType() == null || banner.getTitle() == null || banner.getId() == null) {
            return ResponseFactory.errMissingParameter();
        }
        return bannerService.updateBanner(banner);
    }

    /**
     * 改变banner状态
     *
     * @param: [id 横幅id, status 横幅状态]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @RequestMapping("status")
    public Response changeStatus(Integer id, Integer status) {
        if (id == null || status == null) {
            return ResponseFactory.errMissingParameter();
        }
        return bannerService.changeStatus(id, status);
    }

    /**
     * 删除横幅
     *
     * @param: [id 横幅id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @RequestMapping("del")
    public Response delBanner(Integer id) {
        if (id == null) {
            return ResponseFactory.errMissingParameter();
        }
        return bannerService.delBanner(id);
    }
}
