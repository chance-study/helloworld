package org.chance.pattern;

/**
 * 重要应用 Servlet中的应用
 * Created by gengchao on 16/4/30.
 * 模板方法模式是类的行为模式。准备一个抽象类，
 * 将部分逻辑以具体方法以及具体构造函数的形式实现，
 * 然后声明一些抽象方法来迫使子类实现剩余的逻辑。
 * 不同的子类可以以不同的方式实现这些抽象方法，
 * 从而对剩余的逻辑有不同的实现。这就是模板方法模式的用意。
 * 抽象模板(Abstract Template)角色有如下责任：
定义了一个或多个抽象操作，以便让子类实现。这些抽象操作叫做基本操作，它们是一个顶级逻辑的组成步骤。
定义并实现了一个模板方法。这个模板方法一般是一个具体方法，它给出了一个顶级逻辑的骨架，而逻辑的组成步骤在相应的抽象操作中，推迟到子类实现。顶级逻辑也有可能调用一些具体方法。
 * 具体模板(Concrete Template)角色又如下责任：
实现父类所定义的一个或多个抽象方法，它们是一个顶级逻辑的组成步骤。
每一个抽象模板角色都可以有任意多个具体模板角色与之对应，而每一个具体模板角色都可以给出这些抽
 */
public class TemplateMethodPattern {

    static abstract class AbstractTemplate{
        //模版方法
        public final void templateMethod(){

            System.out.println("AbstractTemplate.templateMethod");
            //调用方法的基本骨架
            abstraceMethod();
            hookMethod();
            concreteMethod();

        }

        //基本方法的声明(由子类实现)
        protected abstract void abstraceMethod();
        //基本方法(空方法) 钩子方法一般以do开头 servlet中的doGet doPost
        protected void hookMethod(){}
        //基本方法(已经实现)
        private final void concreteMethod(){
            System.out.println("AbstractTemplate.concreteMethod");
        }

    }

    static class ConcreteTemplate extends AbstractTemplate{

        //基本方法的实现
        @Override
        protected void abstraceMethod() {
            System.out.println("ConcreteTemplate.abstraceMethod");
        }

        //重写父类的方法
        public void hookMethod(){
            System.out.println("ConcreteTemplate.hookMethod");
        }
    }

    static class Client {
        public static void main(String[] args) {
            AbstractTemplate abstractTemplate =
                    new ConcreteTemplate();
            abstractTemplate.templateMethod();

        }
    }

}
