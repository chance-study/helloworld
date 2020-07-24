package org.chance.pattern.creational.prototype.show1;

/**
 * 简单形式的原型模式
 * 客户(Client)角色：客户类提出创建对象的请求。
 * 抽象原型(Prototype)角色：这是一个抽象角色，通常由一个Java接口或Java抽象类实现。此角色给出所有的具体原型类所需的接口。
 * 具体原型（Concrete Prototype）角色：被复制的对象。此角色需要实现抽象的原型角色所要求的接口。
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-15 15:12:08
 */
public interface Prototype {

    /**
     * 克隆自身的方法
     *
     * @return 一个从自身克隆出来的对象
     */
    Prototype clone();

}
