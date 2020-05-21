package com.chouchong.config;

import com.chouchong.common.ErrorCode;
import com.chouchong.exception.ServiceException;
import com.gexin.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;
import java.util.Enumeration;
import java.util.UUID;

/**
 * @author linqin
 * @description log拦截器
 * @date 2020/1/20
 */
@Component
@Order(-1)
@Slf4j
public class LogFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String traceId = UUID.randomUUID().toString();
        request.setAttribute("traceId",traceId);
        // 记录下请求内容
        log.info("traceId:{}, URL:{}", traceId, request.getRequestURL().toString());
        log.info("traceId:{},HTTP_METHOD :{}", traceId, request.getMethod());
        Enumeration<String> enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String name = enu.nextElement();
            log.info("traceId:{},{}={}", traceId, name, request.getParameter(name));
        }
        ServletResponseWrapper responseWrapper = new ServletResponseWrapper(response);
        filterChain.doFilter(request, responseWrapper);
        log.info("traceId:{},body:{}", traceId, responseWrapper.getResponseBody());
    }

    public static class MonitorOutoutStream extends ServletOutputStream {

        private ServletOutputStream output;
        private ByteArrayOutputStream copy = new ByteArrayOutputStream();


        public MonitorOutoutStream(ServletOutputStream output) {
            super();
            this.output = output;
        }

        @Override
        public boolean isReady() {
            // TODO Auto-generated method stub
            return output.isReady();
        }

        @Override
        public void setWriteListener(WriteListener arg0) {
            // TODO Auto-generated method stub
            output.setWriteListener(arg0);
        }

        @Override
        public void write(int b) throws IOException {
            // TODO Auto-generated method stub
            output.write(b);
            copy.write(b);
        }


        @Override
        public void write(byte[] b) throws IOException {
            // TODO Auto-generated method stub
            output.write(b);
            copy.write(b);
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            // TODO Auto-generated method stub
            output.write(b, off, len);
            copy.write(b, off, len);
        }

        public byte[] getWroteInfo() {
            return copy.toByteArray();
        }

        @Override
        public void flush() throws IOException {
            // TODO Auto-generated method stub
            output.flush();
            copy.close();
        }

        @Override
        public void close() throws IOException {
            // TODO Auto-generated method stub
            output.close();
            copy.close();
        }


    }

    public static class ServletResponseWrapper extends HttpServletResponseWrapper {

        private volatile MonitorOutoutStream mos;

        @Override
        public PrintWriter getWriter() throws IOException {
            return new ResponsePrintWriter("UTF-8", this);
        }

        public ServletResponseWrapper(HttpServletResponse response) {
            super(response);
            // TODO Auto-generated constructor stub
        }


        @Override
        public ServletOutputStream getOutputStream() throws IOException {
            // TODO Auto-generated method stub
            if (mos == null) {
                synchronized (this) {
                    if (mos == null) {
                        mos = new MonitorOutoutStream(super.getOutputStream());
                    }
                }
            }
            return mos;
        }

        public String getResponseBody() {

            return new String(mos.getWroteInfo());


        }
    }

    private static class ResponsePrintWriter extends PrintWriter {

        public ResponsePrintWriter(String characterEncoding, ServletResponseWrapper responseWrapper) throws IOException {
            super(new OutputStreamWriter(responseWrapper.getOutputStream()));
        }

        public void write(char[] buf, int off, int len) {
            super.write(buf, off, len);
            super.flush();
        }

        public void write(String s, int off, int len) {
            super.write(s, off, len);
            super.flush();
        }

        public void write(int c) {
            super.write(c);
            super.flush();
        }
    }

}
