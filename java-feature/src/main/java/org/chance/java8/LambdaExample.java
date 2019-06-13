package org.chance.java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.IntFunction;

/**
 * Created by gengchao on 16/6/14.
 */
public class LambdaExample {
    //我们把这些只拥有一个方法的接口称为函数式接口。
    //@FunctionalInterface注解来显式指定一个接口是函数式接口（以避免无意声明了一个符合函数式标准的接口）
    private static class Hello{
        Runnable r1 = ()->{System.out.println(this);};
        Runnable r2 = ()-> System.out.println(toString());

        @Override
        public String toString() {
            return "hello, world";
        }

        Callable<String> helloCallable(String name){
            String hello = "Hello";
//            hello = "aaa";
            return ()-> hello+", " + name;
        }

        public static void main(String[] args) {
            new Hello().r1.run();
            new Hello().r2.run();

            //
            int sum = 0;
            List<Integer> aList = new ArrayList<>();
            List<String> list = Arrays.asList("1", "2", "3", "4");
            list.forEach(
//                    e ->{ sum+=e.length();}  //Illegal, close over values
                    e -> {
                        aList.add(e.length());
                    }  //Legal, open over variables
            );

            int sum1 = list.stream()
                    .mapToInt(e->e.length())
                    .sum();
            int sum2 = list.stream()
                    .mapToInt(e->e.length())
                    .reduce(0, (x,y)->x+y);


            /***/
            Person[] people = {
                    new Person("hh",12),
                    new Person("cd",15)
            };
//            Comparator<Person> byName = Comparator.comparing(p->p.getName());
            Comparator<Person> byName = Comparator.comparing(Person::getName);

            Arrays.sort(people,byName);


            IntFunction<int[]> arrayMaker = int[]::new;
            int[] array = arrayMaker.apply(10);


        }
    }

    public static class Person{
        private final String name;
        private final int age;


        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }
}
