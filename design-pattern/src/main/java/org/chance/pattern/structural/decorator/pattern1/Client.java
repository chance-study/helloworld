package org.chance.pattern.structural.decorator.pattern1;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-03 10:54:09
 */
public class Client {

    public static void main(String[] args) {

        Component c = new ConcreteComponent();
        Component c1 = new ConcreteDecoratorA(c);
        Component c2 = new ConcreteDecoratorB(c1);

        c.op();
        System.out.println();
        c1.op();
        System.out.println();
        c2.op();
    }

}
