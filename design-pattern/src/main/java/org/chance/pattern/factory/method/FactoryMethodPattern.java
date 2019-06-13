package org.chance.pattern.factory.method;

/**
 * Created by gengchao on 16/4/29.
 * 工厂方法模式
 * 抽象工厂(Creator)角色：是工厂方法模式的核心，与应用程序无关。任何在模式中创建的对象的工厂类必须实现这个接口。
 * 具体工厂(Concrete Creator)角色：这是实现抽象工厂接口的具体工厂类，包含与应用程序密切相关的逻辑，并且受到应用程序调用以创建产品对象。在上图中有两个这样的角色：BulbCreator与TubeCreator。
 * 抽象产品(Product)角色：工厂方法模式所创建的对象的超类型，也就是产品对象的共同父类或共同拥有的接口。在上图中，这个角色是Light。
 * 具体产品(Concrete Product)角色：这个角色实现了抽象产品角色所定义的接口。某具体产品有专门的具体工厂创建，它们之间往往一一对应。
 */
public class FactoryMethodPattern {

    static interface Product {
        void op();
    }

    static interface Creator {
        Product createProduct();
    }

    static class ProductA implements Product {

        @Override
        public void op() {
            System.out.println("ProductA.op");
        }
    }

    static class ConcreteCreator implements Creator {

        @Override
        public Product createProduct() {
            return new ProductA();
        }
    }

    static class Client {
        public static void main(String[] args) {
            Creator creator = new ConcreteCreator();
            Product product = creator.createProduct();
            product.op();

            /***/
            Factory factory=new ConcreteFactory();
            Product product1 = factory.createProduct(ProductA.class);
            product1.op();
        }
    }



    /***/
    static interface Factory {
        <T extends Product> T createProduct(Class<T> c);
    }

    static class ConcreteFactory implements Factory {

        @Override
        public <T extends Product> T createProduct(Class<T> c) {
            T product = null;
            try{
                product = (T)Class.forName(c.getName()).newInstance();
            }catch(Exception e){
                e.printStackTrace();
            }
            return product;
        }
    }


}





