package org.chance.pattern.adapter;

/**
 * Adapter
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/6/14
 */ /* 源类Adaptee没有方法op2 因此适配器补充上这个方法  */
class Adapter extends Adaptee implements Target {
    @Override
    public void op2() {
        System.out.println("Adapter.op2");
    }
}
