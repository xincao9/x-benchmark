package com.github.xincao9.jms.model;

import java.util.Map;

/**
 *
 * @author 510655387@qq.com
 */
public class Request {

    private String destination;
    private Map<String, Object> data;

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

}
