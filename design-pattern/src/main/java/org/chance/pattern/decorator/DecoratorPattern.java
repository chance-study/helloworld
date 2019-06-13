package org.chance.pattern.decorator;

/**
 * Created by wqg on 2016/4/25.
 * 装饰（Decorator）模式
 * 抽象构件(Component)角色：给出一个抽象接口，以规范准备接收附加责任的对象。
 * 具体构件(ConcreteComponent)角色：定义一个将要接收附加责任的类。
 * 装饰(Decorator)角色：持有一个构件(Component)对象的实例，并定义一个与抽象构件接口一致的接口。
 * 具体装饰(ConcreteDecorator)角色：负责给构件对象“贴上”附加的责任。
 *
 */
public class DecoratorPattern {

    //抽象构件角色
    public interface Component {
        public void op();
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
    public class Decorator implements Component{
        private Component component;

        public Decorator(Component component){
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
        public void m(){
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


