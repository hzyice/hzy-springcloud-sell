package com.hzyice.apigateway.util;

import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
    public static void set(HttpServletResponse response, String key, String openid, Integer expire) {
        Cookie cookie = new Cookie(key, openid);
//        cookie.setPath("/");
        cookie.setMaxAge(expire);
        response.addCookie(cookie);
//        try {
//            response.flushBuffer();
//        } catch (IOException e) {
//            System.out.println("CookieUtil: cookie flush exception...");
//        }
    }


    public static Cookie get(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (StringUtils.isEmpty(cookies)) {
            return null;
        }
        Cookie cookie = null;
        for (Cookie c : cookies) {
            if (name.equals(c.getName())) {
                cookie = c;
                break;
            }
        }
        return cookie;
    }

    // 更新 cookie 时间
    public static void updateTime(HttpServletResponse response, Cookie cookie, Integer time) {
        cookie.setMaxAge(time);
        response.addCookie(cookie);
    }
}
