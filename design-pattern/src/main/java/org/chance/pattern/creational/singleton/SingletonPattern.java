package org.chance.pattern.creational.singleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by wqg on 2016/4/25.
 * 单例模式
 * 懒汉式单例
 * 饿汉式单例
 */
public class SingletonPattern {

    private static class Client{
        public static void main(String[] args) {
            //通过反射机制能够让所有单例模式失效
            Class<Singleton> clazz = Singleton.class;
            try {
                Constructor<Singleton> constructor = clazz.getDeclaredConstructor();
                //强制私有构造方法可以被访问
                constructor.setAccessible(true);
                Singleton singleton = constructor.newInstance();
                System.out.println("Client.main>>>"+singleton);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private static class ClientA{
        public static void main(String[] args) {
            long start = System.currentTimeMillis();
            for(int i=0;i<10;i++){
                new Thread(()->{
                    System.out.println("Client.main>>>"+Singleton.getInstance());
                }).start();
            }
            System.out.println("time"+(System.currentTimeMillis()-start));
        }
    }

}


