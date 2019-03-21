package com.zjb.config;

import com.zjb.resource_downloader.queue.EventQueue;
import com.zjb.resource_downloader.queue.PageInfoQueue;
import com.zjb.resource_downloader.queue.UrlQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zjb on 2019/3/21.
 */
@Configuration
public class QueueAndTaskConfig {

    @Bean("urlQueue")
    public EventQueue<String> urlQueue() {
        return new UrlQueue();
    }

    @Bean("pageInfoQueue")
    public EventQueue<String> pageInfoQueue() {
        return new PageInfoQueue();
    }

    @Bean("chm")
    public ConcurrentHashMap<String, Object> chm() {
        return new ConcurrentHashMap<>();
    }
}
