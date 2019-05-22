package com.itavery.forecast;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-12-26
 * https://github.com/helloavery
 */

public class SessionManager {

    private static final int MAX_INACTIVE_INTERVAL = 30 * 60;

    public void partialLogIn(HttpServletRequest request, int userId) {
        HttpSession session = request.getSession();
        session.setAttribute(Constants.PARTIALLY_AUTHENTICATED, true);
        session.setAttribute(Constants.USER_ID, userId);
        session.setMaxInactiveInterval(MAX_INACTIVE_INTERVAL);
    }

    public int getLoggedUserId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return (int) session.getAttribute(Constants.USER_ID);
        }

        return 0;
    }

}
