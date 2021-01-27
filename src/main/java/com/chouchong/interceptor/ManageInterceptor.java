package com.chouchong.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.chouchong.common.Constants;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.webUser.SysFunctionPermissionMapper;
import com.chouchong.entity.webUser.SysAdmin;
import com.chouchong.entity.webUser.SysFunctionPermission;
import com.chouchong.redis.MRedisTemplate;
import com.chouchong.service.webUser.vo.WebUserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author yy
 * @date 2018/4/28
 **/
@Component
public class ManageInterceptor extends OncePerRequestFilter {
     private static final Logger log = LoggerFactory.getLogger(ManageInterceptor.class);
    @Autowired
    private MRedisTemplate mRedisTemplate;
    @Autowired
    private SysFunctionPermissionMapper sysFunctionPermissionMapper;

    private static HashSet<String> hashSet = new HashSet<>();

    static {
        hashSet.add("/manage/user/login");
        hashSet.add("/manage/user/logout");
        hashSet.add("/manage/user/pas");
        hashSet.add("/manage/user/info");
//        hashSet.add("/manage/ask/code");
        /*hashSet.add("/manage/item/detail");*/
        // 不拦截图片上传，会不会有问题？
        hashSet.add("/manage/upload/image");
        //hashSet.add("/manage/itemSku/isGroup");

    }

    private static String url = "/manage/order/item/orderCount";

    private static String urls = "/noauth/manage";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // if (!request.getRequestURI().contains("/manage/user/login")) {
        if (!hashSet.contains(request.getRequestURI())) {
            String token = request.getParameter("token");
            if (token == null) {
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write(JSON.toJSONString(ResponseFactory.errNeedLogin(Constants.USER_TOKEN_ERROR)));
                return;
            }
            // 查询是否返回了权限id
            int permissionId = getPermissionId(request);
            // 根据token取出用户信息
            WebUserInfo webUserInfo = mRedisTemplate.get(token, new TypeReference<WebUserInfo>() {
            });
            // 判断用户缓存是否存在
            if (webUserInfo == null) {
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write(JSON.toJSONString(ResponseFactory.errNeedLogin(Constants.USER_TOKEN_ERROR)));
                return;
            }
            request.setAttribute("user", webUserInfo);
            SysAdmin sysAdmin = webUserInfo.getSysAdmin();
            if (sysAdmin == null) {
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write(JSON.toJSONString(ResponseFactory.errNeedLogin(Constants.USER_TOKEN_ERROR)));
                return;
            }
            // List<String> sysFunctionPermissions =  webUserInfo.getPermissions();
            // 判断该用户是否有权限集合
            if (request.getRequestURI().contains(url)){
                filterChain.doFilter(request,response);
                return;
            }
            List<SysFunctionPermission> permissionList = webUserInfo.getPermissionList();
            if (permissionList == null || permissionList.size() == 0) {
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write(JSON.toJSONString(ResponseFactory.errPermissionDenied("您无权限访问此页面")));
                return;
            }
            /**遍历权限**/
            /*for (String sysFunctionPermission : sysFunctionPermissions) {
                *//**权限匹配**//*
                if (request.getRequestURI().equals("/" + sysFunctionPermission)) {
                    System.out.println("sysFunctionPermission = " + sysFunctionPermission);
                    *//**如果当前URL在权限集合中存在则校验通过**//*
                    filterChain.doFilter(request, response);
                    return;
                }
            }*/
            for (SysFunctionPermission sysFunctionPermission : permissionList) {
                /**权限匹配**/
                /**获取权限的URL地址是否有权限id**/
                String str = sysFunctionPermission.getUrl();
                String[] strs = str.split(":");

                // 如果有权限id 查询是否与前端的权限匹配
                if (strs.length == 2 && permissionId != 0) {
                    try {
                        if (Integer.parseInt(strs[1]) == permissionId) {
                            filterChain.doFilter(request, response);
                            return;
                        }
                    } catch (Exception e) {
                        filterChain.doFilter(request, response);
                        return;
                    }
                } else {
                    if (permissionId == 0) {
                        if (request.getRequestURI().equals("/" + str)) {
                            /**如果当前URL在权限集合中存在则校验通过**/
                            filterChain.doFilter(request, response);
                            return;
                        }
                    }
                }

            }
            // 通过地址找到权限
            SysFunctionPermission permission = null;
            if (permissionId != 0) {
                 permission = sysFunctionPermissionMapper.
                        selectByUrl(request.getRequestURI().substring(1, request.getRequestURI().length())+":" + permissionId);
            } else {
                 permission = sysFunctionPermissionMapper.
                        selectByUrl(request.getRequestURI().substring(1, request.getRequestURI().length()));
            }
            if (permission == null) {
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write(JSON.toJSONString(ResponseFactory.errPermissionDenied("无此权限")));
                return;
            } else {
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write(JSON.toJSONString(ResponseFactory.errPermissionDenied("您没有" + permission.getName() + "的权限")));
                return;
            }
        }  filterChain.doFilter(request, response);
    }

    /**
     * 得到接口的权限id
     *
     * @param: [request]
     * @return: int
     * @author: yy
     * @Date: 2018/7/20
     */
    private int getPermissionId(HttpServletRequest request) {
        try {
            Map<String, String> m = new HashMap<String, String>();
            if (request.getQueryString()== null) {
                return 0;
            } else {
                String str = request.getQueryString();
                String[] strs = str.split("&");
                for(String s:strs){
                    String[] ms = s.split("=");
                    m.put(ms[0], ms[1]);
                }
                return Integer.parseInt(m.get("sfpId"));
            }
        } catch (Exception e) {
            return 0;
        }
    }
}
