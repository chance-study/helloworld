package org.chance.pattern.creational.singleton;

/**
 * 饿汉式单例
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-15 15:02:04
 */
public class Singleton1 {
    private Singleton1() {
    }

    private static final Singleton1 single = new Singleton1();

    public static Singleton1 getInstance() {
        return single;
    }
}
