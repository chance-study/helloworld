package org.chance.pattern.creational.factory.simple;

/**
 * 抽象（Product）产品角色：简单工厂模式所创建的所有对象的父类，注意，这里的父类可以是接口也可以是抽象类，它负责描述所有实例所共有的公共接口。
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-14 14:58:29
 */
public interface Product {
    void op();
}
