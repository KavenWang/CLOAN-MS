package com.uisftech.cloan.limit.config;

import com.uisftech.cloan.limit.async.ExceptionHandlingAsyncTaskExecutor;
import com.uisftech.cloan.limit.config.CloanLimitProperties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.*;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

import javax.inject.Inject;

@Configuration
@EnableAsync
@EnableScheduling
public class AsyncConfiguration implements AsyncConfigurer {

    private final Logger log = LoggerFactory.getLogger(AsyncConfiguration.class);

    @Inject
    private CloanLimitProperties scfsProductProperties;

    @Override
    @Bean(name = "taskExecutor")
    public Executor getAsyncExecutor() {
        log.debug("Creating Async Task Executor");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(scfsProductProperties.getAsync().getCorePoolSize());
        executor.setMaxPoolSize(scfsProductProperties.getAsync().getMaxPoolSize());
        executor.setQueueCapacity(scfsProductProperties.getAsync().getQueueCapacity());
        executor.setThreadNamePrefix("scfs-product-ms-Executor-");
        return new ExceptionHandlingAsyncTaskExecutor(executor);
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SimpleAsyncUncaughtExceptionHandler();
    }
}
