package com.itavery.forecast.utils.session;

import com.itavery.forecast.Constants;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-12-26
 * https://github.com/helloavery
 */

@Named
public class SessionManager {

    public int getLoggedUserId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return (int) session.getAttribute(Constants.USER_ID);
        }
        return 0;
    }

    public String getSessionId(){
        HttpServletRequest servletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return servletRequest != null ? getSessionId(servletRequest) : null;
    }

    public String getSessionId(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session != null) {
            return (String) session.getAttribute(Constants.SESSION_ID);
        }
        return null;
    }

}
