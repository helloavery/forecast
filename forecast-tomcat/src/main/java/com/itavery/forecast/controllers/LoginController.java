package com.itavery.forecast.controllers;

/*=============================================================================
 |                Forecaster V1.0
 |
 |       File created by: Avery Grimes-Farrow
 |
 |       Created On:  4/1/18
 |
 *===========================================================================*/

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Collections;
import java.util.Map;

@Controller
public class LoginController {

    @RequestMapping("/user")
    @ResponseBody
    public Principal user(Principal user) {
        return user;
    }

    @RequestMapping("/token")
    @ResponseBody
    public Map<String, String> token(HttpSession session) {
        return Collections.singletonMap("token", session.getId());
    }
}
