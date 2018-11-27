/**
 * 欢迎浏览和修改代码，有任何想法可以email我
 */
package com.github.xincao9.redis.model;

import java.util.List;

/**
 *
 * @author 510655387@qq.com
 */
public class Configure {

    private Professor professor;
    private Redis redis;
    private List<Request> flow;
    private Source source;
    private static Configure configure;

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Redis getRedis() {
        return redis;
    }

    public void setRedis(Redis redis) {
        this.redis = redis;
    }

    public List<Request> getFlow() {
        return flow;
    }

    public void setFlow(List<Request> flow) {
        this.flow = flow;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public static Configure getConfigure() {
        return configure;
    }

    public static void setConfigure(Configure configure) {
        Configure.configure = configure;
    }

}
