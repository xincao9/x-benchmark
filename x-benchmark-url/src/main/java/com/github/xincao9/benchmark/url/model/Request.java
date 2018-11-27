package com.github.xincao9.benchmark.url.model;

import java.util.Map;

/**
 *
 * @author 510655387@qq.com
 */
public class Request {

    private String url;
    private String method;
    private Map<String, Object> data;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

}
