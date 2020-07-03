package org.chance.pattern.structural.decorator.pattern2;

/**
 * 如果只有一个ConcreteComponent类，那么可以考虑去掉抽象的Component类(接口)，把Decorator作为一个ConcreteComponent的子类。
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-03 11:25:21
 */
public class ConcreteComponent {

    public void op() {
        System.out.println("ConcreteComponent1.op");
    }

}
