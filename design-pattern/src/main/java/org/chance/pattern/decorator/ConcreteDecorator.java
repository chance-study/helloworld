package org.chance.pattern.decorator;

/**
 * ConcreteDecorator
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/6/14
 */
class ConcreteDecorator implements Component2{
    Component2 component2;

    public ConcreteDecorator(Component2 component2){
        this.component2=component2;
    }

    @Override
    public void op() {
        component2.op();
        System.out.println("ConcreteDecorator.op");
    }
}
