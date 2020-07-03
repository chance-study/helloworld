package org.chance.pattern.structural.decorator.demo1;

/**
 * #2 创建实现接口的实体类。
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-02 17:27:37
 */
public class Rectangle implements Shape {

    @Override
    public void draw() {
        System.out.println("Shape: Rectangle");
    }
}
