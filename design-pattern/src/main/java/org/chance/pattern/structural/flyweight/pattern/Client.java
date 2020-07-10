package org.chance.pattern.structural.flyweight.pattern;

/**
 * 从这个结果我们可以看出来，第一次创建X、Y、Z时，都是先创建再从池中取出，而第二次创建X时，因为池中已经存在了，所以直接从池中取出，这就是享元模式。
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-08 13:49:49
 */
public class Client {

    public static void main(String[] args) {
        int extrinsic = 22;

        Flyweight flyweightX = FlyweightFactory.getFlyweight("X");
        flyweightX.operate(++extrinsic);

        Flyweight flyweightY = FlyweightFactory.getFlyweight("Y");
        flyweightY.operate(++extrinsic);

        Flyweight flyweightZ = FlyweightFactory.getFlyweight("Z");
        flyweightZ.operate(++extrinsic);

        Flyweight flyweightReX = FlyweightFactory.getFlyweight("X");
        flyweightReX.operate(++extrinsic);

        Flyweight unsharedFlyweight = new UnsharedConcreteFlyweight("X");
        unsharedFlyweight.operate(++extrinsic);
    }

}
