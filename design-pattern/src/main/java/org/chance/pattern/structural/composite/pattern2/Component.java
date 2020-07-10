package org.chance.pattern.structural.composite.pattern2;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-07 20:05:11
 */
public abstract class Component {

    protected String name;

    public Component(String name) {
        this.name = name;
    }

    //获取分支下的所有叶子构件和树枝构件
    public abstract void display(int depth);

}
