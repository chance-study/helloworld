package org.chance.pattern;

import java.util.Vector;

/**
 * Created by gengchao on 16/5/1.
 * 迭代器模式有叫做游标（Cursor)模式。GOF给出的定义：提供一种方法访问一个
 * 容器（container)对象中的各个元素，而又不暴露该对象的内部细节。
 * 迭代器角色（Iterator）: 负责定义访问和遍历元素的接口。
 * 具体迭代器角色（Concrete Iterator）：实现迭代器接口，并要记录遍历中的当前位置。
 * 容器角色(Container):  负责提供创建具体迭代器角色的接口。
 * 具体容器角色（Concrete Container）：实现创建具体迭代器角色的接口， 这个具体迭代器角色与该容器的结构相关。
 */
public class IteratorPattern {

    static interface Iterator{
        Object first();
        Object next();
        Object currentItem();
        boolean isDone();
    }

    static class ConcreteIterator implements Iterator{

        private int currentIndex = 0;
        private Vector vector=null;

        ConcreteIterator(final Vector vector){
            this.vector = vector;
        }

        @Override
        public Object first() {
            currentIndex = 0;
            return vector.get(0);
        }

        @Override
        public Object next() {
            currentIndex++;
            return vector.get(currentIndex);
        }

        @Override
        public Object currentItem() {
            return vector.get(currentIndex);
        }

        @Override
        public boolean isDone() {

            if(currentIndex >= this.vector.size()-1){
                return true;
            }else{
                return false;
            }
        }
    }

    static interface Aggregat{
        Iterator createIterator();
    }

    static class ConcreteAggregat implements Aggregat{

        private Vector vector = null;

        Vector getVector(){
            return vector;
        }

        void setVector(final Vector vector){
            this.vector = vector;
        }

        ConcreteAggregat(){
            vector = new Vector();
            vector.add("v 1");
            vector.add("v 2");
        }

        @Override
        public Iterator createIterator() {
            return new ConcreteIterator(vector);
        }
    }

    static class Client{
        public static void main(String[] args) {
            final Aggregat aggregat = new ConcreteAggregat();
            final Iterator iterator = aggregat.createIterator();
            System.out.println(iterator.first());
            while(!iterator.isDone()){
                System.out.println(iterator.next());
            }
        }
    }
}
