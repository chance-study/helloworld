package org.chance.pattern.structural.facade.demo;

/**
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-03 15:33:05
 */
public class Circle implements Shape {
    @Override
    public void draw() {
        System.out.println("Circle::draw()");
    }
}
