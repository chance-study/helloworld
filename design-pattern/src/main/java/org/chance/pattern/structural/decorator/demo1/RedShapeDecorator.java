package org.chance.pattern.structural.decorator.demo1;

/**
 * #4 创建扩展了 ShapeDecorator 类的实体装饰类。
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-02 17:30:42
 */
public class RedShapeDecorator extends ShapeDecorator {

    public RedShapeDecorator(Shape decoratedShape) {
        super(decoratedShape);
    }

    @Override
    public void draw() {
        decoratedShape.draw();
        setRedBorder(decoratedShape);
    }

    private void setRedBorder(Shape decoratedShape) {
        System.out.println("Border Color: Red");
    }
}
