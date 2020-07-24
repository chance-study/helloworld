package org.chance.pattern.creational.prototype.show3;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-15 15:33:28
 */
public class ConcretePrototypeY implements Prototype {

    private String name;

    @Override
    public Prototype clone() {
        ConcretePrototypeY prototypeY = new ConcretePrototypeY();
        prototypeY.setName(this.name);
        return prototypeY;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

}
