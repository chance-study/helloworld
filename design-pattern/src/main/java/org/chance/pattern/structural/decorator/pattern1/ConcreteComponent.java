package org.chance.pattern.structural.decorator.pattern1;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-03 11:00:32
 */
public class ConcreteComponent implements Component {

    @Override
    public void op() {
        System.out.println("ConcreteComponent.op");
    }
}
