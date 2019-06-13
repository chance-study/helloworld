package org.chance;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by gengchao on 16/5/1.
 */
public class ReflectStudy {
    public static void main(String[] args) throws ClassNotFoundException {
        /***获取字节码的三种方式 */
        Class c=String.class;
        String str= "aaa";
        Class c1 =  str.getClass();
        Class c2 =  Class.forName("java.lang.String");
        System.out.println(c==c1);
        System.out.println(c1 == c2 );


        /***/
        Method[] methods=c1.getDeclaredMethods();
        Field[] fields = c1.getDeclaredFields();

//        Arrays.asList(methods).forEach((method)->{
//            System.out.println(method.toString());
//        });
//
//        Arrays.asList(fields).forEach((field)->{
//            System.out.println(field.toString());
//        });

        String[] ss=new String[2];
        int[][] ii= new int[3][4];
        float[][] ff= new float[3][4];
        Class cc=ss.getClass();
        System.out.println(cc.getName());
        System.out.println(ii.getClass().getName());
        System.out.println(ff.getClass().getName());
        System.out.println(cc.getComponentType());

        cc.getAnnotations();




    }
}
