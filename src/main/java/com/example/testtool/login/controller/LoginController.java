/**
 * Author:   Herewe
 * Date:     2022/3/6 10:29
 * Description:
 */
package com.example.testtool.login.controller;

import com.example.testtool.login.JwtIgnore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
    @RequestMapping("/unLogin")
    @JwtIgnore
    public String login() {
        return "unLogin";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/err")
    @JwtIgnore
    public String error() {
        return "err";
    }
}
