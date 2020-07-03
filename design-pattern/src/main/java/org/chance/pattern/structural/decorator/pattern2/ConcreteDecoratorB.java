package org.chance.pattern.structural.decorator.pattern2;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-03 11:25:05
 */
public class ConcreteDecoratorB extends Decorator {

    public ConcreteDecoratorB(ConcreteComponent concreteComponent) {
        super(concreteComponent);
    }

    @Override
    public void op() {
        super.op();
        System.out.println("ConcreteDecoratorB.op");
    }
}
