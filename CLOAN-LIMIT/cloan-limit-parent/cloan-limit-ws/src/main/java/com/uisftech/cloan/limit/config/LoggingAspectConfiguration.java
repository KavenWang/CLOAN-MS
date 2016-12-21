package com.uisftech.cloan.limit.config;

import com.uisftech.cloan.limit.aop.logging.LoggingAspect;

import org.springframework.context.annotation.*;

@Configuration
@EnableAspectJAutoProxy
public class LoggingAspectConfiguration {

    @Bean
    @Profile(com.uisftech.cloan.limit.constants.Constants.SPRING_PROFILE_DEVELOPMENT)
    public LoggingAspect loggingAspect() {
        return new LoggingAspect();
    }
}
