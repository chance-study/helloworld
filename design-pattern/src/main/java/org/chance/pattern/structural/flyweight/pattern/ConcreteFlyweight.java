package org.chance.pattern.structural.flyweight.pattern;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-08 13:46:55
 */
public class ConcreteFlyweight extends Flyweight {

    //接受外部状态
    public ConcreteFlyweight(String extrinsic) {
        super(extrinsic);
    }

    //根据外部状态进行逻辑处理
    @Override
    public void operate(int extrinsic) {
        System.out.println("具体Flyweight:" + extrinsic);
    }

}
