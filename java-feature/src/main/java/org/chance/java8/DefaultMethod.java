package org.chance.java8;

import java.util.Comparator;
import java.util.function.Function;

/**
 * Created by gengchao on 16/6/7.
 */
public class DefaultMethod {
    public interface Formula {
        double calculate(int a);

        /**
         * java 8 新特性
         * Java 8允许我们给接口添加一个非抽象的方法实现，只需要使用 default关键字即可，
         * 这个特征又叫做扩展方法
         * */
        default double sqrt(int a) {
            return Math.sqrt(a);
        }


        /**
         * 除了默认方法，Java SE 8还在允许在接口中定义静态方法。
         * 这使得我们可以从接口直接调用和它相关的辅助方法（Helper method），
         * 而不是从其它的类中调用（之前这样的类往往以对应接口的复数命名
         */
        static <T, U extends Comparable<? super U>>
        Comparator<T> comparing(Function<T, U> keyExtractor) {
            return (c1, c2) -> keyExtractor.apply(c1).compareTo(keyExtractor.apply(c2));
        }
    }

    public interface IA {
        default void fun() {
            System.out.println("IA.fun");
        }
    }

    public interface IB1 extends IA {
        @Override
        default void fun() {
            System.out.println("IB1.fun");
        }
    }

    public interface IB2 extends IA {
        @Override
        default void fun() {
            System.out.println("IB2.fun");
        }
    }

    /**
     * 多个接口提供默认方法，则“最具体接口”默认方法胜出，但是不得出现多个“最具体接口”。
     */
    public static class C implements IB1, IB2 {
        @Override
        public void fun() {
            System.out.println("C.fun");
//            IB2.super.fun();
        }

        public static void main(String[] args) {
            new C().fun();
        }
    }

    public static class Clent {
        public static void main(String[] args) {
            Formula formula = new Formula() {

                @Override
                public double calculate(int a) {
                    return sqrt(a * 100);
                }
            };
            System.out.println(formula.calculate(100));
            System.out.println(formula.sqrt(16));
        }

    }
}


