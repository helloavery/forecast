package com.itavery.forecast;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * File created by Avery Grimes-Farrow
 * Created on: 2018-12-26
 * https://github.com/helloavery
 */

public class SessionManager {

    public static final String AUTHENTICATED = "authenticated";
    public static final String PARTIALLY_AUTHENTICATED = "partially-authenticated";
    public static final String USER_ID = "user-id";

    private static final int MAX_INACTIVE_INTERVAL = 30 * 60;

    public void partialLogIn(HttpServletRequest request, int userId) {
        HttpSession session = request.getSession();
        session.setAttribute(PARTIALLY_AUTHENTICATED, true);
        session.setAttribute(USER_ID, userId);
        session.setMaxInactiveInterval(MAX_INACTIVE_INTERVAL);
    }

    public int getLoggedUserId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return (int) session.getAttribute(USER_ID);
        }

        return 0;
    }


}
