package org.chance.pattern.factory.sbstracts;

/**
 * Created by gengchao on 16/4/29.
 * 抽象工厂模式是所有形态的工厂模式中最为抽象和最具一般性的一种形态。抽象工厂模式是指当有多个抽象角色时，使用的一种工厂模式。
 *
 */
public class AbstractFactoryPattern {

    static interface ProductA{
        void opA();
    }

    static interface ProductB{
        void opB();
    }

    static class ConcreteProductA1 implements ProductA{

        @Override
        public void opA() {
            System.out.println("ConcreteProductA1.opA");
        }
    }

    static class ConcreteProductA2 implements ProductA {

        @Override
        public void opA() {
            System.out.println("ConcreteProductA2.opA");
        }
    }

    static class ConcreteProductB1 implements ProductB {

        @Override
        public void opB() {
            System.out.println("ConcreteProductB1.opB");
        }
    }

    static class ConcreteProductB2 implements ProductB {

        @Override
        public void opB() {
            System.out.println("ConcreteProductB2.opB");
        }
    }

    static interface Factory1 {
        ProductA getProductA1();
        ProductB getProductB1();
    }

    static interface Factory2 {
        ProductA getProductA2();
        ProductB getProductB2();
    }

    static class ConcreteFactory1 implements Factory1 {

        @Override
        public ProductA getProductA1() {
            return new ConcreteProductA1();
        }

        @Override
        public ProductB getProductB1() {
            return new ConcreteProductB1();
        }
    }

    static class ConcreteFactory2 implements Factory2 {

        @Override
        public ProductA getProductA2() {
            return new ConcreteProductA2();
        }

        @Override
        public ProductB getProductB2() {
            return new ConcreteProductB2();
        }
    }

    static class Client {
        public static void main(String[] args) {
            Factory1 factory1 = new ConcreteFactory1();
            ProductA productA1 = factory1.getProductA1();
            ProductB productB1 = factory1.getProductB1();

            productA1.opA();
            productB1.opB();

            Factory2 factory2 = new ConcreteFactory2();
            ProductA productA2= factory2.getProductA2();
            ProductB productB2 = factory2.getProductB2();
            productA2.opA();
            productB2.opB();


        }
    }
}
