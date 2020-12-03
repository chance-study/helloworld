package org.chance;

import net.sf.mathocr.BatchProcessor;

import java.io.File;
import java.io.IOException;

public class OCRDemo {

    public static void main(String[] args) throws IOException {
        System.out.println(BatchProcessor.recognizeFormula(javax.imageio.ImageIO.read(new File("/Users/gengchao/work/果肉教育/教研中台/latex.png"))));

    }

}
