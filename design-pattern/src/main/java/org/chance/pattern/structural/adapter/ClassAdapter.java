package org.chance.pattern.structural.adapter;

/**
 * 类适配器
 * 源类Adaptee没有方法op2 因此适配器补充上这个方法
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-01 15:40:03
 */
public class ClassAdapter extends Adaptee implements Target {

    // 通过继承实现Target#op1

    @Override
    public void op2() {
        System.out.println("ClassAdapter.op2");
    }

    @Override
    public void op3() {
        // op3通过继承来的adapteeOp1来实现
        super.adapteeOp1();
    }

}
