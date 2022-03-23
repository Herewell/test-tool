/**
 * Author:   Herewe
 * Date:     2022/3/6 10:15
 * Description: 登录
 */
package com.example.testtool.login.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String userId;
    private String password;

}
