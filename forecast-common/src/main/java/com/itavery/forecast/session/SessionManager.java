package com.itavery.forecast.session;

import com.itavery.forecast.constants.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-12-26
 * https://github.com/helloavery
 */

public class SessionManager {

    public int getLoggedUserId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return (int) session.getAttribute(Constants.USER_ID);
        }

        return 0;
    }

}
