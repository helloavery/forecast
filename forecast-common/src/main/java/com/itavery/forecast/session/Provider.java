package com.itavery.forecast.session;

import com.itavery.forecast.Constants;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-08-02
 * https://github.com/helloavery
 */

@Component
public class Provider {

    public String getUsername(HttpServletRequest request) {
        return this.getUserCookies(request);
    }

    public Integer getUserId(HttpServletRequest request) {
        return this.getUserIdCookies(request);
    }

    private String getUserCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        Cookie c = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(Constants.USER_NAME)) {
                c = cookie;
                break;
            }
        }
        if (c == null) {
            return null;
        }
        String username = c.getValue();
        if (username.trim().isEmpty()) {
            return null;
        }
        return username;
    }

    private Integer getUserIdCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        Cookie c = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(Constants.USER_ID)) {
                c = cookie;
                break;
            }
        }
        if (c == null) {
            return null;
        }
        Integer userId = Integer.valueOf(c.getValue());
        if (Optional.ofNullable(userId).orElse(0) != 0) {
            return null;
        }
        return userId;
    }
}
