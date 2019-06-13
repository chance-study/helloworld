package org.chance.pattern.factory.simple;

/**
 * ConcreteProductA
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/6/14
 */
class ConcreteProductA implements Product {

    @Override
    public void op() {
        System.out.println("ConcreteProductA.op");
    }
}
