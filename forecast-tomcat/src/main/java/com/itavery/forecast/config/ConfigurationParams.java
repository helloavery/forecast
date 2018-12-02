package com.itavery.forecast.config;
 
 /*=============================================================================
 |                Forecaster V1.0
 |
 |       File created by: Avery Grimes-Farrow
 |
 |       Created On:  8/25/18            
 |            
 *===========================================================================*/

import com.itavery.forecast.bootconfig.ProgramArguments;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class ConfigurationParams extends ProgramArguments {

}
