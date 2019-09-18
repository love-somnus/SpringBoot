package com.somnus.springboot.filter;

import com.somnus.springboot.ApplicationContextHolder;
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
 * @date 2018/12/5 20:13
 */
@WebFilter(filterName = "ContextFilter2nd",urlPatterns = "/*")
public class ContextFilter2nd extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("第二种方式获取Spring中的bean:" + ApplicationContextHolder.getBean("loveController"));
        filterChain.doFilter(request, response);
    }
}
