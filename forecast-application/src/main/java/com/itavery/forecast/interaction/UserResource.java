package com.itavery.forecast.interaction;

import com.itavery.forecast.service.user.UserService;
import com.itavery.forecast.session.SessionManager;
import com.itavery.forecast.user.LoginDTO;
import com.itavery.forecast.user.RegistrationDTO;
import com.itavery.forecast.user.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.Principal;
import java.util.Base64;

@RestController
@Path("/users")
@CrossOrigin
public class UserResource {

    private UserService userService;
    private SessionManager sessionManager;

    @Inject
    public UserResource(UserService userService){
        this.userService = userService;
    }

    @Inject
    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(@Context HttpServletRequest request, RegistrationDTO registrationDTO) {
        return userService.createUser(request, registrationDTO);
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@Context HttpServletRequest request, LoginDTO user){
        return userService.login(request, user);
    }

    @POST
    @Path("/verify/token")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response verifyOtp(@Context HttpServletRequest request, String token){
        return userService.verifyOtp(request, token);
    }

    @Path("/user")
    public Principal user(HttpServletRequest request) {
        String authToken = request.getHeader("Authorization").substring("Basic".length()).trim();
        return () ->  new String(Base64.getDecoder().decode(authToken)).split(":")[0];
    }

    @GET
    @Path("/{userId}/details")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("userId") Integer userId) {
        return userService.findUser(userId);
    }

    @PUT
    @Path("/update/user")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@Context HttpServletRequest request, User user) {
        Integer userId = sessionManager.getLoggedUserId(request);
        return userService.updateUser(user, userId);
    }

    @PUT
    @Path("/deactivate/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deactivateUser(@PathParam("userId") Integer userId) {
        return userService.deactivateUser(userId);
    }
}
