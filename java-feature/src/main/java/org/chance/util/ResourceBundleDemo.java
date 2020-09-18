package org.chance.util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-08-21 10:48:57
 */
public class ResourceBundleDemo {

    public static void main(String[] args) {
        // 加载资源文件
        ResourceBundle resource = ResourceBundle.getBundle("messages", Locale.ENGLISH);
        // 获取资源文件中的信息
        System.out.println(resource.getString("database.driver"));

        // 加载资源文件
        Locale locale1 = new Locale("zh", "CN");
        ResourceBundle resource1 = ResourceBundle.getBundle("messages", Locale.CHINA);
        System.out.println(resource1.getString("database.driver"));

    }


}
