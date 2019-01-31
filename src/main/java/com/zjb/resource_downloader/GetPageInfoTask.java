package com.zjb.resource_downloader;

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
    private String url;
    private HttpRestTemplete ht;
    private StringBuilder str = new StringBuilder();

    public GetPageInfoTask(String url) throws IOException {
        this.url = url;
        ht = new HttpRestTemplete(url, null, null, HttpRestTemplete.RequestMethod.GET);
    }

    @Override
    public String call() throws Exception {
        String response = ht.getResponse();
//        System.out.println(response);
        Document doc = HtmlParser.parse(response);
        try {
            Elements elements = doc.getElementsByClass("pos-ul");
            for (Element element : elements.get(0).children()) {
                if (element.hasText()) {
                    str.append(element.text());
                }
            }
            return str.toString();
        } catch (NullPointerException e) {
            return null;
        }

    }
}
