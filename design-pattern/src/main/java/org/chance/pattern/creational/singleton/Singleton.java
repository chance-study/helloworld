package org.chance.pattern.creational.singleton;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-15 15:01:49
 */
public class Singleton {
    private Singleton() {
    }

    private static Singleton singleton = null;

    //静态工厂方法
    //懒汉式单例类.在第一次调用的时候实例化自己
    //线程不安全的 线程安全则加入synchronized
    //静态内部类的方式 实现线程安全
    //静态内部类是当你使用的时候在加载
    private static class LazyHolder {
        private static final Singleton INSTANCE = new Singleton();
    }

    public static final Singleton getInstance() {
//        if(singleton == null){
//            synchronized (Singleton.class){
//                singleton = new Singleton();
//            }
//        }
//        return singleton;
        return LazyHolder.INSTANCE;
    }
}
