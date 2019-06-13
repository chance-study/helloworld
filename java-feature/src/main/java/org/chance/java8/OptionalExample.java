package org.chance.java8;

import java.util.Optional;

/**
 * Created by GengChao on 2016/9/14.
 *  <ul>
 *      <li>{@link java.util.Optional}</li>
 *  </ul>
 * @version 1.0.0
 * @author GengChao
 */
public class OptionalExample {

    private static class OptionalCleint{
        public static void main(String[] args) {
            String s=null;
            String s1= "helloworld";
            // 三种构造方式
            //null 报错
//          Optional<String> os = Optional.of(s);
            //可以传递null
            Optional<String> os2 = Optional.ofNullable(s1);
            // null Optional
            Optional osNull = Optional.empty();


//          os.ifPresent(System.out::print);
            os2.ifPresent(o-> System.out.println(o.toString()));
            os2.filter(p->false).ifPresent(System.out::println);
            os2.filter(p->true).ifPresent(System.out::println);
            os2.map(String::toUpperCase).ifPresent(System.out::println);
            os2.flatMap(m->Optional.of(m)).ifPresent(System.out::println);

            Optional<String> os3 = Optional.ofNullable(null);
            System.out.println(os3.orElse("other1"));
            System.out.println(os3.orElseGet(()->"other2"));
//            os3.orElseThrow(()->new NullPointerException("null"));


        }
    }

}
