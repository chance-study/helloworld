package org.chance.pattern.creational.prototype.show3;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-15 15:33:28
 */
public class ConcretePrototypeX implements Prototype {

    private String name;

    @Override
    public Prototype clone() {
        ConcretePrototypeX prototypeX = new ConcretePrototypeX();
        prototypeX.setName(this.name);
        return prototypeX;
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
