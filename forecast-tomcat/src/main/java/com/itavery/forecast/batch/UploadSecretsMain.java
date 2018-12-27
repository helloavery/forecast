package com.itavery.forecast.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * File created by Avery Grimes-Farrow
 * Created on: 2018-12-18
 * https://github.com/helloavery
 */

@SpringBootApplication
public class UploadSecretsMain extends ExecutorBatch{


    public static void main(String[] args){
        ApplicationContext applicationContext = SpringApplication.run(UploadSecretsMain.class, args);
        applicationContext.getBean(ExecutorBatch.class).execute();
    }

    public void init(){
        executor.submit((Runnable) () -> new UploadSecretsJob(null,null));
    }
}
