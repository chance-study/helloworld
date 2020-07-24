package org.chance.pattern.creational.builder.demo;

/**
 * Director：调用具体建造者来创建复杂对象的各个部分，在指导者中不涉及具体产品的信息，只负责保证对象各部分完整创建或按某种顺序创建。
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-15 11:20:44
 */
public class ComputerDirector {

    public void makeComputer(ComputerBuilder builder) {
        builder.setUsbCount();
        builder.setDisplay();
        builder.setKeyboard();
    }

}
