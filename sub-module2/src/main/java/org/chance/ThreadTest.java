package org.chance;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by gengchao on 16/4/24.
 */
public class ThreadTest {

    /////实现多线程的多种方法
    //如果想象所有对象和类方法都公用一把锁的话,
    //定义个一个锁对象,然后对代码块加锁
    //synchronized static fun 和 synchronized fun 一个是用类加锁,一个是用对象加锁
    //不冲突
    public static final Byte[] locks=new Byte[0];

    private static int j;
    private static Lock lock = new ReentrantLock();

    public void fun(){
        synchronized (locks){
            //...
        }
    }

    public static void main(String[] args) throws Exception{
        MyThread my1 = new MyThread();
        MyThread my2 = new MyThread();
        my1.start();
        my2.start();


        //2 通过实现runnable接口实现多线程
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("通过匿名内部类方式实现一个线程"+this.toString());
                    }
                }
        ).start();

        Runnable task = ()->{
            System.out.println("hello"+Thread.currentThread().getName());
        };
        task.run();
        Thread thread = new Thread(task);
        thread.start();
        System.out.println("Done");

        //线程池的使用
        int taskSize = 5;
        //创建一个线程池
        ExecutorService pool = Executors.newFixedThreadPool(taskSize);
        //创建多个有返回值的任务
        List<Future> list = new ArrayList<>();
        for(int i=0;i<taskSize;i++){
            Callable c = new Thread3(i+"  ");
            //// 执行任务并获取Future对象
            Future f = pool.submit(c);
            list.add(f);
        }
        //// 关闭线程池
        pool.shutdown();

        // 获取所有并发任务的运行结果
        for (Future f : list) {
            // 从Future对象上获取任务的返回值，并输出到控制台
            try {
                System.out.println(">>>" + f.get().toString());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }


        //java8 新功能
        FutureTask task3 = new FutureTask(
                (Callable)()-> {
                    int i=0;
                    for(i=0;i<10;i++){
                        System.out.println(Thread.currentThread().getName()+" "+i);
                    }
                    return i;
                }
        );

        for(int i=0;i<10;i++){
            System.out.println(Thread.currentThread().getName() + " "+i);
            if(i==5) {
                new Thread(task3,"lambda thread5 with value").start();// it's a Runnable
            }
        }

        try {
            System.out.println("lambda thread5 return value "+task3.get());
        } catch(Exception e) {
            e.printStackTrace();
        }


        //////
//        ThreadTest test1=new ThreadTest();
//        new Thread((Runnable)()->{
//            try {
//                test1.x();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        },"a").start();
//
//        new Thread((Runnable)()->{
//            try {
//                test1.x();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        },"b").start();
//
//
//        new Thread((Runnable)()->{
//            try {
//                ThreadTest.staticX();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        },"c").start();
//        System.out.println("End");



        ////////////
        new Thread(()->{
            synchronized (ThreadTest.class){
                System.out.println("t1...");
                System.out.println("t1 waiting...");
                try{
                    ThreadTest.class.wait();
                }catch (Exception e){
                    e.printStackTrace();
                }
                System.out.println("t1 is going on...");
                System.out.println("t1 is being over");

            }
        },"T1").start();
        Thread.sleep(2000);
        new Thread(()->{
            synchronized (ThreadTest.class){
                System.out.println("t2...");
                System.out.println("t2 notify other t...");
                try{
                    ThreadTest.class.notify();
                }catch (Exception e){
                    e.printStackTrace();
                }
                System.out.println("t1 is sleeping ten ...");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("t2 is going on...");
                System.out.println("t2 is being over!");

            }

            try {
                TimeUnit.SECONDS.sleep(1);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("t2 no synchronized");



        },"T2").start();




        ///////////////
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            System.out.println("hello" + Thread.currentThread().getName());
        });


        Callable<Integer> ctask = ()->{
            TimeUnit.SECONDS.sleep(2);
            return 123;
        };

        Future<Integer> future = executor.submit(ctask);
        System.out.println("future doen? " + future.isDone());
        Integer result = future.get();
        //future.get 会阻塞代码
//        Integer result = future.get(1,TimeUnit.SECONDS);
        System.out.println("future done? " + future.isDone());
        System.out.println("reulst "+result);

        //invokeAll()一次提交多个 callable
        List<Callable<String>> callables = Arrays.asList(
                ()->"task1",
                ()->"task2",
                ()->"task3"
        );

        executor.invokeAll(callables)
                .stream()
                .map(f->{
                    try{
                        return f.get();
                    }catch(Exception e){
                        throw new IllegalStateException(e);
                    }
                })
                .forEach(System.out::println);


        //关闭线程池
        try{
            executor.shutdown();
            executor.awaitTermination(5,TimeUnit.SECONDS);
        }catch (InterruptedException e){
            System.err.println("tasks interrupted");
        }finally {
            if(!executor.isTerminated()){
                System.err.println("cancel non-finished tasks");
            }
            executor.shutdownNow();
            System.out.println("shutdown finished");
        }




        /////////////
        //Lock
        for(int i=0;i<2;i++){
            new Thread(()->{
                while(true){
                    lock.lock();
                    try{
                        System.out.println("j--="+j--);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        lock.unlock();
                    }


                }
            },"t1").start();

            new Thread(()->{
                while(true){
                    lock.lock();
                    try{
                        System.out.println("j--="+j--);
                        TimeUnit.SECONDS.sleep(1);
                    }catch (Exception e){

                    }finally {
                        lock.unlock();
                    }
                }
            },"t2").start();
        }

    }


    public static synchronized void staticX() throws InterruptedException
    {
        for (int i = 0; i < 10; i++)
        {
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName()+"staticX.......................");
        }
    }

    public synchronized void x() throws InterruptedException
    {
        for (int i = 0; i < 10; i++)
        {
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName()+"x.......................");
        }
    }

    //1继承Thread类
    static class MyThread extends Thread{

        @Override
        public void run() {
            System.out.println("myThread.run"+this.getId()+this.getName());
        }
    }

    static class Thread2 implements Runnable{

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
        }
    }

    static class Thread3 implements Callable {

        private String taskName;

        Thread3(String taskName){
            this.taskName=taskName;
        }

        @Override
        public Object call() throws Exception {
            System.out.println(">>>>>" + taskName + "任务启动!");
            Date start=new Date();
            Thread.sleep(1000);
            long time = new Date().getTime()-start.getTime();

            return taskName+"任务返回运行结果:"+time;
        }
    }



}
