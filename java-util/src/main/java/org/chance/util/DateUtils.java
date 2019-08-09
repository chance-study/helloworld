package org.chance.util;

/**
 * DateUtils
 *
 * @author GengChao &lt; catchance@163.com &gt;
 * @date 2019 /8/8
 */
public class DateUtils {

    private DateUtils() {

    }

    /**
     * 判断是否为闰年
     *
     * @param year the year
     * @return the boolean
     */
    public static boolean isLeapYear(int year) {
        return (year % 400 == 0) || (year % 100 != 0 && year % 4 == 0);
    }

}
