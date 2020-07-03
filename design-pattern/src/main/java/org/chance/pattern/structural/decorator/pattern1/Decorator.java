package org.chance.pattern.structural.decorator.pattern1;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-03 10:48:00
 */
public class Decorator implements Component {

    Component component;

    public Decorator(Component component) {
        this.component = component;
    }

    @Override
    public void op() {
        component.op();
        System.out.println("ConcreteDecorator.op");
    }
}
