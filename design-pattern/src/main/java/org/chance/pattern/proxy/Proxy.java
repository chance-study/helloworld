package org.chance.pattern.proxy;

/**
 * Proxy
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/6/14
 */ //代理主题角色
class Proxy implements Subject{

    private Subject subject;

    Proxy(Subject subject){
        this.subject = subject;
    }

    @Override
    public void op() {
        System.out.println("before");
        subject.op();
        System.out.println("after");
    }
}
