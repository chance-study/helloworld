package org.chance.java.unsafe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Rectangle
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/8/14
 */

@Data
@AllArgsConstructor
@Slf4j
public class Rectangle {
    static int count = 0;
    private int h;
    private int w;
    private Rectangle subRectangle;

    public Rectangle() {
        log.info("Rectangle构造函数");
    }
}
