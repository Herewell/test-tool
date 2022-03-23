/**
 * Author:   Herewe
 * Date:     2022/3/6 11:54
 * Description:
 */
package com.example.testtool.login.util;

import com.alibaba.fastjson.JSON;
import com.example.testtool.login.UserToken;

import java.util.Objects;

public class WebContext {
    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static UserToken getUser() {
        UserToken userToken = JSON.parseObject(threadLocal.get(), UserToken.class);
        return userToken;
    }

    public static void setUser(String userToken) {
        removeUser();
        threadLocal.set(userToken);

    }

    public static void removeUser() {
        if (Objects.nonNull(threadLocal.get())) {
            threadLocal.remove();
        }
    }
}
