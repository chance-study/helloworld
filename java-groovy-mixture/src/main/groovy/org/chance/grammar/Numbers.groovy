package org.chance.grammar

/**
 * Created by gengchao on 15/12/26.
 */
class Numbers {

    public static void main(String[] args) {
        //primitive types
        byte b=1
        char c=2
        short s=3
        int i=4
        long l=5

        //infinite precision
        BigInteger bi=6
//For positive numbers:

        def a = 1
        assert a instanceof Integer

// Integer.MAX_VALUE
        def bb = 2147483647
        assert bb instanceof Integer

// Integer.MAX_VALUE + 1
        def cc = 2147483648
        assert cc instanceof Long

// Long.MAX_VALUE
        def d = 9223372036854775807
        assert d instanceof Long

// Long.MAX_VALUE + 1
        def e = 9223372036854775808
        assert e instanceof BigInteger


        //As well as for negative numbers:
        def na = -1
        assert na instanceof Integer

// Integer.MIN_VALUE
        def nb = -2147483648
        assert nb instanceof Integer

// Integer.MIN_VALUE - 1
        def nc = -2147483649
        assert nc instanceof Long

// Long.MIN_VALUE
        def nd = -9223372036854775808
        assert nd instanceof Long

// Long.MIN_VALUE - 1
        def ne = -9223372036854775809
        assert ne instanceof BigInteger

        /**
        * Binary literal
        * In Java 6 and before, as well as in Groovy, numbers could be represented in decimal, octal and hexadecimal bases,
        * and with Java 7 and Groovy 2, you can use a binary notation with the 0b prefix:
        * */
        int xInt = 0b10101111
        assert xInt == 175

        short xShort = 0b11001001
        assert xShort == 201 as short

        byte xByte = 0b11
        assert xByte == 3 as byte

        long xLong = 0b101101101101
        assert xLong == 2925l

        BigInteger xBigInteger = 0b111100100001
        assert xBigInteger == 3873g

        int xNegativeInt = -0b10101111
        assert xNegativeInt == -175

        /**
         * Octal literal
         * Octal numbers are specified in the typical format of 0 followed by octal digits.
         */
        int xInt1 = 077
        assert xInt1 == 63

        short xShort1 = 011
        assert xShort1 == 9 as short

        byte xByte1 = 032
        assert xByte1 == 26 as byte

        long xLong1 = 0246
        assert xLong1 == 166l

        BigInteger xBigInteger1 = 01111
        assert xBigInteger1 == 585g

        int xNegativeInt1 = -077
        assert xNegativeInt1 == -63


        DecimalLiterals();
    }

    /**
     * Hexadecimal numbers are specified in the typical
     * format of 0x followed by hex digits.
     */
    public static void HexadecimalLiteral(){
        int xInt = 0x77
        assert xInt == 119

        short xShort = 0xaa
        assert xShort == 170 as short

        byte xByte = 0x3a
        assert xByte == 58 as byte

        long xLong = 0xffff
        assert xLong == 65535l

        BigInteger xBigInteger = 0xaaaa
        assert xBigInteger == 43690g

        Double xDouble = new Double('0x1.0p0')
        assert xDouble == 1.0d

        int xNegativeInt = -0x77
        assert xNegativeInt == -119
    }

    /**
     *The decimal literal types are the same as in Java:
     * float
     * double
     * java.lang.BigDecimal
     */
    public static void DecimalLiterals(){
        //primitive types
        float f=1.234
        double d=2.345

        //infinite precision
        BigDecimal bd=3.456

        assert 1e3==1_000.0
        assert 2E4==20_000.0
        assert 3e+1==30.0
        assert 4E-2==0.04
        assert 5e-1==0.5
    }

    /**
     * Underscore in literals
     * When writing long literal numbers, it’s harder on the eye to figure
     * out how some numbers are grouped together, for example with
     * groups of thousands, of words, etc. By allowing you to place
     * underscore in number literals, it’s easier to spot those groups:
     */
    public static void underscoreInLiterals(){
        long creditCardNumber = 1234_5678_9012_3456L
        long socialSecurityNumbers = 999_99_9999L
        double monetaryAmount = 12_345_132.12
        long hexBytes = 0xFF_EC_DE_5E
        long hexWords = 0xFFEC_DE5E
        long maxLong = 0x7fff_ffff_ffff_ffffL
        long alsoMaxLong = 9_223_372_036_854_775_807L
        long bytes = 0b11010010_01101001_10010100_10010010

    }

    /**
     * Number type suffixes
     * BigInteger G or g
     Long L or l
     Integer I or i
     BigDecimal G or g
     Double D or d
     Float F or f
     */
    public static void numberTypeSuffixes(){
        assert 42I == new Integer('42')
        assert 42i == new Integer('42') // lowercase i more readable
        assert 123L == new Long("123") // uppercase L more readable
        assert 2147483648 == new Long('2147483648') // Long type used, value too large for an Integer
        assert 456G == new BigInteger('456')
        assert 456g == new BigInteger('456')
        assert 123.45 == new BigDecimal('123.45') // default BigDecimal type used
        assert 1.200065D == new Double('1.200065')
        assert 1.234F == new Float('1.234')
        assert 1.23E23D == new Double('1.23E23')
        assert 0b1111L.class == Long // binary
        assert 0xFFi.class == Integer // hexadecimal
        assert 034G.class == BigInteger // octal
    }

    /**
     * power operator(**)
     *
     */
    public static void powerOperator(){
        // base and exponent are ints and the result can be represented by an Integer
        assert    2    **   3    instanceof Integer    //  8
        assert   10    **   9    instanceof Integer    //  1_000_000_000

// the base is a long, so fit the result in a Long
// (although it could have fit in an Integer)
        assert    5L   **   2    instanceof Long       //  25

// the result can't be represented as an Integer or Long, so return a BigInteger
        assert  100    **  10    instanceof BigInteger //  10e20
        assert 1234    ** 123    instanceof BigInteger //  170515806212727042875...

// the base is a BigDecimal and the exponent a negative int
// but the result can be represented as an Integer
        assert    0.5  **  -2    instanceof Integer    //  4

// the base is an int, and the exponent a negative float
// but again, the result can be represented as an Integer
        assert    1    **  -0.3f instanceof Integer    //  1

// the base is an int, and the exponent a negative int
// but the result will be calculated as a Double
// (both base and exponent are actually converted to doubles)
        assert   10    **  -1    instanceof Double     //  0.1

// the base is a BigDecimal, and the exponent is an int, so return a BigDecimal
        assert    1.2  **  10    instanceof BigDecimal //  6.1917364224

// the base is a float or double, and the exponent is an int
// but the result can only be represented as a Double value
        assert    3.4f **   5    instanceof Double     //  454.35430372146965
        assert    5.6d **   2    instanceof Double     //  31.359999999999996

// the exponent is a decimal value
// and the result can only be represented as a Double value
        assert    7.8  **   1.9  instanceof Double     //  49.542708423868476
        assert    2    **   0.1f instanceof Double     //  1.0717734636432956
    }

}


