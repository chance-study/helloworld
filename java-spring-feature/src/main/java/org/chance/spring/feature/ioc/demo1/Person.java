package org.chance.spring.feature.ioc.demo1;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-08-05 16:56:53
 */
public class Person {

    private String name;

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
