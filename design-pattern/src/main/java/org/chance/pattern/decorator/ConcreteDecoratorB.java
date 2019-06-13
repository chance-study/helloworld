package org.chance.pattern.decorator;

/**
 * ConcreteDecoratorB
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/6/14
 */
class ConcreteDecoratorB extends Decorator1{

    public ConcreteDecoratorB(ConcreteComponent1 concreteComponent1) {
        super(concreteComponent1);
    }
    public void op(){
        super.op();
        System.out.println("ConcreteDecoratorB.op");
    }
}
