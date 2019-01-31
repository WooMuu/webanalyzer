package com.zjb.resource_parser.html_parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Created by zjb on 2019/1/31.
 */
public class HtmlParser {
    public static Document parse(String resourse) {
        Document doc = Jsoup.parse(resourse);
        return doc;
    }
}
