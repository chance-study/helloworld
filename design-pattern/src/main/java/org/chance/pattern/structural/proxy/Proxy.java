package org.chance.pattern.structural.proxy;

/**
 * Proxy代理主题角色
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-03 17:17:43
 */
public class Proxy implements Subject {

    private Subject subject;

    Proxy(Subject subject) {
        this.subject = subject;
    }

    @Override
    public void op() {
        System.out.println("before");
        subject.op();
        System.out.println("after");
    }
}
