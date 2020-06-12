package com.chouchong.common;

/**
 * @author yichenshanren
 * @date 2017/12/5
 */

public class Constants {

    public static final String IMG_HOST = "https://yoooka.cn/";
    public static final String ADMINPWD = "43./;'rewff434rlrfFDSFDedsrfSfds3243sDSkehgdjrfke432dsewDS,mcfds,";
    public static final String STOREPWD = "storepassword";

    public static final String USER_TOKEN_ERROR = "登录信息无效或已过期,请重新登录!";

    /*商家审核状态*/
    public interface MERCHANT_STATUS {
        byte NORMAL = 1;//默认
        byte IN_REVIEW = 2;//审核中
        byte PASS = 3; // 审核通过
        byte NO_PASS = 4; //审核不通过
    }
}
