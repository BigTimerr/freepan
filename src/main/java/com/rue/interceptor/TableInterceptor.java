package com.rue.interceptor;

import com.rue.bean.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 查看是否是管理员,不是管理员无法查看用户表
 *
 *
 * @author ruetrash*/
@Slf4j
public class TableInterceptor implements HandlerInterceptor {
    /**
     * 目标方法执行之前
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        log.info("TableInterceptor拦截的路径是{}", requestURI);

        HttpSession session = request.getSession();
        Cookie[] cookies = request.getCookies();
        User loginUser = (User)session.getAttribute("loginUser");
        if ("admin".equals(loginUser.getUsername()) ){
            return true;
        }
        response.sendRedirect("/main.html");
        return false;
    }



    /**
     * 目标方法执行之后
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
