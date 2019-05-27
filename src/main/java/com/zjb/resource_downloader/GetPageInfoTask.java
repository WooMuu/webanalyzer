package com.zjb.resource_downloader;

import com.zjb.resource_downloader.queue.EventQueue;
import com.zjb.resource_parser.html_parser.HtmlParser;
import com.zjb.url_analyzer.httprest.HttpRestTemplete;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * Created by zjb on 2019/1/31.
 */
public class GetPageInfoTask implements Callable<String> {
    private HttpRestTemplete ht;
    private StringBuilder pageInfo = new StringBuilder();


    private EventQueue<String> pageInfoQueue;

    public GetPageInfoTask(String url, EventQueue<String> pageInfoQueue) throws IOException {
        this.pageInfoQueue = pageInfoQueue;
        ht = new HttpRestTemplete(url, null, null, HttpRestTemplete.RequestMethod.GET);
    }

    @Override
    public String call() throws Exception {
        String response = ht.getResponse();
//        System.out.println(response);
        Document doc = HtmlParser.parse(response);
        try {
            Elements elements = doc.getElementsByClass("describtion__detail-content");
            for (Element element : elements.get(0).children()) {
                if (element.hasText()) {
                    pageInfo.append(element.text());
                }
            }
            pageInfoQueue.offer(pageInfo.toString());
            return null;
        } catch (NullPointerException e) {
            return null;
        }

    }
}
