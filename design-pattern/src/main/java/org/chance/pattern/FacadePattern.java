package org.chance.pattern;

/**
 * Created by gengchao on 16/4/30.
 * 为子系统中的一组接口提供一个统一接口。Facade模式定义了一个高层接口，这个接口使得这子系统更容易使用。
 * 门面(Facade)角色 ：客户端可以调用这个角色的方法。此角色知晓相关的（一个或者多个）子系统的功能和责任。
 * 在正常情况下，本角色会将所有从客户端发来的请求委派到相应的子系统去。
 * 子系统(SubSystem)角色 ：可以同时有一个或者多个子系统。每个子系统都不是一个单独的类，
 * 而是一个类的集合（如上面的子系统就是由ModuleA、ModuleB、ModuleC三个类组合而成）。
 * 每个子系统都可以被客户端直接调用，或者被门面角色调用。子系统并不知道门面的存在，对于子系统而言，
 * 门面仅仅是另外一个客户端而已。
 */
public class FacadePattern {

    static class ModuleA {
        //提供给子系统外部使用的方法
        public void op(){
            System.out.println("ModuleA.op");
        }
        //子系统内部模块之间相互调用时使用的方法
        private void op1(){
            System.out.println("ModuleA.op1");
        }
    }

    static class ModuleB{
        void op(){
            System.out.println("ModuleB.op");
        }
    }

    static class ModuleC{
        void op(){
            System.out.println("ModuleC.op");
        }
    }

    static class Facade {
        void op(){
            ModuleA a =new ModuleA();
            a.op();
            ModuleB b = new ModuleB();
            b.op();
            ModuleC c = new ModuleC();
            c.op();

        }
    }

    static class Client {
        public static void main(String[] args) {
            Facade facade = new Facade();
            facade.op();
        }
    }
}
