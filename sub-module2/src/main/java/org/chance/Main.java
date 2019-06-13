package org.chance;

import org.org.helper.IntegerHelper;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by gengchao on 16/4/20.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World");

        /////////
        String str=null;
        if(str !=null && !str.equals("") ){
            System.out.println(">>>>");
        }
//        if(str !=null & !str.equals("") ){
//            System.out.println(">>>>");
//        }

        byte b1 = 0x31 & 0x0f ;
        b1 = 0b0101_0101 & 0b0000_1111;
        System.out.println(Integer.toBinaryString(b1));
        System.out.println(IntegerHelper.toFullBinaryString(b1));
        System.out.println(Integer.toHexString(b1));
        System.out.printf("%#x %n",b1);

        //////////

        ok:
        {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    System.out.println("j = " + j + ",i=" + i);
                    if (j == 5) break ok;
                }
            }

            System.out.println("Main.main");
        }

        System.out.println("test");

        int[][] arry = {{1,2,3},{4,5,6},{9}};
        boolean found = false;
        for(int i=0; i<arry.length && !found; i++){
            for(int j=0 ; j<arry[i].length;j++){
                if(arry[i][j] == 5) {
                    System.out.println("j = " + j + ",i=" + i);
                    found =true;
                    break;
                }
            }
        }


        ////////
        str="TS";
        long l = 1l;
        //switch 目前只能用于 char,byte,short,int,string,Byte,Short,Integer,Character or enum
        switch (str){
            case "TS":System.out.println("str = " + str);break;
//            case 1l: System.out.println("l = " + l);break;
            default:
                System.out.println("default = " + "default");break;
        }


        //////
        // s1+1 会自动提升表达式的类型
        // += 编译器会做特殊处理
        short s1=1;
//        s1=s1+1;
        s1+=1;

        //////
        // char 在Java中是2个字节。java采用unicode，2个字节（16位）来表示一个字符。
        // 通常gbk/gb2312是2个字节，utf-8是3个字节。
        char c = '耿';
        System.out.println(Character.SIZE);
        try {
//            byte[] bs = Character.toString(c).getBytes("utf-8");
//            byte[] bs=Character.toString(c).getBytes("gbk");
//            byte[] bs=Character.toString(c).getBytes("unicode");
            byte[] bs=Character.toString(c).getBytes();
           for(byte b:bs){
               System.out.printf("%#x%n",b);
           }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //////
        //一个数左移 n 位,就相当于乘以了2的 n 次方
        System.out.println(2<<3);
        System.out.println(3<<3);

        //////
        int a = Integer.MAX_VALUE;
        int b = Integer.MAX_VALUE;
        int sum = a + b;
        System.out.println("a="+a+",b="+b+",sum="+sum);

        //////
        // final 修饰的时候是指 引用变量不能变 所指向的对象可以改变
        final StringBuffer sb = new StringBuffer("aa");
        sb.append("bb");
//        sb = new StringBuffer("aab");

        //////
        // == / equals
        String s2=new String("foo");
        String s3=new String("foo");
        System.out.println(s2==s3);

        String s4="foo";
        String s5="foo";
        System.out.println(s4==s5);

        //////
        // ceil 向上取整 floor 想下取整 round =Math.floor(x+0.5)
        System.out.println(Math.round(-11.5));
        System.out.println(Math.round(11.5));

        Map<String,String> map = new HashMap<String,String>();
        System.out.println(new Object());

        ArrayList<String> l1=new ArrayList<>();
        l1.add("aaa");
        l1.add("bbb");
        ArrayList<String> l2 =l1;
        l2.add("cccc");
        ArrayList<String> l3= (ArrayList<String>) l1.clone();
        l3.add("dddd");
        System.out.println(l1.size() + "," + l2.size());
        System.out.println(l1.size()+","+l2.size()+","+l3.size());

        //////
        //引用类型占用
        //32位操作系统引用类型占用 4个字节 64位操作系统占用8个字节
        //引用本身是保留在栈中的 引用所指的对象，是存放在堆中的


        //////
        StringTokenizer tokenizer = new StringTokenizer("a,b,c",",");
        String[] result = new String[tokenizer.countTokens()];
        int i=0;
        while(tokenizer.hasMoreTokens()){
            result[i++] = tokenizer.nextToken();
        }

        for(String s:result){
            System.out.println(s);
        }

        //////
        String str1="a";
        String str2=s1+"b";
        String str3="a"+"b";
        System.out.println(str2=="ab");
        System.out.println(str3=="ab");


        System.out.println(test());
        System.out.println(test1());
        System.out.println(test2());

    }


    public static void fun1(){

        B.B4 b4 = new B.B4();
        B.B3 b3 = new B().new B3();

    }

    /////
    //finally 和return的执行顺序
    // finally 中的代码比return break后执行
    public static int test(){
        int x=1;
        try{
            return x;
        }finally {
            ++x;
            System.out.println("finally"+x);
        }
    }
    public static StringBuffer test1(){
        StringBuffer sb=new StringBuffer();
        sb.append("aa");
        try{
            return sb;
        }finally {
            sb.append("bb");
            System.out.println("finally"+sb);
        }
    }
    public static int test2(){
        try{
            return 1;
        }finally {
            return 2;
        }
    }
}
