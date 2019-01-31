package com.zjb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zjb on 2018/4/3.
 */
@Configuration
public class ThreadPoolConfig {

    private static final int nThreads = 10;

    @Bean
    public ExecutorService myThreadExecutorPool() {
        return Executors.newCachedThreadPool();
    }
}