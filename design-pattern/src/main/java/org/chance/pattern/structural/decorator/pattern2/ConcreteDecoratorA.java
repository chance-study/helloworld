package org.chance.pattern.structural.decorator.pattern2;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-03 11:14:48
 */
public class ConcreteDecoratorA extends Decorator {

    public ConcreteDecoratorA(ConcreteComponent concreteComponent) {
        super(concreteComponent);
    }

    @Override
    public void op() {
        super.op();
        System.out.println("ConcreteDecoratorA.op");
    }

}
