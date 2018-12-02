package com.itavery.forecast;

import com.itavery.forecast.service.user.UserService;
import com.itavery.forecast.user.RegistrationDTO;
import com.itavery.forecast.user.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

@RestController
@RequestMapping("/api/users")
public class UserResource {

    private final UserService userService;
    private final BCryptPasswordEncoder encoder;
    private Provider provider;

    public UserResource(UserService userService, BCryptPasswordEncoder encoder, Provider provider) {
        this.userService = userService;
        this.encoder = encoder;
        this.provider = provider;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public Response createUser(@RequestBody RegistrationDTO registrationDTO) {
        return Response.ok(userService.createUser(registrationDTO)).build();
    }


    @RequestMapping(value = "/{userId}/details", method = RequestMethod.GET, produces = "application/json")
    public Response getUser(@PathVariable("userId") Integer userId) {
        return Response.status(Response.Status.OK).entity(userService.findUser(userId)).build();
    }


    @RequestMapping(value = "/update/user", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public Response updateUser(@Context HttpServletRequest request, @RequestBody User user) {
        Integer userId = provider.getUserId(request);
        userService.updateUser(user, userId);
        return Response.ok().build();
    }

    @RequestMapping(value = "/utils/oldPasswordMatchesNew", method = RequestMethod.POST, consumes = "application/json")
    public Response pwRequestCheck(@Context HttpServletRequest request, String requestedPw) {
        Integer userId = provider.getUserId(request);
        requestedPw = encoder.encode(requestedPw);
        return Response.ok(userService.requestedPasswordMatchesCurrentPassword(userId, requestedPw)).build();
    }

    @RequestMapping(value = "/deactivate/{userId}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public Response deactivateUser(@PathVariable("userId") Integer userId) {
        return Response.ok(userService.deactivateUser(userId)).build();
    }
}
