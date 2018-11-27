package com.github.xincao9.benchmark.url.constent;

/**
 *
 * @author 510655387@qq.com
 */
public enum RequestMethod {

    GET(1, "GET"),
    POST(2, "POST");
    private int id;
    private String name;

    private RequestMethod(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static RequestMethod fromName(String name) {
        for (RequestMethod requestMethod : values()) {
            if (requestMethod.getName().equalsIgnoreCase(name)) {
                return requestMethod;
            }
        }
        return null;
    }
}
