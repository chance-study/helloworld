package org.chance.pattern.creational.factory.simple;

/**
 * Creator
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/6/14
 */
class Creator {

    public static Product getProduct(String type){
        switch(type){
            case "A":return new ConcreteProductA();
            case "B":return new ConcreteProductB();
            default:return null;
        }
    }

}
