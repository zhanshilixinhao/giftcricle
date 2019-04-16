package com.chouchong.common;

/**
 * 响应对象简单工厂
 *
 * @author yichenshanren
 * @date 2017/9/28
 */

public class ResponseFactory {

    public static Response suc() {
        return new ResponseImpl(ErrorCode.SUCCESS.getCode());
    }

    public static ResponseImpl sucMsg(String msg) {
        return new ResponseImpl(ErrorCode.SUCCESS.getCode(), msg);
    }

    public static <T> Response<T> sucData(T data) {
        return new ResponseImpl<>(ErrorCode.SUCCESS.getCode(), data);
    }

    public static <T> Response<T> sucDataWithImgHost(T data) {
        return new ResponseImpl<>(ErrorCode.SUCCESS.getCode(), data, Constants.IMG_HOST);
    }

    public static <T> Response<T> suc(String msg, T data) {
        return new ResponseImpl<>(ErrorCode.SUCCESS.getCode(), msg, data);
    }

    public static <T> Response<T> sucWithImgHost(String msg, T data) {
        return new ResponseImpl<>(ErrorCode.SUCCESS.getCode(), msg, data, Constants.IMG_HOST);
    }

    public static <T> Response<T> sucData(T data, String imgHost) {
        return new ResponseImpl<>(ErrorCode.SUCCESS.getCode(), data, imgHost);
    }


    public static <T> Response<T> suc(String msg, T data, String imgHost) {
        return new ResponseImpl<>(ErrorCode.SUCCESS.getCode(), msg, data, imgHost);
    }

    /**
     * 创建错误响应对象
     *
     * @param errCode 错误码
     * @param msg     错误信息
     * @return
     */
    public static Response errMsg(int errCode, String msg) {
        return new ResponseImpl(errCode, msg);
    }

    public static Response errMsgPath(int errCode, String msg, String path) {
        return new ResponseImpl(errCode, msg, path);
    }

    /**
     * 创建错误响应对象
     *
     * @param msg 错误信息
     * @return
     */
    public static ResponseImpl err(String msg) {
        return new ResponseImpl(ErrorCode.ERROR.getCode(), msg);
    }

    /**
     * 需要登录的响应对象
     *
     * @return
     */
    public static Response errNeedLogin() {
        return new ResponseImpl(ErrorCode.NEED_LOGIN.getCode(), ErrorCode.NEED_LOGIN.getMsg());
    }

    public static Response errNeedLogin(String msg) {
        return new ResponseImpl(ErrorCode.NEED_LOGIN.getCode(), msg);
    }

    /**
     * 权限错误时的响应对象
     *
     * @return
     */
    public static Response errPermissionDenied() {
        return new ResponseImpl(ErrorCode.PERMISSION_DENIED.getCode(), ErrorCode.PERMISSION_DENIED.getMsg());
    }

    public static Response errNeedBindPhone() {
        return new ResponseImpl(ErrorCode.NEED_BIND_PHONE.getCode());
    }

    public static Response errPermissionDenied(String msg) {
        return new ResponseImpl(ErrorCode.PERMISSION_DENIED.getCode(), msg);
    }

    /**
     * 创建参数缺少时的 错误响应对象
     *
     * @return
     */
    public static Response errMissingParameter() {
        return new ResponseImpl(ErrorCode.MISSING_PARAMETER.getCode(), ErrorCode.MISSING_PARAMETER.getMsg());
    }

    public static Response errMissingParameter(String msg) {
        return new ResponseImpl(ErrorCode.MISSING_PARAMETER.getCode(), ErrorCode.MISSING_PARAMETER.getMsg() + msg);
    }

    /**
     * 创建分页响应对象
     *
     * @param data     分页列表
     * @param total    数据总条数
     * @param pages    数据总页数
     * @param pageNum  页码
     * @param pageSize 页大小
     * @param <T>
     * @return
     */
    public static <T> Response<T> page(T data, long total, int pages, int pageNum, int pageSize) {
        return new PageResponse<>(ErrorCode.SUCCESS.getCode(), data, total, pages, pageNum, pageSize);
    }

    public static <T> Response<T> pageWithImgHost(T data, long total, int pages, int pageNum, int pageSize) {
        return new PageResponse<>(ErrorCode.SUCCESS.getCode(), data, total, pages, pageNum, pageSize, Constants.IMG_HOST);
    }

    public static <T> Response<T> page(T data, long total, int pages, int pageNum, int pageSize, String imgHost) {
        return new PageResponse<>(ErrorCode.SUCCESS.getCode(), data, total, pages, pageNum, pageSize, imgHost);
    }

    /**
     * 创建响应对象，签名错误
     *
     * @param msg 错误信息
     * @return 相应对象
     */
    public static Response errSign(String msg) {
        return new ResponseImpl(ErrorCode.SIGN_ERROR.getCode(), msg);
    }

//    public static Response errData(int errCode, String msg, String key) {
//        return new ResponseImpl(errCode, msg, key);
//    }

    public static Response errData(int errCode, String msg, Object data) {
        return new ResponseImpl(errCode, msg, data);
    }

    public static ResponseImpl create(int errCode, String msg, Object data) {
        return new ResponseImpl<>(errCode, msg, data);
    }
}
