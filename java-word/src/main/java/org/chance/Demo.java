package org.chance;

import com.latextoword.Latex_Word;
import io.transpect.calabash.extensions.Ole2XmlConverter;

public class Demo {

    public static void main(String[] args) {
//        String latexStr="$ \\left( x + a \\right)^n  =  \\sum^n_{k = 0} \\left( \\frac{n}{k} \\right) x^k a^{n - k} $";
//        String omml= Latex_Word.latexToWord(latexStr);
//        System.out.println(omml);


        Ole2XmlConverter ole2XmlConverter = new Ole2XmlConverter();
        String mml = ole2XmlConverter.convertFormula("java-word/oleObject1.bin");



    }

}
