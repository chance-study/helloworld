package org.chance.pattern;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gengchao on 16/4/30.
 * 原型模式是一种创建型设计模式,它通过复制一个已经存在的实例来返回新的实例,而不是新建实例.
 * 被复制的实例就是我们所称的原型,这个原型是可定制的.
 * 原型模式中的拷贝分为"浅拷贝"和"深拷贝":
 * 浅拷贝: 对值类型的成员变量进行值的复制,对引用类型的成员变量只复制引用,不复制引用的对象.
 * 深拷贝: 对值类型的成员变量进行值的复制,对引用类型的成员变量也进行引用对象的复制.
 */
public class PrototypePattern {

    /**
     * 简单形式的原型模式
     * 客户(Client)角色：客户类提出创建对象的请求。
     * 抽象原型(Prototype)角色：这是一个抽象角色，通常由一个Java接口或Java抽象类实现。此角色给出所有的具体原型类所需的接口。
     * 具体原型（Concrete Prototype）角色：被复制的对象。此角色需要实现抽象的原型角色所要求的接口。
     * */
    static interface Prototype{
        /**
         * 克隆自身的方法
         * @return 一个从自身克隆出来的对象
         * */
        Prototype clone();
    }

    static class ConcretePrototypeA implements Prototype{
        public Prototype clone(){
            Prototype prototype = new ConcretePrototypeA();
            return prototype;
        }
    }

    static class ConcretePrototypeB implements Prototype{
        public Prototype clone(){
            Prototype prototype = new ConcretePrototypeB();
            return prototype;
        }
    }

    static class Client {

        private Prototype prototype;

        Client(Prototype prototype){
            this.prototype = prototype;
        }

        Prototype copy(){
            return prototype.clone();
        }

        public static void main(String[] args) {

            Prototype prototype = new ConcretePrototypeA();
            Prototype copyPrototype = new Client(prototype).copy();
            copyPrototype.toString();
            System.out.println(prototype == copyPrototype);
            System.out.println(prototype.getClass() == copyPrototype.getClass() );

            /***/
            ConcretePrototype1 concretePrototype1 = new ConcretePrototype1();
            for(int i=0;i<3;i++){
                ConcretePrototype1 copy = (ConcretePrototype1) concretePrototype1.clone();
                copy.show();
            }

        }
    }


    /**java 中已经实现的原型模式*/
    static class Prototype1 implements Cloneable,Serializable{
        //浅克隆
        public Prototype1 clone() {
            Prototype1 prototype1 = null;
            try{
                prototype1 = (Prototype1) super.clone();
            }catch (CloneNotSupportedException e){
                e.printStackTrace();
            }
            return prototype1;
        }

        //深度克隆
        //利用序列化进行深度克隆
        public Object deepClone() throws IOException,
                ClassNotFoundException{
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(this);
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);
            return ois.readObject();

        }


    }

    static class ConcretePrototype1 extends Prototype1 {
        void show(){
            System.out.println("ConcretePrototype1.shoe");
        }
    }


    /**
     * 登记形式的原型模式
     * */
    static interface PrototypeX{
        PrototypeX clone();
        String getName();
        void setName(String name);
    }

    static class ConcretePrototypeX implements PrototypeX{

        private String name;

        @Override
        public PrototypeX clone() {
            ConcretePrototypeX prototypeX = new ConcretePrototypeX();
            prototypeX.setName(this.name);
            return prototypeX;
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public void setName(String name) {
            this.name = name;
        }
    }


    static class ConcretePrototypeY implements PrototypeX{

        private String name;

        @Override
        public PrototypeX clone() {
            ConcretePrototypeY prototypeY = new ConcretePrototypeY();
            prototypeY.setName(this.name);
            return prototypeY;
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public void setName(String name) {
            this.name = name;
        }
    }

    static class PrototypeManager {
        private static Map<String,PrototypeX> map
                =new HashMap<String,PrototypeX>();
        private PrototypeManager(){}

        public synchronized static void setPrototype(String prototypeId,PrototypeX prototypeX){
            map.put(prototypeId,prototypeX);
        }

        public synchronized static void removePrototype(String prototypeId){
            map.remove(prototypeId);
        }

        public synchronized static PrototypeX getPrototype(String prototypeId)
            throws Exception {
            PrototypeX prototypeX = map.get(prototypeId);
            if(prototypeX == null ){
                throw new Exception("您希望获取的原型还没有注册或已被销毁");
            }
            return prototypeX;
        }
    }

    static class ClientX{
        public static void main(String[] args) throws Exception{
            PrototypeX p1 = new ConcretePrototypeX();
            PrototypeManager.setPrototype("p1", p1);
            PrototypeX p3 = PrototypeManager.getPrototype("p1").clone();
            p3.setName("p3");
            System.out.println(">>" + p3);

            PrototypeX p2 = new ConcretePrototypeY();
            PrototypeManager.setPrototype("p1", p2);
            PrototypeX p4 = PrototypeManager.getPrototype("p1").clone();
            p4.setName("p4");
            System.out.println(">>>" + p4);
            PrototypeManager.removePrototype("p1");
            PrototypeX p5 = PrototypeManager.getPrototype("p1").clone();
            p5.setName("p5");
            System.out.println(">>>>"+p5);

        }
    }

}
