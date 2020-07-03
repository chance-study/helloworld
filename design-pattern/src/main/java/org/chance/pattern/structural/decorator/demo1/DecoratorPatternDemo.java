package org.chance.pattern.structural.decorator.demo1;

/**
 * https://www.runoob.com/design-pattern/decorator-pattern.html
 *
 * #5 使用 RedShapeDecorator 来装饰 Shape 对象。
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-02 17:32:09
 */
public class DecoratorPatternDemo {

    public static void main(String[] args) {

        Shape circle = new Circle();
        ShapeDecorator redCircle = new RedShapeDecorator(new Circle());
        ShapeDecorator redRectangle = new RedShapeDecorator(new Rectangle());
        //Shape redCircle = new RedShapeDecorator(new Circle());
        //Shape redRectangle = new RedShapeDecorator(new Rectangle());
        System.out.println("Circle with normal border");
        circle.draw();

        System.out.println("\nCircle of red border");
        redCircle.draw();

        System.out.println("\nRectangle of red border");
        redRectangle.draw();
    }

}
