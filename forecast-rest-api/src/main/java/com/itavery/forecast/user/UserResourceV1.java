package com.itavery.forecast.user;

import com.itavery.forecast.Provider;
import com.itavery.forecast.service.user.UserService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import java.security.Principal;
import java.util.Base64;

@RestController
@RequestMapping("v1/users")
@CrossOrigin
public class UserResourceV1 {

    @Inject
    private UserService userService;
    @Inject
    private Provider provider;

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public UserDTO createUser(@Context HttpServletRequest request, @RequestBody RegistrationDTO registrationDTO) {
        return userService.createUser(request, registrationDTO);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public boolean login(@Context HttpServletRequest request, @RequestBody LoginDTO user){
        return userService.login(request, user);
    }


    @RequestMapping(value = "/verify/token", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public boolean verifyOtp(@Context HttpServletRequest request, @RequestBody String token){
        return userService.verifyOtp(request, token);
    }

    @RequestMapping("/user")
    public Principal user(HttpServletRequest request) {
        String authToken = request.getHeader("Authorization").substring("Basic".length()).trim();
        return () ->  new String(Base64.getDecoder().decode(authToken)).split(":")[0];
    }


    @RequestMapping(value = "/{userId}/details", method = RequestMethod.GET, produces = "application/json")
    public UserDTO getUser(@PathVariable("userId") Integer userId) {
        return userService.findUser(userId);
    }


    @RequestMapping(value = "/update/user", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public void updateUser(@Context HttpServletRequest request, @RequestBody User user) {
        Integer userId = provider.getUserId(request);
        userService.updateUser(user, userId);
    }

    @RequestMapping(value = "/deactivate/{userId}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public String deactivateUser(@PathVariable("userId") Integer userId) {
        return userService.deactivateUser(userId);
    }
}
