package org.chance.pattern.structural.decorator.pattern1;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-03 11:00:47
 */
public class ConcreteDecoratorB extends Decorator {

    public ConcreteDecoratorB(Component component) {
        super(component);
    }

    @Override
    public void op() {
        super.op();
        System.out.println("ConcreteDecoratorB.op");
    }

    /**
     * 半透明的装饰模式才能扩展功能
     */
    public void extend() {
        System.out.println("ConcreteDecoratorB.op");
    }

}
