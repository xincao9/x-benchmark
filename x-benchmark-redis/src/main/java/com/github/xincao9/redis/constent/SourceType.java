package com.github.xincao9.redis.constent;

/**
 *
 * @author 510655387@qq.com
 */
public enum SourceType {

    SEQUENCE(1, "SEQUENCE"),
    FILE(1, "FILE");
    private int id;
    private String name;

    private SourceType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static SourceType fromName(String name) {
        for (SourceType sourceType : values()) {
            if (sourceType.getName().equalsIgnoreCase(name)) {
                return sourceType;
            }
        }
        return null;
    }
}
