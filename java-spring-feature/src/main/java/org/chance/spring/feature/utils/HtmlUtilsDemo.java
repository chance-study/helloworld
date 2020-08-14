package org.chance.spring.feature.utils;

import org.springframework.web.util.HtmlUtils;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-08-03 15:16:46
 */
public class HtmlUtilsDemo {

    public static void main(String[] args) {
        String specialStr = "#<table id=\"testid\"><tr>test1;test2</tr></table>";
        // 转义（用转义字符表示）： #&lt;table id=&quot;testid&quot;&gt;&lt;tr&gt;test1;test2&lt;/tr&gt;&lt;/table&gt;
        String str1 = HtmlUtils.htmlEscape(specialStr);
        System.out.println(str1);

        // 转义（用数字字符表示）： #&#60;table id=&#34;testid&#34;&#62;&#60;tr&#62;test1;test2&#60;/tr&#62;&#60;/table&#62;
        String str2 = HtmlUtils.htmlEscapeDecimal(specialStr);
        System.out.println(str2);

        // 转义（用16进制表示）
        String str3 = HtmlUtils.htmlEscapeHex(specialStr);
        System.out.println(str3);

        // 返转义，一个方法即可 结果见上面 specialStr
        System.out.println(HtmlUtils.htmlUnescape(str1));
        System.out.println(HtmlUtils.htmlUnescape(str2));
        System.out.println(HtmlUtils.htmlUnescape(str3));
    }

}
