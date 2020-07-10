package org.chance.spring.feature.aop.demo1;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-09 14:42:32
 */
public class Client {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AnnotationConfig.class);
        Person person = (Person) ctx.getBean("women");
        person.likePerson();
        Animal animal = (Animal) person;
        animal.eat();
    }
}
