package org.chance.java.lang;

import lombok.extern.slf4j.Slf4j;

/**
 * IntegerDemo
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/8/14
 */
@Slf4j
public class IntegerDemo {

    public static void main(String[] args) {
        /**
         * numberOfLeadingZeros
         * 该方法的作用是返回无符号整型i的最高非零位前面的0的个数，包括符号位在内；
         * 如果i为负数，这个方法将会返回0，符号位为1.
         */
        //二进制转int，二进制用0b开头
        log.info("numberOfLeadingZeros:" + Integer.numberOfLeadingZeros(0b0000_0000_0000_0000_0000_0000_0000_1010));
        log.info("numberOfLeadingZeros:" + Integer.numberOfLeadingZeros(0b1000_0000_0000_0000_0000_0000_0000_1010));


    }

}
