package org.chance.pattern.structural.decorator;

/**
 * - https://www.runoob.com/design-pattern/decorator-pattern.html
 * - https://www.jianshu.com/p/d80b6b4b76fc
 *
 * 装饰器模式是继承的一种替代模式，其优点是可以动态扩展一个实现类的功能。这种设计模式下不仅可以扩展一个类的功能，也可以动态增加功能，动态撤销。但缺点就是多层装饰使用起来相对比较复杂。
 *
 * # 装饰（Decorator）模式:
 * - 抽象构件(Component)角色：给出一个抽象接口，以规范准备接收附加责任的对象。
 * - 具体构件(ConcreteComponent)角色：定义一个将要接收附加责任的类。
 * - 装饰(Decorator)角色：持有一个构件(Component)对象的实例，并定义一个与抽象构件接口一致的接口。
 * - 具体装饰(ConcreteDecorator)角色：负责给构件对象“贴上”附加的责任。
 *
 * # 透明性的要求
 * - 装饰模式对客户端的透明性要求程序不要声明给一个ConcreteComponent类型的变量，而应当声明一个Component类型的变量。
 * ## 完全透明的装饰模式
 * v Component c = new ConcreteComponent();
 * v Component c1 = new ConcreteDecoratorA(c);
 *
 * x Component c = new ConcreteComponent();
 * x ConcreteDecoratorA c1 = new ConcreteDecoratorA(c);
 *
 * ## 半透明的装饰模式
 * 允许装饰模式改变接口，增加新的方法。
 * Component c = new ConcreteComponent();
 * ConcreteDecoratorA c1 = new ConcreteDecoratorA(c);
 * 这样就可以调用c1中添加的方法了
 *
 * 半透明的装饰模式是介于装饰模式和适配器模式之间的。
 *
 * # 装饰模式的相关引用场景：
 * ## 装饰模式在Java语言中最著名的应用莫过于JAVA I/O标准库的设计了
 * 抽象构建角色(Component)：由InputStream扮演。这是一个抽象类，为各种子类型提供统一的接口。
 * 具体构件角色(ConcreteComponent)：由ByteArrayInputStream、FileInputStream、StringBufferInputStream等类扮演。它们实现了抽象构件角色所规定的接口。
 * 抽象装饰角色(Decorator)：由FilterInputStream、ObectInputStream等类扮演。它们实现了InputStream所规定的接口。
 * 具体装饰角色(ConcreteDecorator)：由几个类扮演，分别是BufferedInputStream、DataInputStream以及两个不常用到的类LineNumberInputStream、PushbackInputStream。
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-02 17:35:35
 */
public class DecoratorPattern {

    //抽象构件角色
    public interface Component {
        void op();
    }

    //具体构件角色
    public class ConcreteComponent implements Component {
        @Override
        public void op() {
            // 写相关的业务代码
            System.out.println("ConcreteComponent.op");
        }
    }

    //装饰角色
    public class Decorator implements Component {
        private Component component;

        public Decorator(Component component) {
            this.component = component;
        }

        @Override
        public void op() {
            component.op();
        }
    }

    //具体装饰角色
    public class ConcreteDecoratorA extends Decorator {

        public ConcreteDecoratorA(Component component) {
            super(component);
        }

        @Override
        public void op() {
            super.op();
            // 写相关的业务代码
            System.out.println("ConcreteDecoratorA.op");
        }
    }

    public class ConcreteDecoratorB extends Decorator {

        public ConcreteDecoratorB(Component component) {
            super(component);
        }

        @Override
        public void op() {
            super.op();
            // 写相关的业务代码
            System.out.println("ConcreteDecoratorB.op");
        }
    }

    public class Client {
        public void m() {
            Component c = new ConcreteComponent();

            Component c1 = new ConcreteDecoratorA(c);
            Component c2 = new ConcreteDecoratorB(c1);

            c.op();
            c1.op();
            c2.op();
        }
    }

    public static void main(String[] args) {
        new DecoratorPattern().new Client().m();
    }
}

/////////装饰模式的简化


