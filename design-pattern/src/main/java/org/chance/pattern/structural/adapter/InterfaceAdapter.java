package org.chance.pattern.structural.adapter;

/**
 * 对象适配器
 * 对象适配器与类适配器不同之处在于，类适配器通过继承来完成适配，对象适配器则是通过关联来完成
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-01 15:40:35
 */
public class InterfaceAdapter extends DefaultAdapter {

    private Adaptee adaptee;

    public InterfaceAdapter(Adaptee adaptee) {
        this.adaptee = adaptee;
    }


    @Override
    public void op1() {
        adaptee.adapteeOp1();
        // do
    }
}
