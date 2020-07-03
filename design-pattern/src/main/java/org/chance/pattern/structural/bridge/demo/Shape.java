package org.chance.pattern.structural.bridge.demo;

/**
 * #3 使用 DrawAPI 接口创建抽象类 Shape。
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-03 15:46:36
 */
public abstract class Shape {

    protected DrawAPI drawAPI;

    protected Shape(DrawAPI drawAPI) {
        this.drawAPI = drawAPI;
    }

    public abstract void draw();

}
