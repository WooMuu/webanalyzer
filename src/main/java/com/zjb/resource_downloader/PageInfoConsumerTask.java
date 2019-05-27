package com.zjb.resource_downloader;

import com.zjb.resource_downloader.queue.EventQueue;
import com.zjb.statistical_analysis.GetSkillsTask;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

/**
 * Created by zjb on 2019/3/21.
 */
public class PageInfoConsumerTask implements Runnable {

    private EventQueue<String> pageInfoQueue;
    private ExecutorService pool;
    private ConcurrentHashMap<String, Object> cmap;

    public PageInfoConsumerTask(EventQueue<String> pageInfoQueue, ExecutorService pool, ConcurrentHashMap<String, Object> cmap) {
        this.pageInfoQueue = pageInfoQueue;
        this.pool = pool;
        this.cmap = cmap;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String info = pageInfoQueue.take();
                if (info == null) continue;
                GetSkillsTask getSkillsTask = new GetSkillsTask(info, cmap);
                pool.submit(getSkillsTask);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
