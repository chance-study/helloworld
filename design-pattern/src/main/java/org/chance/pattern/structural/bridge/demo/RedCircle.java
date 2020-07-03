package org.chance.pattern.structural.bridge.demo;

/**
 * #2 创建实现了 DrawAPI 接口的实体桥接实现类。
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-03 15:45:19
 */
public class RedCircle implements DrawAPI {

    @Override
    public void drawCircle(int radius, int x, int y) {
        System.out.println("Drawing Circle[ color: red, radius: "
                + radius + ", x: " + x + ", " + y + "]");
    }

}
