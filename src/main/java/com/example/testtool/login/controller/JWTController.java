/**
 * Author:   Herewe
 * Date:     2022/3/6 10:08
 * Description: JWT
 */
package com.example.testtool.login.controller;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.example.testtool.login.JwtIgnore;
import com.example.testtool.login.UserToken;
import com.example.testtool.login.request.LoginRequest;
import com.example.testtool.login.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
public class JWTController {
    @RequestMapping("login")
    @JwtIgnore
    private String login(@ModelAttribute LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
        loginRequest.getUserId();
        loginRequest.getPassword();
        UserToken userToken = new UserToken();
        userToken.setUserId(loginRequest.getUserId());
        String token = JWTUtil.createToken(JSON.toJSONString(userToken));
        response.setHeader("Authorization", token);
        return "redirect:index";
    }
}
