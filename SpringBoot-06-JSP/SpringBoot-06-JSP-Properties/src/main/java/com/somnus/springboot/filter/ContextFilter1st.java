package com.somnus.springboot.filter;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Somnus
 * @brief
 * @details
 * @date 2018/12/5 20:12
 */
@WebFilter(filterName = "ContextFilter1st",urlPatterns = "/*")
public class ContextFilter1st extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        WebApplicationContext ctx = RequestContextUtils.findWebApplicationContext(request);
        System.out.println("第一种方式获取Spring中的bean:" + ctx.getBean("loveController"));
        filterChain.doFilter(request, response);
    }
}
