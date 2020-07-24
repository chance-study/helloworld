package org.chance.pattern.creational.prototype.show1;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-15 15:19:01
 */
public class ConcretePrototypeB implements Prototype {

    @Override
    public Prototype clone() {
        Prototype prototype = new ConcretePrototypeB();
        return prototype;
    }

}
