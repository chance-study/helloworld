package org.chance.pattern.structural.facade.demo;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-03 15:35:23
 */
public class Client {
    public static void main(String[] args) {
        ShapeMaker shapeMaker = new ShapeMaker();
        shapeMaker.drawCircle();
        shapeMaker.drawRectangle();
        shapeMaker.drawSquare();
    }
}
