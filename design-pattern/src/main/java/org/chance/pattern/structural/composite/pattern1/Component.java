package org.chance.pattern.structural.composite.pattern1;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-07 20:05:11
 */
public abstract class Component {

    protected String name;

    public Component(String name) {
        this.name = name;
    }

    //增加一个叶子构件或树枝构件
    public abstract void add(Component component);

    //删除一个叶子构件或树枝构件
    public abstract void remove(Component component);

    //获取分支下的所有叶子构件和树枝构件
    public abstract void display(int depth);

}
