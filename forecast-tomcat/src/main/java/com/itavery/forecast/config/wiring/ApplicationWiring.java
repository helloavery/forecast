package com.itavery.forecast.config.wiring;

/*=============================================================================
 |                Forecaster V1.0
 |
 |       File created by: Avery Grimes-Farrow
 |
 |       Created On:  7/3/18
 |
 *===========================================================================*/

import com.itavery.forecast.assemblers.DemandAssembler;
import com.itavery.forecast.assemblers.ForecastAssembler;
import com.itavery.forecast.assemblers.UserAssembler;
import com.itavery.forecast.bootconfig.ProgramArguments;
import com.itavery.forecast.dao.audit.AuditDAO;
import com.itavery.forecast.dao.audit.AuditDAOImpl;
import com.itavery.forecast.dao.demand.ProductDemandDAO;
import com.itavery.forecast.dao.demand.ProductDemandDAOImpl;
import com.itavery.forecast.dao.forecast.ProductForecastDAO;
import com.itavery.forecast.dao.forecast.ProductForecastDAOImpl;
import com.itavery.forecast.dao.user.UserDAO;
import com.itavery.forecast.dao.user.UserDAOImpl;
import com.itavery.forecast.dao.verification.VerificationDAO;
import com.itavery.forecast.dao.verification.VerificationDAOImpl;
import com.itavery.forecast.external.MailgunEmailVerification;
import com.itavery.forecast.external.MailgunEmailVerificationImpl;
import com.itavery.forecast.service.audit.AuditService;
import com.itavery.forecast.service.audit.AuditServiceImpl;
import com.itavery.forecast.service.demand.ProductDemandService;
import com.itavery.forecast.service.demand.ProductDemandServiceImpl;
import com.itavery.forecast.service.email.EmailService;
import com.itavery.forecast.service.email.EmailServiceImpl;
import com.itavery.forecast.service.forecast.ProductForecastService;
import com.itavery.forecast.service.forecast.ProductForecastServiceImpl;
import com.itavery.forecast.service.user.UserService;
import com.itavery.forecast.service.user.UserServiceImpl;
import com.itavery.forecast.service.verification.VerificationService;
import com.itavery.forecast.service.verification.VerificationServiceImpl;
import com.itavery.forecast.validator.ProductDemandValidator;
import com.itavery.forecast.validator.ProductForecastValidator;
import com.itavery.forecast.validator.UserValidator;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class ApplicationWiring {

    private DemandAssembler demandAssembler;

    private ForecastAssembler forecastAssembler;

    private UserAssembler userAssembler;

    private ProductDemandValidator productDemandValidator;

    private ProductForecastValidator productForecastValidator;

    private UserValidator userValidator;

    private final ProgramArguments programArguments;

    public ApplicationWiring(DemandAssembler demandAssembler, ForecastAssembler forecastAssembler, UserAssembler userAssembler,
                             ProductDemandValidator productDemandValidator, ProductForecastValidator productForecastValidator,
                             UserValidator userValidator, final ProgramArguments programArguments) {
        this.demandAssembler = demandAssembler;
        this.forecastAssembler = forecastAssembler;
        this.userAssembler = userAssembler;
        this.productDemandValidator = productDemandValidator;
        this.productForecastValidator = productForecastValidator;
        this.userValidator = userValidator;
        this.programArguments = programArguments;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public VelocityEngine velocityEngine(){
        return new VelocityEngine();
    }


    @Bean
    AuditDAO auditDAO() {
        return new AuditDAOImpl();
    }

    @Bean
    EmailService emailService() {
        return new EmailServiceImpl(auditService(),velocityEngine(), programArguments);
    }

    @Bean
    ProductDemandDAO productDemandDAO() {
        return new ProductDemandDAOImpl(demandAssembler);
    }

    @Bean
    ProductForecastDAO productForecastDAO() {
        return new ProductForecastDAOImpl(forecastAssembler);
    }

    @Bean
    UserDAO userDAO() {
        return new UserDAOImpl(userAssembler, passwordEncoder());
    }

    @Bean
    VerificationDAO verificationDAO() {
        return new VerificationDAOImpl();
    }

    @Bean
    AuditService auditService() {
        return new AuditServiceImpl(auditDAO());
    }

    @Bean
    ProductDemandService productDemandService() {
        return new ProductDemandServiceImpl(auditService(), productDemandDAO(), productDemandValidator);
    }

    @Bean
    ProductForecastService productForecastService() {
        return new ProductForecastServiceImpl(auditService(), productForecastDAO(), productForecastValidator);
    }

    @Bean
    UserService userService() {
        return new UserServiceImpl(auditService(), userDAO(), userValidator, emailService(), verificationService());
    }

    @Bean
    VerificationService verificationService(){
        return new VerificationServiceImpl(verificationDAO(),auditService(),mailgunEmailVerification());
    }
    @Bean
    MailgunEmailVerification mailgunEmailVerification() {
        return new MailgunEmailVerificationImpl(programArguments);
    }
}
