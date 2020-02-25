package com.chouchong.interceptor;

import com.chouchong.common.ResponseFactory;
import com.chouchong.common.ResponseImpl;
import com.chouchong.common.Utils;
import com.chouchong.redis.MRedisTemplate;
import com.gexin.fastjson.JSON;
import io.lettuce.core.dynamic.annotation.Command;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author linqin
 * @date 2020/1/3 21:32
 */
@Component
@Slf4j
public class RepeatInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private MRedisTemplate mRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String key = Utils.toMD5(request.getParameter("token")+ request.getRequestURI()) ;
        String string = mRedisTemplate.getString(key);
        if (StringUtils.isBlank(string)){
            mRedisTemplate.setString(key,"a",60);
            return true;
        }
        response.setContentType("application/json;charset=UTF-8");
        ResponseImpl s = ResponseFactory.err("请勿重复请求");
        response.getWriter().write(JSON.toJSONString(s));

        return false;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String key = Utils.toMD5(request.getParameter("token")+ request.getRequestURI()) ;
        mRedisTemplate.del(key);
        log.info("afterCompletion{}",request.getRequestURI());
    }
}
