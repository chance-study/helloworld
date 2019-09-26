package org.chance.javassist;

/**
 * TestClass
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/9/25
 */
public class TestClass {

    public int compute(int param) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return param + 1000;
    }

}
