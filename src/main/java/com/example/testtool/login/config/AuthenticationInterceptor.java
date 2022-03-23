/**
 * Author:   Herewe
 * Date:     2022/3/6 11:49
 * Description: jian quan
 */
package com.example.testtool.login.config;

import com.example.testtool.login.JwtIgnore;
import com.example.testtool.login.util.JWTUtil;
import com.example.testtool.login.util.WebContext;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        if (HttpMethod.OPTIONS.equals(request.getMethod())) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if (method.isAnnotationPresent(JwtIgnore.class)) {
            return true;
        }
        String token = request.getHeader("Authorization");
        String userTokenString = JWTUtil.verifyToken(token);
        WebContext.setUser(userTokenString);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        WebContext.removeUser();
    }
}
