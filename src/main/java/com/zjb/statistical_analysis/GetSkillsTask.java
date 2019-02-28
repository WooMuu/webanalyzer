package com.zjb.statistical_analysis;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zjb on 2019/1/31.
 */
public class GetSkillsTask implements Callable<String> {
    private ConcurrentHashMap<String, Object> chm;
    private String str;
    private static final String pattern = "[a-zA-Z]+('?[a-zA-Z])?";
    private final Pattern r = Pattern.compile(pattern);

    public GetSkillsTask(ConcurrentHashMap<String, Object> chm, String str) {
        this.chm = chm;
        this.str = str;
    }

    @Override
    public String call() throws Exception {
        String strNew = str.toLowerCase();
        Matcher m = r.matcher(strNew);
        while (m.find()) {
            String keyWord = m.group();
            chm.put(keyWord, (int) chm.getOrDefault(keyWord, 0) + 1);
        }
        return null;
    }
}
