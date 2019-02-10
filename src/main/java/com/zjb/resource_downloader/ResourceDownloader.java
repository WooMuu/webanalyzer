package com.zjb.resource_downloader;

import com.zjb.config.ThreadPoolConfig;
import com.zjb.resource_parser.vo.SortedEntry;
import com.zjb.statistical_analysis.GetSkillsTask;
import com.zjb.url_analyzer.httprest.HttpRestTemplete;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created by zjb on 2019/1/31.
 */
public class ResourceDownloader {
    private HttpRestTemplete hrt;
    @Autowired
    ExecutorService myThreadExecutorPool;
    private ConcurrentHashMap<String, Object> chm = new ConcurrentHashMap<>();

    @Test
    public void testGet() throws IOException, ExecutionException, InterruptedException {
        ThreadPoolConfig poolConfig = new ThreadPoolConfig();
        ExecutorService pool = poolConfig.myThreadExecutorPool();
        ArrayList<Future<List<String>>> futures = new ArrayList<>();
        ArrayList<Future<String>> futuresOfPage = new ArrayList<>();
        ArrayList<Future<String>> skillFutures = new ArrayList<>();
        int pageSize = 90;
//        String baseURL = "https://fe-api.zhaopin.com/c/i/sou?cityId=530&workExperience=0510&education=-1&companyType=-1&employmentType=-1&jobWelfareTag=-1&kw=%E6%B5%8B%E8%AF%95&kt=3&_v=0.66292241&x-zp-page-request-id=41b93d1e989c4730bd50ddfb37e6ef9e-1549791028076-477391";
        String baseURL = "https://fe-api.zhaopin.com/c/i/sou?cityId=653&industry=10100&workExperience=-1&education=-1&companyType=-1&employmentType=-1&jobWelfareTag=-1&kw=java&kt=3&_v=0.12844542&x-zp-page-request-id=8eea868d93574748a36d79ec94f2e7aa-1548947204714-985513";
//        String baseURL = "https://fe-api.zhaopin.com/c/i/sou?cityId=530&industry=10100&workExperience=-1&education=-1&companyType=-1&employmentType=-1&jobWelfareTag=-1&kw=java&kt=3&_v=0.12844542&x-zp-page-request-id=8eea868d93574748a36d79ec94f2e7aa-1548947204714-985513";
        for (int i = 0; i <= 1000; i += pageSize) {
            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("start", 1);
            parameters.put("&pageSize", pageSize);
            GetUrlOfPageTask task = new GetUrlOfPageTask(baseURL, parameters);
            Future<List<String>> future = pool.submit(task);
            futures.add(future);
        }
        for (Future<List<String>> future : futures) {
            List<String> urls = future.get();
            for (String url : urls) {
                GetPageInfoTask task = new GetPageInfoTask(url);
                Future<String> futureOfPage = pool.submit(task);
                futuresOfPage.add(futureOfPage);
            }
            for (Future<String> pageFuture : futuresOfPage) {
                String info = pageFuture.get();
                if (info == null) continue;
                GetSkillsTask getSkillsTask = new GetSkillsTask(chm, info);
                pool.submit(getSkillsTask);
            }
        }
        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.HOURS);
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
