/**
 * Author:   Herewe
 * Date:     2022/3/6 11:09
 * Description: <a>https://github.com/auth0/java-jwt<a/>
 */
package com.example.testtool.login.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.springframework.util.StringUtils;

import java.util.Date;


public class JWTUtil {

    /**
     * Authorization: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJ1c2VySWRcIjpcImFkbWluXCJ9IiwiaXNzIjoiY2QiLCJleHAiOjE2NDY1NTc2ODd9.-5ulD3OWL-fUbT7ocv41qgAuchmR7CBusADWpZfDEKs
     * @param content
     * @return
     */
    public static String createToken(String content) {
        String result = JWT.create()
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 10))
                .withIssuer("cd")
                .withSubject(content)
                .sign(Algorithm.HMAC256("secret"));
        return result;
    }

    public static String verifyToken(String token) throws Exception {
        if (StringUtils.isEmpty(token)) {
            throw new Exception("token验证失败!");
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            String userTokenString = JWT.require(algorithm)
                    .withIssuer("cd")
                    .build()
                    .verify(token)
                    .getSubject();//Reusable verifier instance
            return userTokenString;
        } catch (TokenExpiredException exception) {
            throw new Exception("token已经过期，请重新登陆", exception);
        } catch (JWTVerificationException exception) {
            throw new Exception("token验证失败!", exception);
        }
    }
}
