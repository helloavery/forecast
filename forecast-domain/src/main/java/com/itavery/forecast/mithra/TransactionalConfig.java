package com.itavery.forecast.mithra;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-11-05
 * https://github.com/helloavery
 */

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class TransactionalConfig {

    @Bean
    TransactionInterceptor transactionalMethodInterceptor(){
        return new TransactionInterceptor();
    }
}
