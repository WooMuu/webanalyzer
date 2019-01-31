package com.zjb.resource_downloader;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zjb.url_analyzer.httprest.HttpRestTemplete;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by zjb on 2019/1/31.
 */
public class GetUrlOfPageTask implements Callable<List<String>> {
    private HttpRestTemplete ht;

    public GetUrlOfPageTask(String url, Map<String, Object> parameters) {
        try {
            this.ht = new HttpRestTemplete(url, null, parameters, HttpRestTemplete.RequestMethod.GET);
        } catch (IOException e) {
            System.out.println(parameters.get("start") + "查询出错！");
        }
    }

    @Override
    public List<String> call() throws Exception {
        String response = ht.getResponse();
        ArrayList<String> urls = new ArrayList<>();
        JSONObject jsonObject = JSONObject.parseObject(response);
        Object data = jsonObject.get("data");
        String dataString = JSON.toJSONString(data);
        JSONObject dataOj = JSON.parseObject(dataString);
        Object results = dataOj.get("results");
        String resultsString = JSON.toJSONString(results);
        JSONArray objects = JSON.parseArray(resultsString);
        for (Object object : objects) {
            String objString = JSON.toJSONString(object);
            JSONObject parseObject = JSON.parseObject(objString);
            Object positionURL = parseObject.get("positionURL");
            urls.add(positionURL.toString());
        }
        System.out.println("此页面url数量" + urls.size());
        return urls;
    }
}
