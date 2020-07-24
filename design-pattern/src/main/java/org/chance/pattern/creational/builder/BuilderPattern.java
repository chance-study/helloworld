package org.chance.pattern.creational.builder;

/**
 * 建造者模式：是将一个复杂的对象的构建与它的表示分离，使得同样的构建过程可以创建不同的表示。
 * <p>
 * Builder：给出一个抽象接口，以规范产品对象的各个组成成分的建造。这个接口规定要实现复杂对象的哪些部分的创建，并不涉及具体的对象部件的创建。
 * ConcreteBuilder：实现Builder接口，针对不同的商业逻辑，具体化复杂对象的各部分的创建。 在建造过程完成后，提供产品的实例。
 * Director：调用具体建造者来创建复杂对象的各个部分，在指导者中不涉及具体产品的信息，只负责保证对象各部分完整创建或按某种顺序创建。
 * Product：要创建的复杂对象。
 *
 * 指挥者（Director）直接和客户（Client）进行需求沟通；
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-14 18:18:57
 */
public class BuilderPattern {

    interface Product {
    }

    interface Part {
    }

    interface Builder {

        void buildPartA();

        void buildPartB();

        void buildPartC();

        Product getResult();

    }

    static class ConcreteBuilder implements Builder {

        Part partA, partB, partC;

        @Override
        public void buildPartA() {
            System.out.println("ConcreteBuilder.buildPartA");
        }

        @Override
        public void buildPartB() {
            System.out.println("ConcreteBuilder.buildPartB");
        }

        @Override
        public void buildPartC() {
            System.out.println("ConcreteBuilder.buildPartC");
        }

        @Override
        public Product getResult() {
            System.out.println("ConcreteBuilder.getResult");
            return null;
        }
    }

    static class Director {
        private Builder builder;

        Director(Builder builder) {
            this.builder = builder;
        }

        void construct() {
            builder.buildPartA();
            builder.buildPartB();
            builder.buildPartC();
        }
    }

    static class Client {
        public static void main(String[] args) {
            ConcreteBuilder builder = new ConcreteBuilder();
            Director director = new Director(builder);
            director.construct();
            Product product = builder.getResult();

        }
    }

}
