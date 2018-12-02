package com.itavery.forecast.mithra;
 
 /*=============================================================================
 |                Forecaster V1.0
 |
 |       File created by: Avery Grimes-Farrow
 |
 |       Created On:  11/5/18            
 |            
 *===========================================================================*/

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class TransactionalConfig {

    @Bean
    TransactionInterceptor transactionalMethodInterceptor(){
        return new TransactionInterceptor();
    }
}
