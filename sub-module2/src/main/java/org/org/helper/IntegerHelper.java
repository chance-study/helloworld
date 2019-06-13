package org.org.helper;

/**
 * Created by gengchao on 16/4/23.
 */
public class IntegerHelper {
    /**
     * 使用jdk自带函数Integer.toBinaryString() 函数不能完整显示整型数据的二进制格式。
     * 例如Integer.toBinaryString(2)，运行结果为：10，
     * 但真正的完整而进制格式为：00000000000000000000000000000010。
     * 打印整型数值的完整二进制格式
     * @param x
     * @return
     */
    public static String toFullBinaryString(int x) {
        int[] buffer = new int[Integer.SIZE];
        for (int i = (Integer.SIZE - 1); i >= 0; i--) {
            buffer[i] = x >> i & 1;
        }
        String s = "";
        for (int j = (Integer.SIZE - 1); j >= 0; j--) {
            s = s + buffer[j];
        }
        return s;
    }

}
