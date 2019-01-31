package com.zjb.url_analyzer.httprest;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.zip.GZIPInputStream;


/**
 * Created by zjb on 2019/1/30.
 */
public class HttpRestTemplete {
    private String url;
    private URL baseURL;
    private URLConnection uc;
    private Map<String, String> headers;
    private Map<String, Object> parameters;
    private Enum<RequestMethod> method;
    private String encoding = "utf-8";
    private int length;
    private long lengthLong;
    private String contentEncoding;
    private StringBuilder response = new StringBuilder();

    public static enum RequestMethod {
        GET, SET, POST, PUT
    }

    /**
     * @param url
     * @param headers
     * @param parameters
     * @param method
     * @throws IOException
     */
    public HttpRestTemplete(String url, Map<String, String> headers, Map<String, Object> parameters, Enum<RequestMethod> method) throws IOException {
        this.url = url;
        this.parameters = parameters;
        this.method = method;
        setParameters();
        this.baseURL = new URL(this.url);
        this.uc = baseURL.openConnection();
        this.headers = headers;
        setHeaders();
    }

    private void setHeaders() {
        if (headers == null) return;
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            //省略对map中key/value的校验
            uc.setRequestProperty(entry.getKey(), entry.getValue());
        }
    }

    private void setParameters() {
        if (method.equals(RequestMethod.GET)) setParametersOfGet();
        else if (method.equals(RequestMethod.POST)) System.out.println("post");
        else if (method.equals(RequestMethod.PUT)) System.out.println("put");
        else if (method.equals(RequestMethod.SET)) System.out.println("set");
    }

    private void setParametersOfGet() {
        if (parameters == null) {
            return;
        }
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            this.url += entry.getKey() + "=" + entry.getValue();
        }
    }

    public String getResponse() {
        getResponseHeaders();
        try (InputStream raw = uc.getInputStream()) {//自动关闭
            //如果Content encoding为gzip，串入GZipInputStream
            InputStream buffer;
            if (contentEncoding != null && contentEncoding.equals("gzip")) {
                buffer = new BufferedInputStream(new GZIPInputStream(raw));
            } else {
                buffer = new BufferedInputStream(raw);
            }
            //将inputstream串连到一个reader
            InputStreamReader reader = new InputStreamReader(buffer, encoding);
            int c;
            while ((c = reader.read()) != -1) {
                response.append((char) c);
            }
        } catch (IOException e) {
            System.out.println("IO Exception");
        }
        return response.toString();
    }

    public void getResponseHeaders() {
        //获取响应信息编码方式
        String contentType = uc.getContentType();
        int encodingStart = contentType.indexOf("charset=");
        if (encodingStart != -1) {
            encoding = contentType.substring(encodingStart + 8);
        }
        length = uc.getContentLength();
        //如果资源大小超过int的最大值，返回-1，java7增加了getContentLengthLong ()返回long
        lengthLong = uc.getContentLengthLong();
        contentEncoding = uc.getContentEncoding();
        return;
    }
}

