package com.zjb.statistical_analysis;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zjb on 2019/1/31.
 */
public class GetSkillsTask implements Callable<String> {

    private ConcurrentHashMap<String, Object> cmap;
    private String pageInfo;
    private static final String pattern = "[a-zA-Z]+('?[a-zA-Z])?";
    private final Pattern r = Pattern.compile(pattern);

    public GetSkillsTask(String pageInfo, ConcurrentHashMap<String, Object> cmap) {
        this.pageInfo = pageInfo;
        this.cmap = cmap;
    }

    @Override
    public String call() throws Exception {
        String strNew = pageInfo.toLowerCase();
        Matcher m = r.matcher(strNew);
        while (m.find()) {
            String keyWord = m.group();
            cmap.put(keyWord, (int) cmap.getOrDefault(keyWord, 0) + 1);
        }
        return null;
    }
}
