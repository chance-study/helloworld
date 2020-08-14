package org.chance.beans;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

/**
 * Introspector 是操作 javaBean 的 API，用来访问某个属性的 getter/setter 方法。
 * 对于一个标准的 javaBean 来说，它包括属性、get 方法和 set 方法，这是一个约定俗成的规范。为此 sun 提供了 Introspector 工具包，来使开发者更好或者更灵活的操作 javaBean。
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-29 14:57:43
 */
public class IntrospectorDemo {

    public static void main(String[] args) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        // 获取整个Bean的信息
        // BeanInfo beanInfo= Introspector.getBeanInfo(user.getClass());
        // 在Object类时候停止检索，可以选择在任意一个父类停止
        BeanInfo beanInfo = Introspector.getBeanInfo(User.class, Object.class);

        System.out.println("所有属性描述：");
        // 获取所有的属性描述
        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor propertyDescriptor : pds) {
            System.out.println(propertyDescriptor.getName());
        }
        System.out.println("所有方法描述：");
        for (MethodDescriptor methodDescriptor : beanInfo.getMethodDescriptors()) {
            System.out.println(methodDescriptor.getName());
            // Method method = methodDescriptor.getMethod();
        }

        //
        System.out.println(Introspector.decapitalize("HaoRenKa")); // haoRenKa

        //
        User user = new User("jack", 21);
        String propertyName = "name";
        PropertyDescriptor namePd = new PropertyDescriptor(propertyName, User.class);
        System.out.println("名字：" + namePd.getReadMethod().invoke(user));
        namePd.getWriteMethod().invoke(user, "tom");
        System.out.println("名字：" + namePd.getReadMethod().invoke(user));
        System.out.println("========================================");
        String agePropertyName = "age";
        PropertyDescriptor agePd = new PropertyDescriptor(agePropertyName, User.class);
        System.out.println("年龄：" + agePd.getReadMethod().invoke(user));
        agePd.getWriteMethod().invoke(user, 22);
        System.out.println("年龄：" + agePd.getReadMethod().invoke(user));

    }


    private static class User {

        private String name;
        private int age;

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

}
