package org.chance.pattern.factory.simple;

/**
 * Created by gengchao on 16/4/29.
 * 简单工厂模式（Simple Factory Pattern）属于类的创新型模式，又叫静态工厂方法模式（Static FactoryMethod Pattern）,是通过专门定义一个类来负责创建其他类的实例，
 * 被创建的实例通常都具有共同的父类。
 * 工厂角色（Creator）：这是简单工厂模式的核心，由它负责创建所有的类的内部逻辑。当然工厂类必须能够被外界调用，创建所需要的产品对象。
 * 抽象（Product）产品角色：简单工厂模式所创建的所有对象的父类，注意，这里的父类可以是接口也可以是抽象类，它负责描述所有实例所共有的公共接口。
 * 具体产品（Concrete Product）角色：简单工厂所创建的具体实例对象，这些具体的产品往往都拥有共同的父类。
 */
public class SimpleFactoryPattern {
    public static void main(String[] args) {
        Product a = Creator.getProduct("A");
        Product b = Creator.getProduct("B");
        a.op();
        b.op();

    }
}


