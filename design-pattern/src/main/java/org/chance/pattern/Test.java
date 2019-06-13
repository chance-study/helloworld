package org.chance.pattern;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by gengchao on 16/5/12.
 */
public class Test {

    public static class Singleton{

        private static class Holder{
            private static Singleton singleton=new Singleton();
        }

        private Singleton(){
            System.out.println("Singleton.Singleton>>>init");
        }

        public final static Singleton getInstance(){
            return Holder.singleton;
        }

    }

    public static class Client{
        public static void main(String[] args) {
            long start = System.currentTimeMillis();
            for(int i=0;i<10;i++){
                new Thread(()->{
                    System.out.println("Client.main>>>"+Singleton.getInstance());
                }).start();
            }
            Singleton s=Singleton.getInstance();
            s=null;


            System.out.println("time"+(System.currentTimeMillis()-start));
        }
    }

    //
    public static class ClientA{
        public static void main(String[] args) {
            try {
                Class<Singleton> clazz= Singleton.class;
                Constructor cs = clazz.getDeclaredConstructor();
                cs.setAccessible(true);
                Singleton s = (Singleton) cs.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            
            System.out.println(Boolean.logicalXor(true, false));
        }
    }



}
