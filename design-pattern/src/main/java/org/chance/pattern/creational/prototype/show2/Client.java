package org.chance.pattern.creational.prototype.show2;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-15 15:27:15
 */
public class Client {
    public static void main(String[] args) {

        ConcretePrototype concretePrototype = new ConcretePrototype();

        for (int i = 0; i < 3; i++) {
            ConcretePrototype copy = (ConcretePrototype) concretePrototype.clone();
            copy.show();
        }
    }
}
