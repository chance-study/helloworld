package org.chance.pattern.creational.prototype.show3;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-15 15:35:50
 */
public class Client {

    public static void main(String[] args) throws Exception {
        Prototype p1 = new ConcretePrototypeX();
        PrototypeManager.setPrototype("p1", p1);
        Prototype p3 = PrototypeManager.getPrototype("p1").clone();
        p3.setName("p3");
        System.out.println(">>" + p3);

        Prototype p2 = new ConcretePrototypeY();
        PrototypeManager.setPrototype("p1", p2);
        Prototype p4 = PrototypeManager.getPrototype("p1").clone();
        p4.setName("p4");
        System.out.println(">>>" + p4);
        PrototypeManager.removePrototype("p1");
        Prototype p5 = PrototypeManager.getPrototype("p1").clone();
        p5.setName("p5");
        System.out.println(">>>>" + p5);

    }

}
