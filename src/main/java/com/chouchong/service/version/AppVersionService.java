package com.chouchong.service.version;

import com.chouchong.common.Response;
import com.chouchong.entity.home.AppVersion;

/**
 * @author linqin
 * @date 2019/4/16
 */

public interface AppVersionService {
    /**
     * 获取apk列表
     * @return
     * @author linqin
     * @date 2019/4/16
     */
    Response getApkList();

    /**
     * 上传apk
     *
     * @param appVersion
     * @return
     * @author linqin
     * @date 2019/4/16
     */
    Response uploadApk(AppVersion appVersion);
}
