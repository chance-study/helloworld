package org.chance.algol.sort;

import java.util.Arrays;

/**
 * 插入排序算法
 * [基于Java实现的插入排序算法](https://www.cnblogs.com/captainad/p/10957006.html)
 * [十大经典排序算法最强总结（含JAVA代码实现）](https://www.cnblogs.com/guoyaohua/p/8600214.html)
 * <p>
 * 一般来说，插入排序都采用in-place在数组上实现。具体算法描述如下：
 * 从第一个元素开始，该元素可以认为已经被排序；
 * 取出下一个元素，在已经排序的元素序列中从后向前扫描；
 * 如果该元素（已排序）大于新元素，将该元素移到下一位置；
 * 重复步骤3，直到找到已排序的元素小于或者等于新元素的位置；
 * 将新元素插入到该位置后；
 * 重复步骤2~5。
 * <p>
 * 最佳情况：T(n) = O(n)   最坏情况：T(n) = O(n2)   平均情况：T(n) = O(n2)
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-27 15:49:22
 */
public class InsertionSort {

    public static int[] sort(int[] array) {

        int len;
        // 基本情况下的数组可以直接返回
        if (array == null || (len = array.length) == 0 || len == 1) {
            return array;
        }
        int current;
        for (int i = 0; i < len - 1; i++) {
            // 第一个数默认已排序，从第二个数开始
            current = array[i + 1];
            // 前一个数的下标
            int preIdx = i;

            // 拿当前的数与之前已排序序列逐一往前比较，
            // 如果比较的数据比当前 的大，就把该数往后挪一步
            while (preIdx >= 0 && current < array[preIdx]) {
                array[preIdx + 1] = array[preIdx];
                preIdx--;
            }
            // while循环跳出说明找到了位置
            array[preIdx + 1] = current;
        }
        return array;
    }

    public static void main(String[] args) {
        int[] array = new int[]{23, 64, 8, 35, 97, 28, 17};
        System.out.println(Arrays.toString(sort(array)));
    }

}
