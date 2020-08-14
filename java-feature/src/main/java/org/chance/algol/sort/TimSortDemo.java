package org.chance.algol.sort;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-28 15:41:51
 * <p>
 * o1 表示后面的数 o2 表示前面的数
 * 如果结果返回-1则表示要交换顺序
 * <p>
 * countRunAndMakeAscending 这个方法确定是顺序还是降序，并且将数组的一部分排列好。并返回未排列的起始位置
 * 将未排列的起始位置传递给binarySort进行二分插入排序。
 */
public class TimSortDemo {

    public static void main(String[] args) {

        List<Integer> array = Arrays.asList(1, 2, 6, 5, 8, 8, 4);

        Comparator<Integer> c0 = (o1, o2) -> {
            return -1;
        };

        Comparator<Integer> c = (o1, o2) -> {
            if (o1 < o2) {
                return 1;
            } else if (o1 > o2) {
                return -1;
            }
            return 0;
        };

        array.sort(c);

        System.out.println(array);

    }
}
