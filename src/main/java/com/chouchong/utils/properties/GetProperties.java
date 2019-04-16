package com.chouchong.utils.properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @author yy
 * @date 2017/12/15
 **/
public class GetProperties {
    private static final Logger LOG = Logger.getLogger(GetProperties.class);

    private static Properties properties;

    static {
        load();
    }

    private static void load() {
        String filename = "file.properties";
        properties = new Properties();
        try {
            properties.load(new InputStreamReader(
                    GetProperties.class.getClassLoader().getResourceAsStream(filename)
                    , "UTF-8"));
        } catch (IOException e) {
            LOG.error("配置文件读取异常", e);
        }
    }

    public static String getProperty(String key) {
        return getProperty(key, null);
    }

    public static String getProperty(String key, String defaultValue) {
        String value = properties.getProperty(key.trim());
        if (StringUtils.isBlank(value)) {
            return defaultValue;
        }
        return value.trim();
    }

    /**
     * 获取图片服务器的地址
     *
     * @return 图片服务器的地址
     */
    public static String getImageHost() {
        return getProperty("image.url");
    }

    public static String getWebUrl() {
        return getProperty("static.web.url");
    }

    public static String getServerUrl() {
        return getProperty("server.url");
    }

    /**
     * 获取图片保存的地址
     *
     * @return
     */
    public static String getImageSavePath() {
        return getProperty("image.save.path");
    }
}
