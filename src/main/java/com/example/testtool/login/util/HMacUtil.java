/**
 * Author:   Herewe
 * Date:     2022/3/6 17:08
 * Description:
 */
package com.example.testtool.login.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class HMacUtil {
    public static String hMac256(String content, String secret) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        Mac hmacSHA256 = Mac.getInstance(secretKeySpec.getAlgorithm());
        hmacSHA256.init(secretKeySpec);
        Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
        return encoder.encodeToString(hmacSHA256.doFinal(content.getBytes(StandardCharsets.UTF_8)));
    }

    public static void main(String[] args) {
        try {
            System.out.println(hMac256("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJ1c2VySWRcIjpcImFkbWluXCJ9IiwiaXNzIjoiY2QiLCJleHAiOjE2NDY1NTc2ODd9", "secret"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
