package org.chance.pattern.adapter;

/**
 * Adapter1
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/6/14
 */
class Adapter1 implements Target1{
    private Adaptee1 adapter1;

    public Adapter1(Adaptee1 adaptee1){
        this.adapter1=adapter1;
    }

    //源类有方法直接调用
    public void op1(){
        this.adapter1.op1();
    }
    //需要补充的方法
    public void op2(){
        System.out.println("Adapter1.op2");
    }

}
