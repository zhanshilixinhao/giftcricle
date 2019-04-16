package com.chouchong.dao.home.banner;

import com.chouchong.entity.home.banner.Banner;

import java.util.List;
import java.util.Map;

public interface BannerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Banner record);

    int insertSelective(Banner record);

    Banner selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Banner record);

    int updateByPrimaryKeyWithBLOBs(Banner record);

    int updateByPrimaryKey(Banner record);

    /**
     * 根据条件查询banner列表
     *
     * @param: [map 查询条件]
     * @return: java.util.List<com.chouchong.entity.home.banner.Banner>
     * @author: yy
     * @Date: 2018/7/12
     */
    List<Banner> selectBySearch(Map map);
}