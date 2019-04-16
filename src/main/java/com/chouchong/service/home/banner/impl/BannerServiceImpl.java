package com.chouchong.service.home.banner.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.gift.item.ItemMapper;
import com.chouchong.dao.gift.themeItem.ThemeMapper;
import com.chouchong.dao.home.banner.BannerMapper;
import com.chouchong.entity.gift.item.Item;
import com.chouchong.entity.gift.themeItem.Theme;
import com.chouchong.entity.home.banner.Banner;
import com.chouchong.service.home.banner.BannerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yy
 * @date 2018/7/12
 **/

@Service
public class BannerServiceImpl implements BannerService{
    @Autowired
    private BannerMapper bannerMapper;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private ThemeMapper themeMapper;

    /**
     * 获得banner列表
     *
     * @param: [page 分页信息, search 查询条件]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @Override
    public Response getBannerList(PageQuery page, String search) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        JSONObject jsonObject = JSON.parseObject(search);
        Map map = new HashMap();
        map.put("title",jsonObject.getString("title"));
        map.put("type",jsonObject.getInteger("type"));
        map.put("status",jsonObject.getInteger("status"));
        List<Banner> banners = bannerMapper.selectBySearch(map);
        PageInfo pageInfo = new PageInfo<>(banners);
        return ResponseFactory.page(banners, pageInfo.getTotal(),
                pageInfo.getPages(), pageInfo.getPageNum(), pageInfo.getPageSize());
    }

    /**
     * 添加banner
     *
     * @param: [banner 横幅信息]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @Override
    public Response addBanner(Banner banner) {
        banner.setStatus((byte)1);
        banner.setCreated(new Date());
        banner.setUpdated(new Date());
        if (verifyItem(banner) == false) {
            return ResponseFactory.err("商品id不存在");
        }
        if (verifyTheme(banner) == false) {
            return ResponseFactory.err("主题id不存在");
        }
        int count = bannerMapper.insert(banner);
        if (count == 1) {
            return ResponseFactory.sucMsg("添加成功");
        }
        return ResponseFactory.err("添加失败");
    }

    /**
     * 获得banner详情
     *
     * @param: [id 横幅id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @Override
    public Response getBannerDetail(Integer id) {
        Banner banner = bannerMapper.selectByPrimaryKey(id);
        if (banner == null) {
            return ResponseFactory.err("无此横幅");
        }
        return ResponseFactory.sucData(banner);
    }

    /**
     * 修改banner
     *
     * @param: [banner 横幅]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @Override
    public Response updateBanner(Banner banner) {
        Banner banner1 = bannerMapper.selectByPrimaryKey(banner.getId());
        if (banner1 == null) {
            return ResponseFactory.err("无此横幅");
        }
        if (verifyItem(banner) == false) {
            return ResponseFactory.err("商品id不存在");
        }
        if (verifyTheme(banner) == false) {
            return ResponseFactory.err("主题id不存在");
        }
        banner1.setUpdated(new Date());
        banner1.setTargetId(banner.getTargetId());
        banner1.setType(banner.getType());
        banner1.setRequestUrl(banner.getRequestUrl());
        banner1.setCover(banner.getCover());
        banner1.setTitle(banner.getTitle());
        int count = bannerMapper.updateByPrimaryKey(banner1);
        if (count == 1) {
            return ResponseFactory.sucMsg("修改成功");
        }
        return ResponseFactory.err("修改失败");
    }

    /**
     * 改变banner状态
     *
     * @param: [id 横幅id, status 横幅状态]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @Override
    public Response changeStatus(Integer id, Integer status) {
        Banner banner = bannerMapper.selectByPrimaryKey(id);
        if (banner == null) {
            return ResponseFactory.err("无此横幅");
        }
        banner.setUpdated(new Date());
        banner.setStatus(status.byteValue());
        bannerMapper.updateByPrimaryKey(banner);
        int count = bannerMapper.updateByPrimaryKey(banner);
        if (count == 1) {
            return ResponseFactory.sucMsg("设置成功");
        }
        return ResponseFactory.err("设置失败");
    }

    /**
     * 删除横幅
     *
     * @param: [id 横幅id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @Override
    public Response delBanner(Integer id) {
        Banner banner = bannerMapper.selectByPrimaryKey(id);
        if (banner == null) {
            return ResponseFactory.err("无此横幅");
        }
        banner.setUpdated(new Date());
        banner.setStatus((byte)3);
        bannerMapper.updateByPrimaryKey(banner);
        int count = bannerMapper.updateByPrimaryKey(banner);
        if (count == 1) {
            return ResponseFactory.sucMsg("删除成功");
        }
        return ResponseFactory.err("删除失败");
    }

    /**
     * 验证商品是否存在
     *
     * @param: [banner]
     * @return: boolean
     * @author: yy
     * @Date: 2018/7/12
     */
    private boolean verifyItem(Banner banner) {
        if (banner.getType() == (byte)1) {
            if (banner.getTargetId() != null) {
                Item item = itemMapper.selectByPrimaryKey(Integer.parseInt(banner.getTargetId()));
                if (item == null) {
                    return false;
                }
                if (item.getStatus() == (byte)3) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * 验证主题是否存在
     *
     * @param: [banner]
     * @return: boolean
     * @author: yy
     * @Date: 2018/7/12
     */
    private boolean verifyTheme(Banner banner) {
        if (banner.getType() == (byte)2) {
            if (banner.getTargetId() != null) {
                Theme theme = themeMapper.selectByPrimaryKey(Integer.parseInt(banner.getTargetId()));
                if (theme == null) {
                    return false;
                }
                if (theme.getStatus() == (byte)3) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }
}
