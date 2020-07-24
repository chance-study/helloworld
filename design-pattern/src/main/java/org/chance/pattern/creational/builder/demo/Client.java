package org.chance.pattern.creational.builder.demo;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-15 11:21:17
 */
public class Client {

    public static void main(String[] args) {

        ComputerDirector director = new ComputerDirector();//1
        ComputerBuilder builder = new MacComputerBuilder("I5处理器", "三星125");//2
        director.makeComputer(builder);//3
        Computer macComputer = builder.getComputer();//4
        System.out.println("mac computer:" + macComputer.toString());

        ComputerBuilder lenovoBuilder = new LenovoComputerBuilder("I7处理器", "海力士222");
        director.makeComputer(lenovoBuilder);
        Computer lenovoComputer = lenovoBuilder.getComputer();
        System.out.println("lenovo computer:" + lenovoComputer.toString());
    }

}
