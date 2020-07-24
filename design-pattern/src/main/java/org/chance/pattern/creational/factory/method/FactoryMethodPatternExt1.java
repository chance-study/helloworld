package org.chance.pattern.creational.factory.method;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-14 14:52:53
 */
public class FactoryMethodPatternExt1 extends FactoryMethodPattern {

    /***/
    interface Factory {
        <T extends Product> T createProduct(Class<T> c);
    }

    static class ConcreteFactory implements Factory {

        @Override
        public <T extends Product> T createProduct(Class<T> c) {
            T product = null;
            try {
                product = (T) Class.forName(c.getName()).newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return product;
        }
    }

    static class Client {
        public static void main(String[] args) {

            /***/
            Factory factory = new ConcreteFactory();
            Product product1 = factory.createProduct(ProductA.class);
            product1.op();
        }
    }


}





