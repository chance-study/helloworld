package org.chance.pattern.decorator;

/**
 * Decorator1
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/6/14
 */
class Decorator1 extends ConcreteComponent1{

    ConcreteComponent1 concreteComponent1;
    public Decorator1(ConcreteComponent1 concreteComponent1){
        this.concreteComponent1=concreteComponent1;
    }

    public void op(){
        super.op();
        System.out.println("Decorator1.op");
    }
}
