package org.chance;

import java.util.Vector;

/**
 * Created by gengchao on 16/4/24.
 */
public class JVMStudy {
    /////////
    //java 内存泄漏的原因

    static Vector v = new Vector(10);

    public static void main(String[] args) {
        for(int i=1;i<100;i++){
            Object o = new Object();
            v.add(o);
            //虽然将对象置空了,但是由于被静态变量应用,所以
            //不能被gc回收,造成内存泄漏!
            o=null;
        }



        //classloader

    }

}
