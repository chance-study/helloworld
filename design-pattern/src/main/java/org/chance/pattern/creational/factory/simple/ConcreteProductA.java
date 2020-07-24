package org.chance.pattern.creational.factory.simple;

/**
 * 具体产品（Concrete Product）角色：简单工厂所创建的具体实例对象，这些具体的产品往往都拥有共同的父类。
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-14 14:58:13
 */
public class ConcreteProductA implements Product {

    @Override
    public void op() {
        System.out.println("ConcreteProductA.op");
    }
}
