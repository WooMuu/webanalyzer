package com.zjb.resource_downloader;

import com.zjb.resource_downloader.queue.EventQueue;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

/**
 * Created by zjb on 2019/3/21.
 */
public class UrlConsumerTask implements Runnable {

    private EventQueue<String> urlQueue;
    private ExecutorService pool;
    private EventQueue<String> pageInfoQueue;

    public UrlConsumerTask(EventQueue<String> urlQueue, ExecutorService pool, EventQueue<String> pageInfoQueue) {
        this.urlQueue = urlQueue;
        this.pool = pool;
        this.pageInfoQueue = pageInfoQueue;

    }

    @Override
    public void run() {
        while (true) {
            try {
                String url = urlQueue.take();
                GetPageInfoTask task = new GetPageInfoTask(url, pageInfoQueue);
                pool.submit(task);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
