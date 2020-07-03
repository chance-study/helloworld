package org.chance.pattern.structural.bridge.demo;

/**
 * #5 使用 Shape 和 DrawAPI 类画出不同颜色的圆。
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-03 15:49:06
 */
public class Client {

    public static void main(String[] args) {
        Shape redCircle = new Circle(100, 100, 10, new RedCircle());
        Shape greenCircle = new Circle(100, 100, 10, new GreenCircle());

        redCircle.draw();
        greenCircle.draw();
    }

}
