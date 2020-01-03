package com.chouchong.config;

import com.chouchong.interceptor.RepeatInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author linqin
 * @date 2020/1/3 21:59
 */
@SpringBootConfiguration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private RepeatInterceptor repeatInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(repeatInterceptor);
    }
}
