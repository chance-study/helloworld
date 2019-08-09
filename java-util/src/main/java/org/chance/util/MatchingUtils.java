package org.chance.util;

import org.apache.commons.lang3.StringUtils;
import org.chance.constant.RegexConstant;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * MatchingUtils
 *
 * @author GengChao &lt; catchance@163.com &gt;
 * @date 2019/8/2
 */
public class MatchingUtils {

    /**
     * 地区码
     */
    private final static Map<Integer, String> ZONE_NUM = new HashMap<>();

    static {
        ZONE_NUM.put(11, "北京");
        ZONE_NUM.put(12, "天津");
        ZONE_NUM.put(13, "河北");
        ZONE_NUM.put(14, "山西");
        ZONE_NUM.put(15, "内蒙古");
        ZONE_NUM.put(21, "辽宁");
        ZONE_NUM.put(22, "吉林");
        ZONE_NUM.put(23, "黑龙江");
        ZONE_NUM.put(31, "上海");
        ZONE_NUM.put(32, "江苏");
        ZONE_NUM.put(33, "浙江");
        ZONE_NUM.put(34, "安徽");
        ZONE_NUM.put(35, "福建");
        ZONE_NUM.put(36, "江西");
        ZONE_NUM.put(37, "山东");
        ZONE_NUM.put(41, "河南");
        ZONE_NUM.put(42, "湖北");
        ZONE_NUM.put(43, "湖南");
        ZONE_NUM.put(44, "广东");
        ZONE_NUM.put(45, "广西");
        ZONE_NUM.put(46, "海南");
        ZONE_NUM.put(50, "重庆");
        ZONE_NUM.put(51, "四川");
        ZONE_NUM.put(52, "贵州");
        ZONE_NUM.put(53, "云南");
        ZONE_NUM.put(54, "西藏");
        ZONE_NUM.put(61, "陕西");
        ZONE_NUM.put(62, "甘肃");
        ZONE_NUM.put(63, "青海");
        ZONE_NUM.put(64, "新疆");
        ZONE_NUM.put(71, "台湾");
        ZONE_NUM.put(81, "香港");
        ZONE_NUM.put(82, "澳门");
        ZONE_NUM.put(91, "外国");
    }

    /**
     * 奇偶校验 parity bit
     */
    private final static int[] PARITYBIT = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};

    private final static int[] POWER_LIST = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10,
            5, 8, 4, 2};


    private MatchingUtils() {

    }

    /**
     * 判断是否是姓名
     *
     * @param name the name
     * @return boolean
     */
    public static boolean isChineseName(String name) {
        return Pattern.matches(StringUtils.join(RegexConstant.CHINESE_CHARACTER, "{2,6}"), name);
    }


    /**
     * 判断是否是手机号
     * <p>
     * 正则：手机号（精确）
     *
     * <p>移动：134(0-8)、135、136、137、138、139、147、150、151、152、157、158、159、178、182、183、184、187、188、198</p>
     * <p>联通：130、131、132、145、155、156、175、176、185、186、166</p>
     * <p>电信：133、153、173、177、180、181、189、199</p>
     * <p>全球星：1349</p>
     * <p>虚拟运营商：170</p>
     * <p>
     * 手机号脱敏处理
     *
     * @param mobile the mobile
     * @return boolean
     */
    public static boolean isChineseMobile(String mobile) {
        return Pattern.matches(RegexConstant.CHINESE_MOBILE, mobile);
    }

    /**
     * ((^\(0\d{2,3}\))|(^0\d{2,3}[- ]?))?\d{7,8}$
     * <p>
     * 可以匹配到如下：
     * - 022-22334455
     * - 02912345678
     * - 029 12345678
     * - 12345678
     * - (010)88886666
     *
     * @param phone 固定电话号码
     * @return boolean
     */
    public static boolean isChinesePhone(String phone) {
        return Pattern.matches(RegexConstant.CHINESE_PHONE, phone);
    }

    /**
     * 身份证合法性校验（默认不开启强校验）
     *
     * @param idCard the id card
     * @return boolean
     */
    public static boolean isChineseIdCard(String idCard) {
        return isChineseIdCard(idCard, false);
    }

    /**
     * <p>
     * 身份证合法性校验
     * </p>
     *
     * <pre>
     * - 15位身份证号码：第7、8位为出生年份(两位数)，第9、10位为出生月份，第11、12位代表出生日期，第15位代表性别，奇数为男，偶数为女。
     * - 18位身份证号码：第7、8、9、10位为出生年份(四位数)，第11、第12位为出生月份，第13、14位代表出生日期，第17位代表性别，奇数为男，偶数为女。
     *    最后一位为校验位
     * </pre>
     * <p>
     * 18位身份证号码验证
     * 1、号码的结构
     * 公民身份号码是特征组合码，由十七位数字本体码和一位校验码组成。从左至右依次为：六位数字地址码，
     * 八位数字出生日期码，三位数字顺序码和一位数字校验码。
     * 2、地址码(前六位数）
     * 表示编码对象常住户口所在县(市、旗、区)的行政区划代码，按GB/T2260的规定执行。
     * 3、出生日期码（第七位至十四位）
     * 表示编码对象出生的年、月、日，按GB/T7408的规定执行，年、月、日代码之间不用分隔符。
     * 4、顺序码（第十五位至十七位）
     * 表示在同一地址码所标识的区域范围内，对同年、同月、同日出生的人编定的顺序号，
     * 顺序码的奇数分配给男性，偶数分配给女性。
     * 5、校验码（第十八位数）
     * （1）十七位数字本体码加权求和公式 S = Sum(Ai Wi), i = 0, , 16 ，先对前17位数字的权求和 ;
     * Ai:表示第i位置上的身份证号码数字值; Wi:表示第i位置上的加权因子 Wi: 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2
     * （2）计算模 Y = mod(S, 11)
     * （3）通过模( 0 1 2 3 4 5 6 7 8 9 10)得到对应的校验码 Y:1 0 X 9 8 7 6 5 4 3 2
     * <p>
     * 正则表达式：
     * (^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}$)
     * <p>
     * (^[1-9]\d{5}(18|19|([23]\d))\d{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))\d{3}$)
     *
     * <p>
     * 判断是否是身份证
     *
     * @param idCard the id card
     * @param strong the strong
     * @return boolean
     */
    public static boolean isChineseIdCard(String idCard, boolean strong) {

        boolean isMatch = Pattern.matches(RegexConstant.CHINESE_IDCARD, idCard);

        if (!strong || !isMatch) {
            return isMatch;
        }
        // strong && isMatch
        //校验区位码
        if (!ZONE_NUM.containsKey(Integer.valueOf(idCard.substring(0, 2)))) {
            return false;
        }

        int year = getYearByIdCard(idCard);

        if (isLeapYear(year) &&
                Objects.equals("0229",
                        idCard.length() == 15 ? idCard.substring(8, 12) : idCard.substring(10, 14))) {
            return false;
        }


        if (idCard.length() == 15) {
            // 15位身份证没有校验位
            return true;
        }

        //校验"校验码"
        // 18位身份证校验校验位
        final char[] cs = idCard.toUpperCase().toCharArray();

        //校验位数
        int power = 0;

        for (int i = 0; i < cs.length - 1; i++) {
            power += (cs[i] - '0') * POWER_LIST[i];
        }
        //校验最后一位校验位
        return cs[cs.length - 1] == PARITYBIT[power % 11];

    }

    /**
     * 根据身份编号获取生日年
     *
     * @param idCard 身份编号
     * @return 生日(yyyy)
     */
    private static int getYearByIdCard(String idCard) {
        Integer len = idCard.length();
        if (len == 15) {
            return Integer.parseInt(StringUtils.join("19", idCard.substring(6, 8)));
        } else {
            return Integer.parseInt(idCard.substring(6, 10));
        }
    }

    /**
     * 判断是否为闰年
     */
    private static boolean isLeapYear(int year) {
        return (year % 400 == 0) || (year % 100 != 0 && year % 4 == 0);
    }

    /**
     * 判断是否是电子邮箱
     *
     * @param email the email
     * @return boolean
     */
    public static boolean isEmail(String email) {
        return Pattern.matches(RegexConstant.EMAIL, email);
    }

    /**
     * 判断是否是户口本
     * 户口本
     * 规则： 15位数字, 18位数字, 17位数字 + X
     * 样本： 441421999707223115
     *
     * @param card the card
     * @return boolean boolean
     */
    public static boolean isAccountCard(String card) {
        return Pattern.matches(RegexConstant.ACCOUNT_CARD, card);
    }


    /**
     * 判断是否是军官证
     * 军官证
     * 规则： 军/兵/士/文/职/广/（其他中文） + "字第" + 4到8位字母或数字 + "号"
     * 样本： 军字第2001988号, 士字第P011816X号
     *
     * @param card the card
     * @return boolean
     */
    public static boolean isOfficerCard(String card) {
        return Pattern.matches(RegexConstant.OFFICER_CARD, card);
    }

    /**
     * 判断是否是护照
     * 护照
     * 规则： 14/15开头 + 7位数字, G + 8位数字, P + 7位数字, S/D + 7或8位数字,等
     * 样本： 141234567, G12345678, P1234567
     *
     * @param card the card
     * @return boolean
     */
    public static boolean isPassPortCard(String card) {
        return Pattern.matches(RegexConstant.PASSPORT_CARD, card);
    }


    /**
     * 判断是否是港澳居民来往内地通行证
     * 港澳居民来往内地通行证
     * 规则： H/M + 10位或6位数字
     * 样本： H1234567890
     *
     * @param card the card
     * @return boolean
     */
    public static boolean isHKCard(String card) {
        return Pattern.matches(RegexConstant.HK_CARD, card);
    }

    /**
     * 判断是否是台湾居民来往大陆通行证
     * 台湾居民来往大陆通行证
     * 规则： 新版8位或18位数字， 旧版10位数字 + 英文字母
     * 样本： 12345678 或 1234567890B
     *
     * @param card the card
     * @return boolean
     */
    public static boolean isTWCard(String card) {
        return Pattern.matches(RegexConstant.TW_CARD, card);
    }


    public static void main(String[] args) {

        System.out.println(isEmail("a@abc.com"));
        System.out.println(isEmail("a@abc"));

        System.out.println(isChinesePhone("022-22334455"));
        System.out.println(isChinesePhone("02912345678"));
        System.out.println(isChinesePhone("029 12345678"));
        System.out.println(isChinesePhone("12345678"));
        System.out.println(isChinesePhone("(010)88886666"));
        System.out.println(isChineseIdCard("342601199504171234", false));
        System.out.println(isChineseIdCard("320926040431527", true));

    }


}
