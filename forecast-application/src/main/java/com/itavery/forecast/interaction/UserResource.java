package com.itavery.forecast.interaction;

import com.itavery.forecast.request.LoginRequest;
import com.itavery.forecast.request.RegistrationRequest;
import com.itavery.forecast.service.user.UserService;
import com.itavery.forecast.user.User;
import com.itavery.forecast.utils.ResponseBuilder;
import com.itavery.forecast.utils.session.SessionManager;
import com.itavery.forecast.utils.validation.ValidUserRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.BeanParam;
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

@RestController
@Path("/users")
@CrossOrigin
public class UserResource {

    private UserService userService;
    private SessionManager sessionManager;

    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Inject
    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(@Context HttpServletRequest servletRequest,
                               @ValidUserRequest @BeanParam RegistrationRequest registrationRequest) {
        return userService.createUser(servletRequest, registrationRequest);
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@Context HttpServletRequest request,
                          @Valid @BeanParam LoginRequest loginRequest){
        return userService.login(request, loginRequest);
    }

    @POST
    @Path("/verify/token")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response verifyOtp(@Context HttpServletRequest request, String token){
        return userService.verifyOtp(request, token);
    }

    @GET
    @Path("/{userId}/details")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("userId") int userId) {
        return userService.findUser(userId);
    }

    @PUT
    @Path("/update/user")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@Context HttpServletRequest request, @ValidUserRequest User user) {
        int userId = sessionManager.getLoggedUserId(request);
        return userId != 0 ? userService.updateUser(user, userId)
                : ResponseBuilder.createFailureResponse(Response.Status.BAD_REQUEST, "Invalid userId");
    }

    @PUT
    @Path("/deactivate/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deactivateUser(@PathParam("userId") int userId) {
        return userService.deactivateUser(userId);
    }
}
