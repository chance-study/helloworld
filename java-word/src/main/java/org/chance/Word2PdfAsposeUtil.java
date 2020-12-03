package org.chance;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeManager;

import java.io.*;

public class Word2PdfAsposeUtil {

    public static void main(String[] args) throws Exception {

//        String docPath = "/Users/gengchao/work/果肉教育/教研中台/demos.docx";
//        String pdfPath = "/Users/gengchao/work/果肉教育/教研中台/demos.pdf";
//
//        XWPFDocument document;
//        InputStream doc = new FileInputStream(docPath);
//
//        document = new XWPFDocument(doc);
//        PdfOptions options = PdfOptions.create();
//        OutputStream out = new FileOutputStream(pdfPath);
//        PdfConverter.getInstance().convert(document, out, options);
//


//        String LibreOffice_HOME = "/Users/gengchao/work/果肉教育/教研中台/";
//		File file = new File("/Users/gengchao/work/果肉教育/教研中台/demos.docx");
//
//		DefaultOfficeManagerConfiguration configuration = new DefaultOfficeManagerConfiguration();
//		configuration.setOfficeHome(new File(LibreOffice_HOME));
//		configuration.setPortNumber(8100);
//		configuration.setTaskExecutionTimeout(1000 * 60 * 25L);
//		configuration.setTaskQueueTimeout(1000 * 60 * 60 * 24L);
//		OfficeManager officeManager = configuration.buildOfficeManager();
//		officeManager.start();
//		OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager);
//		converter.getFormatRegistry();
//		try {
//			converter.convert(file, new File("./demos.pdf"));
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			officeManager.stop();
//		}

		InputStream is = Word2PdfAsposeUtil.class.getClassLoader().getResourceAsStream("resources/license.xml");
		License aposeLic = new License();
		aposeLic.setLicense(is);
		File file = new File("/Users/gengchao/work/果肉教育/教研中台/小学数学.docx");
		File outFile = new File("/Users/gengchao/work/果肉教育/教研中台/小学数学.pdf");
		Document doc = new Document(file.getAbsolutePath());
		doc.save(outFile.getAbsolutePath(), SaveFormat.PDF);

    }



}
