package com.zjb;

import com.zjb.url_analyzer.httprest.HttpRestTemplete;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.sql.Time;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class WebanalyzerApplicationTests {

    private static int count = 0;
    private static int runable = 0;

    @Test
    public void contextLoads() {
    }

    @Test
    public void test33() {
        Set<String> set1 = new HashSet<>();
        set1.add("a");
        set1.add("b");
        set1.add("c");
        Set<String> set2 = new HashSet<>();
        set2.add("a");
        set2.add("b");
        set2.add("c");
        System.out.println(set1.equals(set2));
    }

    @Test
    public void test() throws IOException, InterruptedException {

        String url = " https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&rsv_idx=1&tn=baidu&wd=%E6%9D%AD%E5%B7%9E%E6%AF%8D%E5%A5%B3%E9%81%AD%E7%8B%97%E5%9B%B4%E6%94%BB&oq=%25E6%259D%25AD%25E5%25B7%259E%25E6%25AF%258D%25E5%25A5%25B3%25E9%2581%25AD%25E7%258B%2597%25E5%259B%25B4%25E6%2594%25BB&rsv_pq=ddb075550008818f&rsv_t=5850XRS1WKRsMwwWF0%2F9Xc%2BxD90vJAGnRigEOFmUMtgjZ8SD9oZn3VC1qj0&rqlang=cn&rsv_enter=1&rsv_sug3=1&rsv_sug2=0&inputT=8&rsv_sug4=605&rsv_jmp=slow";
        Map<String, String> headers = new HashMap<>();
        headers.put("Cookie", "BAIDUID=3DC27C5D7B2AC0A086E58D8F34C80FC3:FG=1; BIDUPSID=3DC27C5D7B2AC0A086E58D8F34C80FC3; PSTM=1561652926; BD_UPN=12314753; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; MCITY=-179%3A; H_PS_PSSID=1451_21083_29135_29237_28518_29099_28835_29221_29439; BDSFRCVID=0u_OJeC62GpLpZJwXK9ztQGEoeqWU_oTH6aoZXihosw1ogaHeCU7EG0PjU8g0Kub_FlpogKK0mOTHv-F_2uxOjjg8UtVJeC6EG0P3J; H_BDCLCKID_SF=tJIe_KI-JKI3qPFkMRoSMJK8qxby26n402c9aJ5nJDohbM-C3bjbWMP05qOI0MQy5RTKhluKQpP-HJ7-3Ucqj-kN5Mnu0l5XJ258Kl0MLpbWbb0xynoD24AnyfnMBMPjamOnaPLE3fAKftnOM46JehL3346-35543bRTohFLtCPWhKPxD5A3-P_XbfoHKJjy5-o2WbC8Kbu3h-53XU6qLT5XQp3rbpTZbabiV4c2Ll3pDn5l3porhp0njxQyXqv02IOeLKjO5bj1eqOajUonDh8q3H7MJUntKHOlWPJO5hvvhb6O3M7-qfKmDloOW-TB5bbPLUQF5l8-sq0x05bke4tX-NFtt6-etM5; delPer=0; BD_CK_SAM=1; PSINO=1; H_PS_645EC=85e1TVIPPuYh8loeMF8cCVx1%2F9OWk4JJIOwQB3CchiDUiK5bekD0uVIqDvQ");
        headers.put("Host", "www.baidu.com");
        headers.put("Accept", "text/javascript, application/javascript, application/ecmascript, application/x-ecmascript, */*; q=0.01");
        headers.put("Accept-Encoding", "gzip, deflate");
        headers.put("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
        headers.put("Connection", "keep-alive");
        headers.put("Referer", "https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&rsv_idx=1&tn=baidu&wd=%E6%9D%AD%E5%B7%9E%E6%AF%8D%E5%A5%B3%E9%81%AD%E7%8B%97%E5%9B%B4%E6%94%BB&oq=%25E6%259D%25AD%25E5%25B7%259E%25E6%25AF%258D%25E5%25A5%25B3%25E9%2581%25AD%25E7%258B%2597%25E5%259B%25B4%25E6%2594%25BB&rsv_pq=c026f17f000127b5&rsv_t=ba338OTS5RDkP8q8GElww8v6sXZltXsPIGuwNsT5GGuGyjFET36WMwAq4C0&rqlang=cn&rsv_enter=1&rsv_sug3=1&rsv_sug1=3&rsv_sug7=000&rsv_sug2=0&inputT=12582&rsv_sug4=26039&rsv_sug=1");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36");
        headers.put("X-Requested-With", "XMLHttpRequest");
        ExecutorService pool = Executors.newCachedThreadPool();
        while (true) {
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        doGet(url, headers);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    count++;
                    System.out.println(count);
                }
            });
            try {
                TimeUnit.MILLISECONDS.sleep(50);
                runable++;
//                System.out.println("runable:" + runable);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void doGet(String url, Map headers) throws IOException {
        HttpRestTemplete ht = new HttpRestTemplete(url, headers, null, HttpRestTemplete.RequestMethod.GET);
        String response = ht.getResponse();
//        System.out.println(response);
    }
}

