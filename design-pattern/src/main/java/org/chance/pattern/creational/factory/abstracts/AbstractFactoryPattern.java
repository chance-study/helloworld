package org.chance.pattern.creational.factory.abstracts;

/**
 * 抽象工厂模式是所有形态的工厂模式中最为抽象和最具一般性的一种形态。抽象工厂模式是指当有多个抽象角色时，使用的一种工厂模式。
 * <p>
 * 抽象工厂模式的主要角色:
 * 抽象产品（Product）：定义了产品的规范，描述了产品的主要特性和功能，抽象工厂模式有多个抽象产品。
 * 具体产品（ConcreteProduct）：实现了抽象产品角色所定义的接口，由具体工厂来创建，它 同具体工厂之间是多对一的关系。
 * 抽象工厂（Abstract Factory）：提供了创建产品的接口，它包含多个创建产品的方法 newProduct()，可以创建多个不同等级的产品。
 * 具体工厂（Concrete Factory）：主要是实现抽象工厂中的多个抽象方法，完成具体产品的创建。
 * <p>
 * <p>
 * 使用抽象工厂模式一般要满足以下条件。
 * 系统中有多个产品族，每个具体工厂创建同一族但属于不同等级结构的产品。
 * 系统一次只可能消费其中某一族产品，即同族的产品一起使用。
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-14 14:53:14
 */
public class AbstractFactoryPattern {

    interface ProductA {
        void opA();
    }

    interface ProductB {
        void opB();
    }

    static class ConcreteProductA1 implements ProductA {

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

    interface AbstractFactory {
        ProductA getProductA();

        ProductB getProductB();
    }

    static class ConcreteFactory1 implements AbstractFactory {

        @Override
        public ProductA getProductA() {
            return new ConcreteProductA1();
        }

        @Override
        public ProductB getProductB() {
            return new ConcreteProductB1();
        }
    }

    static class ConcreteFactory2 implements AbstractFactory {

        @Override
        public ProductA getProductA() {
            return new ConcreteProductA2();
        }

        @Override
        public ProductB getProductB() {
            return new ConcreteProductB2();
        }
    }

    static class Client {
        public static void main(String[] args) {
            AbstractFactory factory1 = new ConcreteFactory1();
            ProductA productA1 = factory1.getProductA();
            ProductB productB1 = factory1.getProductB();
            productA1.opA();
            productB1.opB();

            AbstractFactory factory2 = new ConcreteFactory2();
            ProductA productA2 = factory2.getProductA();
            ProductB productB2 = factory2.getProductB();
            productA2.opA();
            productB2.opB();


        }
    }
}
