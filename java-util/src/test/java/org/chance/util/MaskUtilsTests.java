package org.chance.util;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MaskUtilsTests {

    @Test
    public void hideName() {
        assertThat(MaskUtils.hideName("王多")).isEqualTo("王*");
        assertThat(MaskUtils.hideName("王多余")).isEqualTo("王*余");
        assertThat(MaskUtils.hideName("司徒多余")).isEqualTo("司徒*余");
        assertThat(MaskUtils.hideName("司徒多多余")).isEqualTo("司徒多*余");
        assertThat(MaskUtils.hideName("爱新觉罗媚")).isEqualTo("爱新觉*媚");
    }


    @Test
    public void test() {

        assertThat(MaskUtils.hideAllBerforNAfterM("a", 1, 3, "***")).isEqualTo("a");
        assertThat(MaskUtils.hideAllBerforNAfterM("ab", 1, 3, "***")).isEqualTo("ab");
        assertThat(MaskUtils.hideAllBerforNAfterM("abc", 1, 3, "***")).isEqualTo("abc");
        assertThat(MaskUtils.hideAllBerforNAfterM("abcd", 1, 3, "***")).isEqualTo("abcd");
        assertThat(MaskUtils.hideAllBerforNAfterM("abcde", 1, 3, "***")).isEqualTo("a***cde");
        assertThat(MaskUtils.hideAllBerforNAfterM("abcdef", 1, 3, "***")).isEqualTo("a***def");
    }

    @Test
    public void testLeftZero() {

        assertThat(MaskUtils.hideAllBerforNAfterM("a", 0, 3, "***")).isEqualTo("a");
        assertThat(MaskUtils.hideAllBerforNAfterM("ab", 0, 3, "***")).isEqualTo("ab");
        assertThat(MaskUtils.hideAllBerforNAfterM("abc", 0, 3, "***")).isEqualTo("abc");
        assertThat(MaskUtils.hideAllBerforNAfterM("abcd", 0, 3, "***")).isEqualTo("***bcd");
        assertThat(MaskUtils.hideAllBerforNAfterM("abcde", 0, 3, "***")).isEqualTo("***cde");
        assertThat(MaskUtils.hideAllBerforNAfterM("abcdef", 0, 3, "***")).isEqualTo("***def");
    }

    @Test
    public void testRightZero() {

        assertThat(MaskUtils.hideAllBerforNAfterM("a", 2, 0, "***")).isEqualTo("a");
        assertThat(MaskUtils.hideAllBerforNAfterM("ab", 2, 0, "***")).isEqualTo("ab");
        assertThat(MaskUtils.hideAllBerforNAfterM("abc", 2, 0, "***")).isEqualTo("ab***");
        assertThat(MaskUtils.hideAllBerforNAfterM("abcd", 2, 0, "***")).isEqualTo("ab***");
        assertThat(MaskUtils.hideAllBerforNAfterM("abcde", 2, 0, "***")).isEqualTo("ab***");
        assertThat(MaskUtils.hideAllBerforNAfterM("abcdef", 2, 0, "***")).isEqualTo("ab***");
    }


    @Test
    public void testHideSingleBerforNAfterM() {

        assertThat(MaskUtils.hideSingleBerforNAfterM("a", 1, 3, '*')).isEqualTo("a");
        assertThat(MaskUtils.hideSingleBerforNAfterM("ab", 1, 3, '*')).isEqualTo("ab");
        assertThat(MaskUtils.hideSingleBerforNAfterM("abc", 1, 3, '*')).isEqualTo("abc");
        assertThat(MaskUtils.hideSingleBerforNAfterM("abcd", 1, 3, '*')).isEqualTo("abcd");
        assertThat(MaskUtils.hideSingleBerforNAfterM("abcde", 1, 3, '*')).isEqualTo("a*cde");
        assertThat(MaskUtils.hideSingleBerforNAfterM("abcdef", 1, 3, '*')).isEqualTo("a**def");

        assertThat(MaskUtils.hideSingleBerforNAfterM("a", 2, 0, '*')).isEqualTo("a");
        assertThat(MaskUtils.hideSingleBerforNAfterM("ab", 2, 0, '*')).isEqualTo("ab");
        assertThat(MaskUtils.hideSingleBerforNAfterM("abc", 2, 0, '*')).isEqualTo("ab*");
        assertThat(MaskUtils.hideSingleBerforNAfterM("abcd", 2, 0, '*')).isEqualTo("ab**");


        assertThat(MaskUtils.hideSingleBerforNAfterM("a", 0, 2, '*')).isEqualTo("a");
        assertThat(MaskUtils.hideSingleBerforNAfterM("ab", 0, 2, '*')).isEqualTo("ab");
        assertThat(MaskUtils.hideSingleBerforNAfterM("abc", 0, 2, '*')).isEqualTo("*bc");
        assertThat(MaskUtils.hideSingleBerforNAfterM("abcd", 0, 2, '*')).isEqualTo("**cd");
    }

}