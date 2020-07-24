package org.chance.pattern.creational.builder.demo2;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-15 13:51:49
 */
public class Client {

    public static void main(String[] args) {
        Computer computer = Computer.builder()
                .setCpu("")
                .setRam("")
                .setDisplay("三星24寸")
                .setKeyboard("罗技")
                .setUsbCount(2)
                .build();
    }

}
