package org.chance.pattern.creational.singleton;

/**
 * Singleton1
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/6/14
 */ //饿汉式单例
class Singleton1{
    private Singleton1(){}
    private static final Singleton1 single = new Singleton1();

    public static Singleton1 getInstance(){
        return single;
    }
}
