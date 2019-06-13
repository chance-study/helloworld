package org.chance.pattern.decorator;

/**
 * ConcreteDecoratorA
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/6/14
 */
class ConcreteDecoratorA extends Decorator1{

    public ConcreteDecoratorA(ConcreteComponent1 concreteComponent1) {
        super(concreteComponent1);
    }

    public void op(){
        super.op();
        System.out.println("ConcreteDecoratorA.op");
    }

}
