package com.zjb.resource_downloader;

import com.zjb.config.QueueAndTaskConfig;
import com.zjb.config.ThreadPoolConfig;
import com.zjb.resource_downloader.queue.EventQueue;
import com.zjb.resource_downloader.queue.PageInfoQueue;
import com.zjb.resource_parser.vo.SortedEntry;
import com.zjb.url_analyzer.httprest.HttpRestTemplete;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created by zjb on 2019/1/31.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ThreadPoolConfig.class, QueueAndTaskConfig.class})
public class ResourceDownloader {
    private HttpRestTemplete hrt;
    @Autowired
    ExecutorService pool;
    @Autowired
    private ConcurrentHashMap<String, Object> chm;
    @Autowired
    private EventQueue<String> urlQueue;

    @Autowired
    private EventQueue<String> pageInfoQueue;

    @Test
    public void testGet() throws IOException, ExecutionException, InterruptedException {
        int pageSize = 90;
        pool.submit(new UrlConsumerTask(urlQueue, pool, pageInfoQueue));
        pool.submit(new PageInfoConsumerTask(pageInfoQueue, pool, chm));
//        String baseURL = "https://fe-api.zhaopin.com/c/i/sou?cityId=530&workExperience=0510&education=-1&companyType=-1&employmentType=-1&jobWelfareTag=-1&kw=%E6%B5%8B%E8%AF%95&kt=3&_v=0.66292241&x-zp-page-request-id=41b93d1e989c4730bd50ddfb37e6ef9e-1549791028076-477391";
//        String baseURL = "https://fe-api.zhaopin.com/c/i/sou?cityId=653&industry=10100&workExperience=-1&education=-1&companyType=-1&employmentType=-1&jobWelfareTag=-1&kw=java&kt=3&_v=0.12844542&x-zp-page-request-id=8eea868d93574748a36d79ec94f2e7aa-1548947204714-985513";
        String baseURL = "https://fe-api.zhaopin.com/c/i/sou?cityId=530&industry=10100&workExperience=-1&education=-1&companyType=-1&employmentType=-1&jobWelfareTag=-1&kw=java&kt=3&_v=0.12844542&x-zp-page-request-id=8eea868d93574748a36d79ec94f2e7aa-1548947204714-985513";
        for (int i = 0; i <= 10000; i += pageSize) {
            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("start", i);
            parameters.put("&pageSize", pageSize);
            GetUrlOfPageTask task = new GetUrlOfPageTask(baseURL, parameters, urlQueue);
            pool.submit(task);
        }
        pool.awaitTermination(2, TimeUnit.MINUTES);
        ArrayList<SortedEntry> list = new ArrayList<>();
        long start = System.currentTimeMillis();
        for (Map.Entry<String, Object> entry : chm.entrySet()) {
            SortedEntry sortedEntry = new SortedEntry(entry.getKey(), (int) entry.getValue());
            list.add(sortedEntry);
        }
        Collections.sort(list);
        System.out.println("排序耗时" + (System.currentTimeMillis() - start));
        System.out.println(list);


    }
}
