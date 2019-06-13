package org.chance.pattern.proxy;

/**
 * RealSubject
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/6/14
 */ //具体主题角色
class RealSubject implements Subject{

    @Override
    public void op() {
        System.out.println("RealSubject.op");
    }
}
