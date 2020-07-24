package org.chance.pattern.creational.factory.simple;

/**
 * 工厂角色（Creator）：这是简单工厂模式的核心，由它负责创建所有的类的内部逻辑。当然工厂类必须能够被外界调用，创建所需要的产品对象。
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-14 14:57:12
 */
public class Creator {

    public static Product getProduct(String type) {
        switch (type) {
            case "A":
                return new ConcreteProductA();
            case "B":
                return new ConcreteProductB();
            default:
                return null;
        }
    }

}
