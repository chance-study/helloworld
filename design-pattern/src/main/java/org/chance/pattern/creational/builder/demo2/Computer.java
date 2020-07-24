package org.chance.pattern.creational.builder.demo2;

/**
 * Lombok中 builder的实现
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-15 13:50:41
 */
public class Computer {

    private final String cpu;//必须
    private final String ram;//必须
    private final int usbCount;//可选
    private final String keyboard;//可选
    private final String display;//可选

    private Computer(ComputerBuilder builder) {
        this.cpu = builder.cpu;
        this.ram = builder.ram;
        this.usbCount = builder.usbCount;
        this.keyboard = builder.keyboard;
        this.display = builder.display;
    }

    public static ComputerBuilder builder() {
        return new ComputerBuilder();
    }

    public static class ComputerBuilder {
        private String cpu;//必须
        private String ram;//必须
        private int usbCount;//可选
        private String keyboard;//可选
        private String display;//可选

        ComputerBuilder() {
        }

        public ComputerBuilder setCpu(String cpu) {
            this.cpu = cpu;
            return this;
        }

        public ComputerBuilder setRam(String ram) {
            this.ram = ram;
            return this;
        }

        public ComputerBuilder setUsbCount(int usbCount) {
            this.usbCount = usbCount;
            return this;
        }

        public ComputerBuilder setKeyboard(String keyboard) {
            this.keyboard = keyboard;
            return this;
        }

        public ComputerBuilder setDisplay(String display) {
            this.display = display;
            return this;
        }

        public Computer build() {
            return new Computer(this);
        }
    }

}
