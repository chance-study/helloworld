package org.chance.elasticjob.util;

import org.springframework.core.env.Environment;

/**
 * SpringEnvPropertyUtils
 * 环境属性读取工具类
 *
 * @author catchance &lt;catchance@163.com&gt;
 * @date 2019-08-30 18:42:57
 */
public class SpringEnvPropertyUtils {

    public static String getStringPropertyValue(Environment environment, String key, String defaultValue) {
        return environment.getProperty(key, String.class, defaultValue);
    }

    public static Long getLongPropertyValue(Environment environment, String key, Long defaultValue) {
        return environment.getProperty(key, Long.class, defaultValue);
    }

    public static Integer getIntegerPropertyValue(Environment environment, String key, Integer defaultValue) {
        return environment.getProperty(key, Integer.class, defaultValue);
    }

    public static Boolean getBooleanPropertyValue(Environment environment, String key, Boolean defaultValue) {
        return environment.getProperty(key, Boolean.class, defaultValue);
    }
}
