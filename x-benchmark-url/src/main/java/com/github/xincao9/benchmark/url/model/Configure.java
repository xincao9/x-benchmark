package com.github.xincao9.benchmark.url.model;

import java.util.List;

/**
 *
 * @author 510655387@qq.com
 */
public class Configure {

    private Professor professor;
    private Login login;
    private List<Request> flow;
    private Source source;
    private static Configure configure;

    public static Configure getConfigure() {
        return configure;
    }

    public static void setConfigure(Configure configure) {
        Configure.configure = configure;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
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

}
