package org.chance.util;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.apache.commons.text.StringSubstitutor;
import org.junit.Test;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * StringUtilsTests
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/8/6
 */
public class StringUtilsTests {

    /**
     * %s 字符串类型                      "mingrisoft"
     * %c 字符类型                       'm'
     * %b 布尔类型                       true
     * %d 整数类型（十进制）               99
     * %x 整数类型（十六进制）             FF
     * %o 整数类型（八进制）              77
     * %f 浮点类型                      99.99
     * %a 十六进制浮点类型                FF.35AE
     * %e 指数类型                      9.38e+5
     * %g 通用浮点类型（f和e类型中较短的）
     * %h 散列码
     * %% 百分比类型                    ％
     * %n 换行符
     * %tx 日期与时间类型（x代表不同的日期与时间转换符
     * <p>
     * %n$ms：代表输出的是字符串，n代表是第几个参数，设置m的值可以在输出之前放置空格
     * %n$md：代表输出的是整数，n代表是第几个参数，设置m的值可以在输出之前放置空格
     * %n$mf：代表输出的是浮点数，n代表是第几个参数，设置m的值可以控制小数位数
     */
    @Test
    public void test_String_format() {

        assertThat(String.format("%1$s %2$d", "str", 2)).isEqualTo("str 2");

        assertThat(String.format("%s %d", "str", 2)).isEqualTo("str 2");

        assertThat(String.format("%s %d", "str", 2, 3)).isEqualTo("str 2");

        assertThat(String.format("%d", 2)).isEqualTo("2");

        assertThatThrownBy(() -> String.format("%s %d", "str")).hasMessageContaining("Format specifier '%d'");

        assertThatThrownBy(() -> String.format("%d %s", "str", 2));

    }


    /**
     * ### FormatElement
     * - { ArgumentIndex }：是从0开始的入参位置索引
     * - { ArgumentIndex , FormatType }
     * - { ArgumentIndex , FormatType , FormatStyle }
     * <p>
     * ### FormatType：指定使用不同的Format子类对入参进行格式化处理。值范围如下：
     * - number：调用NumberFormat进行格式化
     * - date：调用DateFormat进行格式化
     * - time：调用DateFormat进行格式化
     * - choice：调用ChoiceFormat进行格式化
     * <p>
     * ### FormatStyle：设置FormatType中使用的格式化样式。值范围如下：
     * short、medium、long、full、integer、currency、percent、SubformatPattern (子格式模式，形如#.##)
     */
    @Test
    public void test_MessageFormat_format() {
        assertThat(MessageFormat.format("{0} {1}", "str", 2)).isEqualTo("str 2");
        assertThat(MessageFormat.format("{0} {1} {2}", "str", 2)).isEqualTo("str 2 {2}");
        assertThat(MessageFormat.format("{0}", "str", 2)).isEqualTo("str");


        assertThat(MessageFormat.format("oh, {0,number,#.#} is a number", 3.1415)).isEqualTo("oh, 3.1 is a number");

    }

    /**
     *
     */
    @Test
    public void test_StrSubstitutor_format() {

        Map<String, Object> args = new HashMap<>();
        args.put("name", "chance");
        args.put("age", 25);
        args.put("sex", '男');

        //StrSubstitutor不是线程安全的类
        StrSubstitutor strSubstitutor = new StrSubstitutor(args, "${", "}");

        assertThat(strSubstitutor.replace("${name} ${age}")).isEqualTo("chance 25");
        assertThat(strSubstitutor.replace("${name}")).isEqualTo("chance");
        assertThat(strSubstitutor.replace("${name} ${age} ${other}")).isEqualTo("chance 25 ${other}");
        assertThat(strSubstitutor.replace("${name} ${age} ${other}")).isEqualTo("chance 25 ${other}");

    }

    @Test
    public void test_StringSubstitutor_format() {

        Map<String, Object> args = new HashMap<>();
        args.put("name", "chance");
        args.put("age", 25);
        args.put("sex", '男');

        //StringSubstitutor不是线程安全的类
        StringSubstitutor stringSubstitutor = new StringSubstitutor(args);

        assertThat(stringSubstitutor.replace("${name} ${age}")).isEqualTo("chance 25");
        assertThat(stringSubstitutor.replace("${name}")).isEqualTo("chance");
        assertThat(stringSubstitutor.replace("${name} ${age} ${other}")).isEqualTo("chance 25 ${other}");
        assertThat(stringSubstitutor.replace("${name} ${age} ${other}")).isEqualTo("chance 25 ${other}");

    }

}
