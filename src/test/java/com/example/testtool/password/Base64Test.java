/**
 * Author:   Herewe
 * Date:     2022/3/13 21:47
 * Description: BASE64
 */
package com.example.testtool.password;

import org.junit.Test;
import org.springframework.util.Base64Utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64Test {
    public static final String HELLO = "hello你好";

    @Test
    public void testEncode() throws UnsupportedEncodingException {
        /**
         * base64，对字节进行编码，此类支持的3中编码类型
         */
        // 基础的base64编码
        String encode = Base64.getEncoder().encodeToString(HELLO.getBytes());
        // url类型的编码，编码结果中不会出现url中不允许出现的字符
        String url = Base64.getUrlEncoder().encodeToString(HELLO.getBytes());
        // 无填充类型的编码
        String urlNoPadding = Base64.getUrlEncoder().withoutPadding().encodeToString(HELLO.getBytes());
        // mime协议的编码
        String mime = Base64.getMimeEncoder().encodeToString(HELLO.getBytes());
        System.out.println(encode);
        System.out.println(url);
        System.out.println(mime);
        // 区别于base64 url编码是对字符进行编码，（浏览器搜索栏使用的编码方式）
        String url1 = URLEncoder.encode(HELLO, String.valueOf(Charset.forName("utf-8")));
        System.out.println(url1);
    }
}
