package com.hisoft.web.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 解决post请求中文乱码的filter
 */
@WebFilter("/*")
public class CharacterFilter implements Filter {

    private Logger logger = Logger.getLogger(CharacterFilter.class);

    @Override
    public void destroy() {
        logger.info("CharacterFilter销毁");
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("CharacterFilter初始化成功");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        servletResponse.setContentType("text/html;charset=utf-8");
        servletResponse.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
