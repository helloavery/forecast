package com.itavery.forecast.config;

import com.itavery.forecast.interaction.AdminResource;
import com.itavery.forecast.interaction.DemandResource;
import com.itavery.forecast.interaction.ForecastResource;
import com.itavery.forecast.interaction.UserResource;
import com.itavery.forecast.interaction.VerificationResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2019-05-27
 * https://github.com/helloavery
 */

@Component
@ApplicationPath("/rest/v1")
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig(){
        registerEndpoints();
    }

    private void registerEndpoints(){
        register(AdminResource.class);
        register(DemandResource.class);
        register(ForecastResource.class);
        register(UserResource.class);
        register(VerificationResource.class);
    }

}

