package org.chance.generic;

import java.util.ArrayList;

/**
 *
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-08-19 16:08:20
 */
public class GenericDemo {

    public static void main(String[] args) {

        // 擦除法实现的伪泛型
        Class c1 = new ArrayList<String>().getClass();
        Class c2 = new ArrayList<Integer>().getClass();
        System.out.println(c1 == c2);
    }

}
