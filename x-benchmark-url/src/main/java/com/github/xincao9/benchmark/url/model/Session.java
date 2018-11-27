package com.github.xincao9.benchmark.url.model;

/**
 *
 * @author 510655387@qq.com
 */
public class Session {

    private Boolean keepalive;
    private Integer timeout;

    public Boolean getKeepalive() {
        return keepalive;
    }

    public void setKeepalive(Boolean keepalive) {
        this.keepalive = keepalive;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

}
