package com.itavery.forecast.config;

import com.itavery.forecast.admin.AdminResourceV1;
import com.itavery.forecast.demand.DemandResourceV1;
import com.itavery.forecast.forecast.ForecastResourceV1;
import com.itavery.forecast.user.UserResourceV1;
import com.itavery.forecast.verification.VerificationResourceV1;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2019-05-27
 * https://github.com/helloavery
 */

@Component
@ApplicationPath("/rest")
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig(){
        registerEndpoints();
    }

    private void registerEndpoints(){
        register(AdminResourceV1.class);
        register(DemandResourceV1.class);
        register(ForecastResourceV1.class);
        register(UserResourceV1.class);
        register(VerificationResourceV1.class);
    }

}

