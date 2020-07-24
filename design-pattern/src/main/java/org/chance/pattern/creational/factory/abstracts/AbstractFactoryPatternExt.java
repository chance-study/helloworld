package org.chance.pattern.creational.factory.abstracts;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-14 14:53:14
 */
public class AbstractFactoryPatternExt extends AbstractFactoryPattern {

    static class ConcreteProductA3 implements ProductA {

        @Override
        public void opA() {
            System.out.println("ConcreteProductA3.opA");
        }
    }

    static class ConcreteProductB3 implements ProductB {

        @Override
        public void opB() {
            System.out.println("ConcreteProductB3.opB");
        }
    }

    static class ConcreteFactory3 implements AbstractFactory {

        @Override
        public ProductA getProductA() {
            return new ConcreteProductA3();
        }

        @Override
        public ProductB getProductB() {
            return new ConcreteProductB3();
        }
    }

    static class Client {
        public static void main(String[] args) {
            AbstractFactory factory3 = new ConcreteFactory3();
            ProductA productA3 = factory3.getProductA();
            ProductB productB3 = factory3.getProductB();
            productA3.opA();
            productB3.opB();

        }
    }
}
