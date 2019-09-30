package com.itavery.forecast.config;

import com.averygrimes.servicediscovery.FeignClientBean;
import com.itavery.forecast.interaction.client.AuthyClient;
import com.itavery.forecast.interaction.client.MailgunClient;
import com.itavery.forecast.utils.exceptions.ClientRestConfigException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;

/**
 * @author Avery Grimes-Farrow
 * Created on: 9/29/19
 * https://github.com/helloavery
 */

@Configuration
public class ClientRestConfiguration {

    private static final Logger LOGGER = LogManager.getLogger(ClientRestConfiguration.class);
    private ProgramArguments programArguments;

    private static final String AUTHY_SERVICE = "authyService";
    private static final String MAILGUN_SERVICE  = "mailgunService";

    @Inject
    public void setProgramArguments(ProgramArguments programArguments) {
        this.programArguments = programArguments;
    }

    @Bean
    public AuthyClient createAuthyClient(){
        String version =  StringUtils.defaultString(programArguments.getAuthyClientVersion(), "1.0.0");
        String instance = StringUtils.defaultString(programArguments.getInstance(), "QA");
        FeignClientBean<AuthyClient> feignClientBean = new FeignClientBean<>(AuthyClient.class, AUTHY_SERVICE, version, instance);
        return getClientFromFeignObject(feignClientBean);
    }

    @Bean
    public MailgunClient createMailgunClient(){
        String version = StringUtils.defaultString(programArguments.getMailgunClientVersion(), "1.0.0");
        String instance = StringUtils.defaultString(programArguments.getInstance(), "QA");
        FeignClientBean<MailgunClient> feignClientBean = new FeignClientBean<>(MailgunClient.class, MAILGUN_SERVICE, version, instance);
        return getClientFromFeignObject(feignClientBean);
    }

    private <T> T getClientFromFeignObject(FeignClientBean<T> feignClientBean){
        try{
            return (T) feignClientBean.getObject();
        }
        catch(Exception e){
            LOGGER.error("Error getting client from feign client bean");
            throw new ClientRestConfigException("Error getting client from feign client bean " + e.getMessage());
        }
    }

}
