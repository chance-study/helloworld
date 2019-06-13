package org.chance;

/**
 * 类修饰符
 * public  公共类
 * default 默认class前面什么都没有
 * abstruct 抽象类
 * final 不能被继承的类
 * static 静态类
 * private 只能修饰内部类，
 *              类内部     本包      子类      外部包
 * public          o        o           o       o
 * protected       o        o           o
 * default         o        o
 * private         o
 * Created by wqg on 2016/4/21.
 */
public class A {
    public static void main(String[] args) {
        B b=new B();
        //内部类的实例化
        B.B1 b1 =  b.new B1();
        B.B3 b3 = new B().new B3();

        //静态内部类的实例化
        B.B4 b4 = new B.B4();

        C1 c1 = new C1();
        c1.op();
    }
}

interface I{
    void op();
}

interface I2 extends I{
    default public void op(){

        System.out.println("I2.op");
    }
}
class C1 implements I2{

    @Override
    public void op() {
        System.out.println("C1.op");
    }
}

class B {

    public static int ii= 0;
    public int iii;

    public B(){}

    public B(String b){

    }

    public void fun1() throws NullPointerException{

        int  i=1;
        final class B22{
            int ii = i;
        }

        B22 b22=new B22();

    }

    private void fun2(){
//        B22 不可见
    }

    //内部类可以定义在方法里面也可以定义在方法外面
    public class B1{
        //内部类不能定义静态成员
//        public static final int INT_B1;
//        public static void fun1();
        public void fun1(){
            ii = 1;
            iii = 1;
        }

    }
    private class B2{}
    class B3{}
    static class B4{}

    public static class B5{
        public static final int INT_B5=0;
    }
}

final class BB extends B{

//    public BB(String b) {
//        super(b);
//    }

    public void fun1(){

    }

    public void fun2(){

    }

    static class BB1{

    }
}

abstract class AB{}

interface C {

}
