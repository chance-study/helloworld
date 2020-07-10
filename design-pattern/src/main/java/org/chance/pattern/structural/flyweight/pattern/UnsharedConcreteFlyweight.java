package org.chance.pattern.structural.flyweight.pattern;

/**
 * 指那些不需要共享的Flyweight子类。
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-08 13:48:08
 */
public class UnsharedConcreteFlyweight extends Flyweight {

    public UnsharedConcreteFlyweight(String extrinsic) {
        super(extrinsic);
    }

    @Override
    public void operate(int extrinsic) {
        System.out.println("不共享的具体Flyweight:" + extrinsic);
    }

}
