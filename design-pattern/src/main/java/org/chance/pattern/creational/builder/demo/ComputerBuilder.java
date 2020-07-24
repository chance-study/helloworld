package org.chance.pattern.creational.builder.demo;

/**
 * Builder：给出一个抽象接口，以规范产品对象的各个组成成分的建造。这个接口规定要实现复杂对象的哪些部分的创建，并不涉及具体的对象部件的创建。
 * 抽象构建者类可以是抽象类也可以是接口
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-15 11:13:11
 */
public abstract class ComputerBuilder {

    public abstract void setUsbCount();

    public abstract void setKeyboard();

    public abstract void setDisplay();

    public abstract Computer getComputer();

}
