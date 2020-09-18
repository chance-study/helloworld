package org.chance.pattern.behavioral.observer.demo;

import java.util.Observable;
import java.util.Observer;

/**
 * JDK 中提供类和方法实现观察者模式
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-09-10 17:59:58
 */
public class JdkObserverPatternDemo {

    private static class ConcreteSubject extends Observable {

        private int state;

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
            setChanged();    //表示目标对象已经更改状态
            notifyObservers();    //通知所有观察者
        }

    }

    private static class ConcrereObserver implements Observer {

        private int myState;

        @Override
        public void update(Observable o, Object arg) {
            myState = ((ConcreteSubject) o).getState();
        }

        public int getMyState() {
            return myState;
        }

        public void setMyState(int myState) {
            this.myState = myState;
        }

    }

    private static class Client {

        public static void main(String[] args) {

            //目标对象
            ConcreteSubject sub = new ConcreteSubject();

            //创建三个观察者
            ConcrereObserver o1 = new ConcrereObserver();
            ConcrereObserver o2 = new ConcrereObserver();
            ConcrereObserver o3 = new ConcrereObserver();

            //将这三个观察者添加到目标对象subject的容器中
            sub.addObserver(o1);
            sub.addObserver(o2);
            sub.addObserver(o3);

            //修改目标对象的状态值
            sub.setState(666);

            System.out.println(o1.getMyState());
            System.out.println(o2.getMyState());
            System.out.println(o3.getMyState());

            System.out.println("----------------------");

            //修改目标对象的状态值
            sub.setState(123);

            System.out.println(o1.getMyState());
            System.out.println(o2.getMyState());
            System.out.println(o3.getMyState());

        }

    }


}
