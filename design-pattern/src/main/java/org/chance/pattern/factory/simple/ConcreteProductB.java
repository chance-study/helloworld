package org.chance.pattern.factory.simple;

/**
 * ConcreteProductB
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/6/14
 */
class ConcreteProductB implements Product {

    @Override
    public void op() {
        System.out.println("ConcreteProductB.op");
    }
}
