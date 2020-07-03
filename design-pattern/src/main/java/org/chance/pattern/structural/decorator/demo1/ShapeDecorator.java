package org.chance.pattern.structural.decorator.demo1;

/**
 * #3 创建实现了 Shape 接口的抽象装饰类。
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-02 17:29:41
 */
public abstract class ShapeDecorator implements Shape {

    protected Shape decoratedShape;

    public ShapeDecorator(Shape decoratedShape) {
        this.decoratedShape = decoratedShape;
    }

    @Override
    public void draw() {
        decoratedShape.draw();
    }
}
