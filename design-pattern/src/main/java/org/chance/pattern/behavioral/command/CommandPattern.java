package org.chance.pattern.behavioral.command;

/**
 * 命令模式（command），属于行为型模式的一种，将一个请求封装到一个对象，从而使得可用不同的请求对客户进行参数化。
 * <p>
 * Command ：抽象命令类
 * ConcreteCommand ：具体命令类
 * Invoker ：调用者 / 请求者 请求的发送者，他通过命令对象来执行。一个调用者并不需要在设计时确定其接受者，因此它只与抽象命令类之间存在关联，在程序运行时，将调用命令对象的execute()，间接调用接受者的相关操作。
 * Receiver ：接收者  接收者执行与请求相关的操作，具体实现对请求的业务处理。
 * 未抽象前，实际执行操作内容的对象。
 * Client 客户类 在客户类中需要创建调用者对象、具体命令类对象，在创建具体命令类对象时指定对应的接受者。发送者和接收者之间没有直接关系，都通过命令对象间接调用。
 * <p>
 * 场景：在军队中，团长发出作战命令，但不指定谁去执行命令，命令根据接受者的不同去让接受者（战士）去执行命令。
 * <p>
 * 引用场景：
 * - Struts2 中，action 的整个调用过程就是命令模式；
 * - 数据库事务机制的底层实现；
 * - 命令的撤销和恢复；
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-09-10 17:36:04
 */
public class CommandPattern {

    /**
     * Command ：抽象命令类
     * 命令接口
     */
    private interface Command {

        /**
         * 在真正的项目中可以设计多个方法
         */
        void execute();
    }

    /**
     * ConcreteCommand ：具体命令类
     */
    private static class ConcreteCommand implements Command {

        /**
         * 命令真正的执行者
         */
        private Receiver receiver;


        public ConcreteCommand() {
        }

        public ConcreteCommand(Receiver receiver) {
            this.receiver = receiver;
        }

        @Override
        public void execute() {
            //命令执行前或后，进行相关的操作
            receiver.action();
        }
    }

    /**
     * 命令的调用者或发起者（相当于例子中的团长）
     */
    private static class Invoke {

        private Command command;    //也可以通过容器List<Command>容纳很多的命令，进行批处理。
        //数据库底层的事务管理就是类似的结构！

        public Invoke(Command command) {
            this.command = command;
        }

        /**
         * 下达命令
         */
        public void call() {
            //命令执行前或后，进行相关的操作
            //通过命令对象间接调用接收者
            command.execute();
        }

    }

    /**
     * 真正的命令执行者（相当于例子中的战士）
     */
    private static class Receiver {

        /**
         * 执行命令
         */
        public void action() {
            System.out.println("战士去执行作战命令！");
        }

    }

    private static class Client {

        public static void main(String[] args) {

            //创建具体命令类对象并指定对应的接受者
            Command command = new ConcreteCommand(new Receiver());

            Invoke invoke = new Invoke(command);

            invoke.call();
        }
    }

}
