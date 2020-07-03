package org.chance.pattern.structural.proxy;

/**
 * RealSubject 具体主题角色
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-03 17:26:51
 */
public class RealSubject implements Subject {

    @Override
    public void op() {
        System.out.println("RealSubject.op");
    }
}
