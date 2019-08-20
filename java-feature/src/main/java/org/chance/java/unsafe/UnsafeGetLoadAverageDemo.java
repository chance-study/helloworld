package org.chance.java.unsafe;

import lombok.extern.slf4j.Slf4j;
import org.chance.wrapper.UnsafeWrapper;
import sun.misc.Unsafe;

/**
 * UnsafeGetLoadAverageDemo
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/8/14
 */
@Slf4j
public class UnsafeGetLoadAverageDemo {

    public static void main(String[] args) {

        /**
         * //获取系统的平均负载值，loadavg这个double数组将会存放负载值的结果，nelems决定样本数量，nelems只能取值为1到3，
         * //分别代表最近1、5、15分钟内系统的平均负载。如果无法获取系统的负载，此方法返回-1，否则返回获取到的样本数量(loadavg中有效的元素个数)。
         * public native int getLoadAverage(double[] loadavg, int nelems);
         */

        Unsafe unsafe = UnsafeWrapper.getUnsafe();
        double[] loadAvg = new double[3];
        //获取在过去的1、5、15分钟CPU正在处理以及等待CPU处理的进程数之和的统计信息
        //也就是CPU使用队列的长度的统计信息
        unsafe.getLoadAverage(loadAvg, loadAvg.length);
        log.info("loadAvg:{}", loadAvg);
    }
}
