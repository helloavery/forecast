package com.itavery.forecast.config;

import com.itavery.forecast.bootconfig.ProgramArguments;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Avery Grimes-Farrow
 * Created on: 2018-08-25
 * https://github.com/helloavery
 */

@Component
@ConfigurationProperties
public class ConfigurationParams extends ProgramArguments {

}
