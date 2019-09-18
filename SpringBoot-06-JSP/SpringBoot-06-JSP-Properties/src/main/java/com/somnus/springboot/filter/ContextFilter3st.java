package com.somnus.springboot.filter;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

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
 * @date 2018/12/5 20:14
 */
@WebFilter(filterName = "ContextFilter3st",urlPatterns = "/*")
public class ContextFilter3st extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
        System.out.println("第三种方式获取Spring中的bean:" + ctx.getBean("loveController"));
        filterChain.doFilter(request, response);
    }
}
