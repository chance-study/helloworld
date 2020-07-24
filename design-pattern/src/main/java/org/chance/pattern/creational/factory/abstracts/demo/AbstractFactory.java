package org.chance.pattern.creational.factory.abstracts.demo;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-14 15:33:15
 */
public abstract class AbstractFactory {

    public abstract Color getColor(String color);

    public abstract Shape getShape(String shape);

}
