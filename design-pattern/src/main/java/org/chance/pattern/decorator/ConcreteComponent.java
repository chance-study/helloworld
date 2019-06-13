package org.chance.pattern.decorator;

/**
 * ConcreteComponent
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/6/14
 */
class ConcreteComponent implements Component2{

    @Override
    public void op() {
        System.out.println("ConcreteComponent.op");
    }
}
