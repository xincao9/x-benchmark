package com.github.xincao9.redis.model;

/**
 *
 * @author 510655387@qq.com
 */
public class Request {

    private String key;
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
