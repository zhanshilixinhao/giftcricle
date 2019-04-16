package com.chouchong.controller.upload;

import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.entity.home.AppVersion;
import com.chouchong.service.version.AppVersionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author linqin
 * @date 2019/4/16
 */
@RestController
@RequestMapping("manage/version")
public class AppVersionController {

    @Autowired
    private AppVersionService appVersionService;

    /**
     * 上传apk
     *
     * @param appVersion
     * @return
     * @author linqin
     * @date 2019/4/16
     */
    @PostMapping("apk")
    public Response uploadApk(AppVersion appVersion) {
        if (appVersion.getType() == null){
            return ResponseFactory.errMissingParameter();
        }
        if (StringUtils.isAnyBlank(appVersion.getVersionName(),appVersion.getApkUrl(),appVersion.getUpgradePoint(),appVersion.getVersionCode())){
            return ResponseFactory.errMissingParameter();
        }
        return appVersionService.uploadApk(appVersion);
    }


    /**
     * 获取apk列表
     * @return
     * @author linqin
     * @date 2019/4/16
     */
   @PostMapping("list")
    public Response getApkList() {
        return appVersionService.getApkList();
    }


}
