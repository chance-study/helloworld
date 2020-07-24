package org.chance.pattern.creational.prototype.show3;

/**
 * 登记形式的原型模式
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-15 15:32:36
 */
public interface Prototype {

    Prototype clone();

    String getName();

    void setName(String name);
}
