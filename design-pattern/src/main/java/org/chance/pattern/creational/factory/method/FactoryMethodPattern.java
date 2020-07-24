package org.chance.pattern.creational.factory.method;

/**
 * 工厂方法模式
 * 抽象工厂(Creator)角色：是工厂方法模式的核心，与应用程序无关。任何在模式中创建的对象的工厂类必须实现这个接口。
 * 具体工厂(Concrete Creator)角色：这是实现抽象工厂接口的具体工厂类，包含与应用程序密切相关的逻辑，并且受到应用程序调用以创建产品对象。在上图中有两个这样的角色：BulbCreator与TubeCreator。
 * 抽象产品(Product)角色：工厂方法模式所创建的对象的超类型，也就是产品对象的共同父类或共同拥有的接口。在上图中，这个角色是Light。
 * 具体产品(Concrete Product)角色：这个角色实现了抽象产品角色所定义的接口。某具体产品有专门的具体工厂创建，它们之间往往一一对应。
 * <p>
 * 就是通过对应的工厂类来生成对应的产品类
 * <p>
 * 工厂方法模式的主要优点有：
 * 用户只需要知道具体工厂的名称就可得到所要的产品，无须知道产品的具体创建过程；
 * 在系统增加新的产品时只需要添加具体产品类和对应的具体工厂类，无须对原工厂进行任何修改，满足开闭原则；
 * <p>
 * 其缺点是：每增加一个产品就要增加一个具体产品类和一个对应的具体工厂类，这增加了系统的复杂度。
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-14 14:52:53
 */
public class FactoryMethodPattern {

    interface Product {
        void op();
    }

    static class ProductA implements Product {

        @Override
        public void op() {
            System.out.println("ProductA.op");
        }
    }

    static class ProductB implements Product {

        @Override
        public void op() {
            System.out.println("ProductB.op");
        }
    }

    interface Creator {
        // factoryMethod 必须由子类去实现这个方法
        Product createProduct();
    }

    static class ConcreteCreatorA implements Creator {

        @Override
        public Product createProduct() {
            return new ProductA();
        }
    }

    static class ConcreteCreatorB implements Creator {

        @Override
        public Product createProduct() {
            return new ProductB();
        }
    }

    static class Client {

        public static void main(String[] args) {

            //用户只需要知道具体工厂的名称就可得到所要的产品，无须知道产品的具体创建过程；

            Product productA = new ConcreteCreatorA().createProduct();
            productA.op();

            Product productB = new ConcreteCreatorB().createProduct();
            productB.op();

        }
    }


}





