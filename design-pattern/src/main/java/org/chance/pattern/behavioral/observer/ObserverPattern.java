package org.chance.pattern.behavioral.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 观察者模式（又被称为发布-订阅（Publish/Subscribe）模式，属于行为型模式的一种，它定义了一种一对多的依赖关系，让多个观察者对象同时监听某一个主题对象。这个主题对象在状态变化时，会通知所有的观察者对象，使他们能够自动更新自己。
 * <p>
 * Subject：抽象主题（抽象被观察者），抽象主题角色把所有观察者对象保存在一个集合里，每个主题都可以有任意数量的观察者，抽象主题提供一个接口，可以增加和删除观察者对象。
 * ConcreteSubject：具体主题（具体被观察者），该角色将有关状态存入具体观察者对象，在具体主题的内部状态发生改变时，给所有注册过的观察者发送通知。
 * Observer：抽象观察者，是观察者者的抽象类，它定义了一个更新接口，使得在得到主题更改通知时更新自己。
 * ConcrereObserver：具体观察者，是实现抽象观察者定义的更新接口，以便在得到主题更改通知时更新自身的状态。
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-09-10 17:36:04
 */
public class ObserverPattern {

    /**
     * Subject：抽象主题（抽象被观察者），抽象主题角色把所有观察者对象保存在一个集合里，每个主题都可以有任意数量的观察者，抽象主题提供一个接口，可以增加和删除观察者对象。
     */
    private static class Subject {

        protected List<Observer> list;

        public Subject() {
            list = new ArrayList<>();
        }

        public void addObserver(Observer observer) {
            list.add(observer);
        }

        public void removeObserver(Observer observer) {
            list.remove(observer);
        }

        /**
         * 通知所以观察者更新状态
         */
        public void notifyAllObserver() {
            for (Observer o : list) {
                o.update(this);
            }
        }


    }

    /**
     * ConcreteSubject：具体主题（具体被观察者），该角色将有关状态存入具体观察者对象，在具体主题的内部状态发生改变时，给所有注册过的观察者发送通知。
     */
    private static class ConcreteSubject extends Subject {

        /**
         * 状态
         */
        private int state;

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
            //若主题对象（目标对象）发生的状态发生改变，请通知所有观察者更新状态
            this.notifyAllObserver();
        }

    }

    /**
     * Observer：抽象观察者，是观察者者的抽象类，它定义了一个更新接口，使得在得到主题更改通知时更新自己。
     */
    private static interface Observer {
        void update(Subject subject);
    }

    /**
     * ConcrereObserver：具体观察者，是实现抽象观察者定义的更新接口，以便在得到主题更改通知时更新自身的状态。
     */
    private static class ConcrereObserver implements Observer {

        // myState需要和目标对象的state保持一致
        private int myState;

        @Override
        public void update(Subject subject) {
            myState = ((ConcreteSubject) subject).getState();
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
            sub.setState(100);

            System.out.println(o1.getMyState());
            System.out.println(o2.getMyState());
            System.out.println(o3.getMyState());

            System.out.println("----------------------");

            //修改目标对象的状态值
            sub.setState(10);

            System.out.println(o1.getMyState());
            System.out.println(o2.getMyState());
            System.out.println(o3.getMyState());
        }
    }

}
