package org.chance.object;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by gengchao on 16/6/20.
 * Object类中的equals方法和“==”是一样的，没有区别，即俩个对象的比较是比较他们的栈内存中存储的内存地址。
 */
public class HashCodeAndEquals {

    public static void main(String[] args) {

        /**
         * Object类中的equals方法和“==”是一样的，
         * 没有区别，即俩个对象的比较是比较他们的栈内存中存储的内存地址。
        */
        Object o1 = new Object();
        Object o2 = new Object();
        o1.equals(o2);
        System.out.println(o1 == o2);

        String s1 = "abcdd";
        String s2 = "abc";
        s1.equals(s2);
        System.out.println(s1 == s2);

        HashMap map1 = new HashMap();
        HashMap map2 = new HashMap();
        map1.equals(map2);

        /**
         * Java中的Collection有两类，一类是List，一类是Set。
         * List内的元素是有序的，元素可以重复。
         * Set元素无序，但元素不可重复。
         * 要想保证元素不重复，两个元素是否重复应该依据什么来判断呢？
         * 用Object.equals方法。但若每增加一个元素就检查一次，
         * 那么当元素很多时，后添加到集合中的元素比较的次数就非常多了。
         * 这显然会大大降低效率。于是Java采用了哈希表的原理。
         */

        HashSet hashSet = new HashSet();
        HashSet hashSet1 = new HashSet();
        hashSet.equals(hashSet1);

        ArrayList arrayList = new ArrayList();
        arrayList.equals(hashSet);

        /**
         * 当Set接收一个元素时根据该对象的内存地址算出hashCode
         * 当俩个对象的hashCode值相同的时候，Hashset会将对象保存在同一个位置，
         * 但是他们equals返回false，所以实际上这个位置采用链式结构来保存多个对象。
         */

        /**
         * Q1:若两个对象equals相等，但不在一个区间，因为hashCode的值在重写之前是对内存地址计算得出，
         * 所以根本没有机会进行比较，会被认为是不同的对象。
         */

        /**
         * Java对于eqauls方法和hashCode方法是这样规定的：
         * - 如果两个对象相同，那么它们的hashCode值一定要相同。也告诉我们重写equals方法，一定要重写hashCode方法
         * - 如果两个对象的hashCode相同，它们并不一定相同，这里的对象相同指的是用eqauls方法比较。
         */

        System.out.println("========= set ============");
        Set<Cat> catSet = new HashSet<>();
        catSet.add(new Cat("chance", 11));
        catSet.add(new Cat("chance", 12));
        System.out.println(catSet.size());

        System.out.println("========= float ============");
        float f = 0.0f / 0.0f;
        System.out.println(Float.isNaN(f));
        System.out.println( (f != f) );

    }

}
