package org.chance.pattern.structural.adapter;

/**
 * 对象适配器
 * 对象适配器与类适配器不同之处在于，类适配器通过继承来完成适配，对象适配器则是通过关联来完成
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-01 15:40:35
 */
public class ObjectAdapter implements Target {

    private Adaptee adaptee;

    public ObjectAdapter(Adaptee adaptee) {
        this.adaptee = this.adaptee;
    }

    /**
     * 源类有方法直接调用
     */
    @Override
    public void op1() {
        this.adaptee.op1();
    }

    /**
     * 需要补充的方法
     */
    @Override
    public void op2() {
        System.out.println("ObjectAdapter.op2");
    }

    @Override
    public void op3() {
        this.adaptee.adapteeOp1();
    }

}
