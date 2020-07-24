package org.chance.pattern.creational.prototype.show1;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-15 15:20:41
 */
public class Client {

    private Prototype prototype;

    Client(Prototype prototype) {
        this.prototype = prototype;
    }

    Prototype copy() {
        return prototype.clone();
    }

    public static void main(String[] args) {

        Prototype prototype = new ConcretePrototypeA();
        Prototype copyPrototype = new Client(prototype).copy();
        copyPrototype.toString();
        System.out.println(prototype == copyPrototype);
        System.out.println(prototype.getClass() == copyPrototype.getClass());

    }

}
