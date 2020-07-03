package org.chance.pattern.structural.decorator.pattern2;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-03 11:24:12
 */
public class Decorator extends ConcreteComponent {

    private ConcreteComponent concreteComponent;

    public Decorator(ConcreteComponent concreteComponent) {
        this.concreteComponent = concreteComponent;
    }

    @Override
    public void op() {
        super.op();
        System.out.println("Decorator.op");
    }
}
