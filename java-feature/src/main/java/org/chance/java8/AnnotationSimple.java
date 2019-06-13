package org.chance.java8;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Target;

/**
 * Created by gengchao on 16/6/20.
 */
public class AnnotationSimple {

    @interface Hints{
        Hint[] value();
    }

    @Repeatable(Hints.class)
    @interface Hint{
        String value();
    }

    //Old Method
    @Hints({@Hint("hint1"),@Hint("hint2")})
    class Person{}

    // New Method

    @Hint("hint1")
    @Hint("hint2")
    class Person1{}

    static class Client{
        public static void main(String[] args) {
            Hint hint = Person1.class.getAnnotation(Hint.class);
            System.out.println("hint = " + hint);

            Hints hints1 = Person.class.getAnnotation(Hints.class);
            System.out.println("hints1 = " + hints1.value().length);

            Hint[] hints2 = Person1.class.getAnnotationsByType(Hint.class);
            System.out.println("hints = " + hints2.length);


        }
    }

    @Target({ElementType.TYPE_PARAMETER,ElementType.TYPE_USE})
    @interface MyAnnotation{}

}
