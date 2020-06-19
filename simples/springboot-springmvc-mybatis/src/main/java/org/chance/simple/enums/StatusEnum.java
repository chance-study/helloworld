package org.chance.simple.enums;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-06-17 17:20:23
 */
public enum StatusEnum {

    /**
     *
     */
    ENABLE(0, "启用"),

    /**
     *
     */
    DISABLE(1, "禁用");

    private int code;

    private String desc;

    StatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer code() {
        return code;
    }

    public String desc() {
        return desc;
    }

    @Override
    public String toString() {
        return code + ":" + desc;
    }
}
