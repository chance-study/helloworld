package org.chance.pattern.structural.facade.demo;

/**
 * Facade类
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-03 15:34:09
 */
public class ShapeMaker {

    private Shape circle;
    private Shape rectangle;
    private Shape square;

    public ShapeMaker() {
        circle = new Circle();
        rectangle = new Rectangle();
        square = new Square();
    }

    public void drawCircle() {
        circle.draw();
    }

    public void drawRectangle() {
        rectangle.draw();
    }

    public void drawSquare() {
        square.draw();
    }

}
