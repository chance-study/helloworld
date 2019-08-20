package org.chance.java.thread;

/**
 * VolatileDemo
 *
 public class org.chance.java.thread.VolatileDemo {
 public org.chance.java.thread.VolatileDemo();
 Code:
 0: aload_0
 1: invokespecial #1                  // Method java/lang/Object."<init>":()V
 4: return
 LineNumberTable:
 line 65: 0
 LocalVariableTable:
 Start  Length  Slot  Name   Signature
 0       5     0  this   Lorg/chance/java/thread/VolatileDemo;

 public static void main(java.lang.String[]);
 Code:
 0: new           #2                  // class java/lang/Thread
 3: dup
 4: invokedynamic #3,  0              // InvokeDynamic #0:run:()Ljava/lang/Runnable;
 9: invokespecial #4                  // Method java/lang/Thread."<init>":(Ljava/lang/Runnable;)V
 12: invokevirtual #5                  // Method java/lang/Thread.start:()V
 15: ldc2_w        #6                  // long 5000l
 18: invokestatic  #8                  // Method java/lang/Thread.sleep:(J)V
 21: goto          29
 24: astore_1
 25: aload_1
 26: invokevirtual #10                 // Method java/lang/InterruptedException.printStackTrace:()V
 29: iconst_1
 30: putstatic     #11                 // Field isOver:Z
 33: return
 Exception table:
 from    to  target type
 15    21    24   Class java/lang/InterruptedException
 LineNumberTable:
 line 71: 0
 line 75: 12
 line 78: 15
 line 81: 21
 line 79: 24
 line 80: 25
 line 83: 29
 line 84: 33
 LocalVariableTable:
 Start  Length  Slot  Name   Signature
 25       4     1     e   Ljava/lang/InterruptedException;
 0      34     0  args   [Ljava/lang/String;

 static {};
 Code:
 0: iconst_0
 1: putstatic     #11                 // Field isOver:Z
 4: return
 LineNumberTable:
 line 67: 0
 }
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/8/13
 */
public class VolatileDemo {

    private static volatile boolean isOver = false;

    public static void main(String[] args) {

        new Thread(() -> {
            while (!isOver){
                System.out.println("----------------");
            }
        }).start();

        try {
            Thread.sleep(1000 * 5 );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        isOver = true;
    }
}
