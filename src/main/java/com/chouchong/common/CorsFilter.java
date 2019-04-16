package com.chouchong.common;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yy
 * @date 2018/6/22
 **/
@Component
public class CorsFilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * 跨域问题
     *
     * @param: [req, res, filterChain]
     * @return: void
     * @author: yy
     * @Date: 2018/6/25
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        // System.out.println("*********************************过滤器被使用**************************");
        filterChain.doFilter(req, res);
    }

    @Override
    public void destroy() {

    }
}
