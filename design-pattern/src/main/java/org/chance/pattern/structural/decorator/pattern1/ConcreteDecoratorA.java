package org.chance.pattern.structural.decorator.pattern1;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-03 11:00:41
 */
public class ConcreteDecoratorA extends Decorator {

    public ConcreteDecoratorA(Component component) {
        super(component);
    }

    @Override
    public void op() {
        super.op();
        System.out.println("ConcreteDecoratorA.op");
    }

}
