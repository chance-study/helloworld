package org.chance.dubbo.consumer.controller;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2019-09-06 14:58:09
 */
public class ExceptionUtil {

    public static String getStackTrace(Throwable throwable) {

        /**
         * StringWriter 不需要关闭
         * close flush方法上面都没有做
         */
        StringWriter sw = new StringWriter();

        try (PrintWriter pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            return sw.toString();
        }

    }

}
