package org.chance.pattern.structural.bridge.demo;

/**
 * #4 创建实现了 Shape 接口的实体类。
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-03 15:48:07
 */
public class Circle extends Shape {

    private int x, y, radius;

    public Circle(int x, int y, int radius, DrawAPI drawAPI) {
        super(drawAPI);
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    @Override
    public void draw() {
        drawAPI.drawCircle(radius, x, y);
    }
}
