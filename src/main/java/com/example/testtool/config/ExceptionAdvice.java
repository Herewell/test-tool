/**
 * Author:   Herewe
 * Date:     2021/5/23 12:14
 * Description:
 */
package com.example.testtool.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionAdvice {
    @ExceptionHandler(Exception.class)
    public String doHandler(Exception e) {
        log.error("error:{}", e.getMessage(), e);
        return "redirect:err";
    }
}
