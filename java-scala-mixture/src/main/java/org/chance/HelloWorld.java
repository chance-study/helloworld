package org.chance;

/**
 * Created by Administrator on 2016/9/22.
 */
public class HelloWorld {

    public static void main(String[] args) {
        Hello h = new Hello();
        h.sayHello("scala");
    }

    public static void sayHello(String arg){
        System.out.println("Hello,"+arg);
    }

}
