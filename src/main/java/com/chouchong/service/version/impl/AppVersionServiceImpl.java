package com.chouchong.service.version.impl;

import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.home.AppVersionMapper;
import com.chouchong.entity.home.AppVersion;
import com.chouchong.service.version.AppVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author linqin
 * @date 2019/4/16
 */
@Service
public class AppVersionServiceImpl implements AppVersionService {

    @Autowired
    private AppVersionMapper appVersionMapper;

    /**
     * 获取apk列表
     * @return
     * @author linqin
     * @date 2019/4/16
     */
    @Override
    public Response getApkList() {
        AppVersion appVersion = appVersionMapper.selectAllList();
        return ResponseFactory.sucData(appVersion);
    }


    /**
     * 上传apk
     *
     * @param appVersion
     * @return
     * @author linqin
     * @date 2019/4/16
     */
    @Override
    public Response uploadApk(AppVersion appVersion) {
        appVersionMapper.deleteByAll();
        appVersion.setStatus((byte)1);
        int insert = appVersionMapper.insert(appVersion);
        if (insert < 1){
            return ResponseFactory.err("上传失败");
        }
        return ResponseFactory.sucMsg("上传成功");
    }



}
