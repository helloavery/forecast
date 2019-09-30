package com.itavery.forecast.interaction;

import com.averygrimes.core.pojo.EmailNotificationRequest;
import com.itavery.forecast.kafka.KafkaMessageSender;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * @author Avery Grimes-Farrow
 * Created on: 8/25/19
 * https://github.com/helloavery
 */

@RestController
@Path("/kafka")
public class KafkaResource {

    private KafkaMessageSender messageSender;

    @Inject
    public void setMessageSender(KafkaMessageSender messageSender) {
        this.messageSender = messageSender;
    }


    @POST
    @Path("/send")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendMessage(EmailNotificationRequest emailNotificationRequest){
        messageSender.sendEmailNotificationMessage(emailNotificationRequest);
        return Response.ok().build();
    }
}
