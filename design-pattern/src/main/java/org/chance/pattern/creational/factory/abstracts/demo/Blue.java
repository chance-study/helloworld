package org.chance.pattern.creational.factory.abstracts.demo;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-14 15:30:06
 */
public class Blue implements Color {

    @Override
    public void fill() {
        System.out.println("Inside Blue::fill() method.");
    }
}
