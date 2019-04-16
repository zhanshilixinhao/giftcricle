package com.chouchong.service.statistics.impl;

import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.iwant.appUser.AppUserMapper;
import com.chouchong.service.statistics.UserStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author linqin
 * @date 2018/7/31
 */
@Service
public class UserStatisticsServiceImpl implements UserStatisticsService {

    @Autowired
    private AppUserMapper appUserMapper;

    /**
     * 按天统计用户注册
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     * @author linqin
     * @date 2018/7/31
     */
    @Override
    public Response days(String startTime, String endTime) {
        List<Map> appUser = appUserMapper.selectByCreated(startTime, endTime);
        if (appUser == null) {
            return ResponseFactory.err("还没有用户注册");
        }
        return ResponseFactory.sucData(appUser);
    }


    /**
     * 按月统计用户注册
     *
     * @param endMonth
     * @return
     * @author linqin
     * @date 2018/7/31
     */
    @Override
    public Response month(String startMonth, String endMonth) {
        List<Map> appUsers = appUserMapper.selectByCreatedMonth(startMonth,endMonth);
        if (appUsers == null) {
            return ResponseFactory.err("还没有用户注册");
        }
        return ResponseFactory.sucData(appUsers);
    }



    /**
     * 统计用户注册
     *
     * @return
     * @author linqin
     * @date 2018/7/31
     */
    @Override
    public Response userNumber() {
        List<Map> appUser = appUserMapper.selectAllByCreated();
        if (appUser == null) {
            return ResponseFactory.err("还没有用户注册");
        }
        return ResponseFactory.sucData(appUser);
    }

}
