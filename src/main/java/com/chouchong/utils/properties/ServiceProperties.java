package com.chouchong.utils.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 优咖 业务相关属性
 *
 * @author yichenshanren
 * @date 2017/12/4
 */
@Component
@ConfigurationProperties(prefix = "yichen.service")
public class ServiceProperties {

    // 后台管理员头像地址
    private String avatar;

    private String host = "https://api.goexplore.io";

    private String productDetail;

    private String articleDetail;

    private String wxUrl = "noauth/pay/wx";

    private String aliUrl = "noauth/pay/aliv2";

    public String getArticleDetail() {
        return articleDetail;
    }

    public void setArticleDetail(String articleDetail) {
        this.articleDetail = articleDetail;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getWxUrl() {
        return wxUrl;
    }

    public void setWxUrl(String wxUrl) {
        this.wxUrl = wxUrl;
    }

    public String getAliUrl() {
        return aliUrl;
    }

    public void setAliUrl(String aliUrl) {
        this.aliUrl = aliUrl;
    }

    public String getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(String productDetail) {
        this.productDetail = productDetail;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
