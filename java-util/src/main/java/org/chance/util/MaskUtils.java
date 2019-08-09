package org.chance.util;

import org.apache.commons.lang3.StringUtils;
import org.chance.constant.RegexConstant;

import java.util.regex.Pattern;

/**
 * MaskUtils
 *
 * @author GengChao &lt; catchance@163.com &gt;
 * @date 2019/8/2
 */
public class MaskUtils {

    private MaskUtils() {

    }

    /**
     * <code>
     * (?<=\w{2})\w{1,}(?=\w{3})
     * </code>
     */
    private static final String FORMATTER = "(?<=\\w{%d}).{%d,}(?=\\w{%d})";

    /**
     * 左边N个的正则
     */
    private static final String LEFT_N_PATTERN = "(?<=\\w{%d})";

    /**
     * 中间的正则
     */
    private static final String HIDE_SINGLE_CENTER_PATTERN = "\\w";

    /**
     * 中间的正则
     */
    private static final String HIDE_ALL_CENTER_PATTERN = "\\w+";


    /**
     * 右边M个的正则
     */
    private static final String RIGHT_M_PATTERN = "(?=\\w{%d})";

    /**
     * 替换中间所有字符串 n = 1 m = 1
     * a -&gt; a
     * ab -&gt; ab
     * abc -&gt; a*c
     * abcd -&gt; a**d
     * abcde -&gt; a***e
     *
     * @param original 原始字符串
     * @param n        左边N个
     * @param m        右边M个
     * @param maskChar 替换的字符
     * @return string
     */
    public static String hideSingleBerforNAfterM(String original, int n, int m, char maskChar) {

        if (StringUtils.isEmpty(original)) {
            return original;
        }

        if (original.length() <= n + m) {
            // 当长度小于 n + m 的时候直接返回原来的字符
            return original;
        }

        StringBuffer regex = new StringBuffer();

        if (n > 0) {
            regex.append(LEFT_N_PATTERN);
        }

        regex.append(HIDE_SINGLE_CENTER_PATTERN);

        if (m > 0) {
            regex.append(RIGHT_M_PATTERN);
        }
        if (n > 0 && m > 0) {
            // n、m 都大于0  (?<=\w{n})\w(?=\w{m})
            return original.replaceAll(String.format(regex.toString(), n, m), String.valueOf(maskChar));
        } else if (n > 0) {
            // n 大于0 m小于0 (?<=\w{n})\w
            return original.replaceAll(String.format(regex.toString(), n), String.valueOf(maskChar));
        } else {
            // m 大于0 n小于0 \w(?=\w{m})
            return original.replaceAll(String.format(regex.toString(), m), String.valueOf(maskChar));
        }

    }

    /**
     * 替换中间所有字符串 n = 1 m = 1  maskStr = ***
     * a -&gt; a
     * ab -&gt; ab
     * abc -&gt; a***b
     * abcd -&gt; a***b
     * abcde -&gt; a***e
     * abcdef -&gt; a***f
     *
     * @param original 原始字符串
     * @param n        左边N个
     * @param m        右边M个
     * @param maskStr  替换的字符
     * @return string
     */
    public static String hideAllBerforNAfterM(String original, int n, int m, String maskStr) {

        if (StringUtils.isEmpty(original)) {
            return original;
        }

        if (original.length() <= n + m) {
            // 当长度小于 n + m 的时候直接返回原来的字符
            return original;
        }

        StringBuffer regex = new StringBuffer();

        if (n > 0) {
            regex.append(LEFT_N_PATTERN);
        }

        regex.append(HIDE_ALL_CENTER_PATTERN);

        if (m > 0) {
            regex.append(RIGHT_M_PATTERN);
        }
        if (n > 0 && m > 0) {
            // n、m 都大于0  (?<=\w{n})\w+(?=\w{m})
            return original.replaceAll(String.format(regex.toString(), n, m), maskStr);
        } else if (n > 0) {
            // n 大于0 m小于0 (?<=\w{n})\w+
            return original.replaceAll(String.format(regex.toString(), n), maskStr);
        } else {
            // m 大于0 n小于0 \w+(?=\w{m})
            return original.replaceAll(String.format(regex.toString(), m), maskStr);
        }

    }

    /**
     * 姓名脱敏处理
     *
     * @param name the name
     * @return string
     */
    public static String hideName(String name) {

        boolean isMatch = Pattern.matches("[\u4e00-\u9fa5]{2,}", name);

        if (!isMatch) {
            return name;
        }

        StringBuilder sb = new StringBuilder(name);

        if (name.length() == 2) {
            return sb.replace(name.length() - 1, name.length(), "*").toString();
        }

        return sb.replace(name.length() - 2, name.length() - 1, "*").toString();

    }


    /**
     * 正则：手机号（精确）
     * <p>移动：134(0-8)、135、136、137、138、139、147、150、151、152、157、158、159、178、182、183、184、187、188、198</p>
     * <p>联通：130、131、132、145、155、156、175、176、185、186、166</p>
     * <p>电信：133、153、173、177、180、181、189、199</p>
     * <p>全球星：1349</p>
     * <p>虚拟运营商：170</p>
     * <p>
     * 手机号脱敏处理
     *
     * @param mobile the mobile
     * @return string
     */
    public static String hideMobile(String mobile) {

        boolean isMatch = Pattern.matches("^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$", mobile);

        if (!isMatch) {
            return mobile;
        }

        return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");

    }

    /**
     * 身份证脱敏处理
     *
     * @param idCard the id card
     * @return string
     */
    public static String hideIdCard(String idCard) {

        boolean isMatch = Pattern.matches("(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)", idCard);

        if (!isMatch) {
            return idCard;
        }

        return hideAllBerforNAfterM(idCard, 6, 4, "****");
    }

    /**
     * 银行卡脱敏处理
     *
     * @param cardNo the card no
     * @return string
     */
    public static String hideBankCard(String cardNo) {

        boolean isMatch = Pattern.matches("^[1-9]\\d{15,18}$", cardNo);

        if (!isMatch) {
            return cardNo;
        }

        return hideAllBerforNAfterM(cardNo, 6, 4, "****");
    }

    /**
     * email脱敏处理
     *
     * @param email the email
     * @return string
     */
    public static String hideEmail(String email) {

        boolean isMatch = Pattern.matches(RegexConstant.EMAIL, email);

        if (!isMatch) {
            return email;
        }

        return email.replaceAll("(\\w{2})\\w{3,}(\\w)@(\\w+)", "$1***$2@$3");
    }


    public static void main(String[] args) {

//        System.out.println(hideMobile("18856567878"));
//        System.out.println(hideMobile("16656567878"));
//        System.out.println(hideMobile("11156567878"));
//        System.out.println(hideMobile("11156567878"));

//        System.out.println(hideIdCard("33333319891205251x"));

//        System.out.println(hideBankCard("6226552654564585"));

        System.out.println(hideEmail("chance765@xyz.com"));

    }


}
