package org.chance.pattern.structural.adapter;

/**
 * https://www.runoob.com/design-pattern/adapter-pattern.html
 *
 * 将一个类的接口转换成客户希望的另外一个接口
 * Adapter模式使得原本由于接口不兼容而不能一起工作的那些类可以在一起工作。
 * <p>
 * 目标接口（Target）：客户所期待的接口。目标可以是具体的或抽象的类，也可以是接口。
 * 需要适配的类（Adaptee）：需要适配的类或适配者类。
 * 适配器（ClassAdapter）：通过包装一个需要适配的对象，把原接口转换成目标接口。
 *
 * 类适配器模式，继承源类，实现目标接口。
 * 对象适配器模式，持有源类的对象，把继承关系改变为组合关系。
 * 接口适配器模式，借助中间抽象类空实现目标接口所有方法，适配器选择性重写。
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-01 15:27:15
 */
public class AdapterPattern {

    public static void main(String[] args) {

        Target target = new ClassAdapter();
        target.op1();



    }

}




