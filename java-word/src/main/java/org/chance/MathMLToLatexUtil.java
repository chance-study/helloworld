package org.chance;

import fmath.conversion.ConvertFromLatexToMathML;
import fmath.conversion.ConvertFromMathMLToLatex;
import fmath.conversion.ConvertFromWordToMathML;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.xwpf.usermodel.*;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTOMath;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathPara;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MathMLToLatexUtil {

    public static String tMathMLToLatex(String sText)
    {
        String keys = "<math.*?</math>";
        Pattern pattern = Pattern.compile(keys);
        String strResult = "";

        try
        {
            Matcher matcher = pattern.matcher(sText);
            StringBuffer sbr = new StringBuffer();
            while (matcher.find())
            {
                String strTmp = matcher.group();
                try
                {
                    strTmp = strTmp.replace("<math xmlns=\'http://www.w3.org/1998/Math/MathML\'>", "<math>");
                    strTmp = strTmp.replaceAll("<math[^>]*>", "<math>");
                    strTmp = ConvertFromMathMLToLatex.convertToLatex(strTmp);
                    strTmp = strTmp.replace("\\", "##");
                    strTmp = "@@" + strTmp + "@@";
                }
                catch (Exception e)
                {
                    // TODO: handle exception
                    strTmp = "";
                    e.printStackTrace();
                }

                matcher.appendReplacement(sbr, strTmp);
            }
            matcher.appendTail(sbr);
            strResult = sbr.toString();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        strResult = strResult.replace("@@", "$");
        strResult = strResult.replace("##", "\\");

        return strResult;
    }

    public static String LatexToMathML(String inStr) {
        if(inStr==null||"".equals(inStr.trim())){
            return "";
        }
        //String test="$\\sin(\\alpha)^{\\theta}=\\sum_{i=0}^{n}(x^i + \\cos(f))$";
        String mathml = ConvertFromLatexToMathML.convertToMathML(inStr);
        return mathml;
    }

    public static String MathMLToLatex(String inStr) {
        if(inStr==null||"".equals(inStr.trim())){
            return "";
        }
        String latex = ConvertFromMathMLToLatex.convertToLatex(inStr);
        return latex;
    }

    public static void test() {

//        ArrayList<Question> list = new ArrayList<>();
        XWPFDocument doc = null;

        try {
            doc = new XWPFDocument(POIXMLDocument.openPackage("/Users/gengchao/work/果肉教育/教研中台/demo.docx"));

            //dom4j解析器的初始化
            SAXReader reader = new SAXReader(new DocumentFactory());
            Map<String, String> map=new HashMap<String, String>();
            map.put("xdr","http://schemas.openxmlformats.org/drawingml/2006/spreadsheetDrawing");
            map.put("m","http://schemas.openxmlformats.org/officeDocument/2006/math");
            reader.getDocumentFactory().setXPathNamespaceURIs(map); //xml文档的namespace设置

            InputSource source = new InputSource(doc.getPackagePart().getInputStream());
            source.setEncoding("utf-8");
            Document doc1 = reader.read(source);
            Element root = doc1.getRootElement();
            Element e = (Element)root.selectSingleNode("//m:oMathPara");    //用xpath得到OMML节点
            String omml = e.asXML();    //转为xml

            List<String> mathMLList = new ArrayList<String>();

//            String mml = convertOMML2MML(omml);
            String latex = MathMLToLatex(mathMLList.get(0));
            System.out.println(convertMML2Latex(mathMLList.get(0)));
            System.out.println(latex);
//            List<XWPFParagraph> paragraphs = doc.getParagraphs();
//            System.out.println(paragraphs.size());
//            /**
//             * 处理paragraphs
//             *         每个paragraphs是一行文本内容
//             */
//            String rule = "^[\\d]{1,3}$";        //正则表达式，^表示起始，$表示结束
////            String ruleA = "^[A-E]";
//            Pattern pTitle = Pattern.compile(rule);
//            //首先确定第一行为试卷标题。title
////            for (int i = 1;i < paragraphs.size();i++) {
//
////            List<XWPFParagraph> paragraphs = doc.getParagraphs();
//            for (XWPFParagraph paragraph : paragraphs) {
//
//                StringBuffer text = new StringBuffer();
//                List<XWPFRun> runs = paragraph.getRuns();
//                for (XWPFRun run : runs) {
//
//                    Node runNode = run.getCTR().getDomNode();
//                    text.append(getText(runNode));
//
//                    String math = getMath(run, runNode);
//                    text.append(math);
//                }
//                System.out.println("段落内容：".concat(text.toString()));
//            }
        }catch (Exception e1) {

            System.out.println(e1.getMessage());

            try {
                doc.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }


    /**
     * 获取字符串
     *
     * @param runNode
     * @return
     */
    private static String getText(Node runNode) {
        Node textNode = getChildNode(runNode, "w:t");
        if (textNode == null) {
            return "";
        }
        return textNode.getFirstChild().getNodeValue();
    }

    private static String getMath(XWPFRun run, Node runNode) throws Exception {
        Node objectNode = getChildNode(runNode, "w:object");
        if (objectNode == null) {
            return "";
        }
        Node shapeNode = getChildNode(objectNode, "v:shape");
        if (shapeNode == null) {
            return "";
        }
        Node imageNode = getChildNode(shapeNode, "v:imagedata");
        if (imageNode == null) {
            return "";
        }
        Node binNode = getChildNode(objectNode, "o:OLEObject");
        if (binNode == null) {
            return "";
        }

        XWPFDocument word = run.getDocument();

        NamedNodeMap shapeAttrs = shapeNode.getAttributes();
        // 图片在Word中显示的宽高
        String style = shapeAttrs.getNamedItem("style").getNodeValue();
        System.out.println("图片宽高：".concat(style));

        System.out.println("--------------");

        NamedNodeMap imageAttrs = imageNode.getAttributes();
        // 图片在Word中的ID
        String imageRid = imageAttrs.getNamedItem("r:id").getNodeValue();
        // 获取图片信息
        PackagePart imgPart = word.getPartById(imageRid);
        System.out.println("图片名称".concat(imgPart.getPartName().getName()));
        System.out.println(imgPart.getInputStream());

        System.out.println("--------------");

        NamedNodeMap binAttrs = binNode.getAttributes();
        // 公式二进制文件在Word中的ID
        String binRid = binAttrs.getNamedItem("r:id").getNodeValue();
        // 获取二进制文件
        PackagePart binPart = word.getPartById(binRid);
        System.out.println("二进制文件名称：".concat(binPart.getPartName().getName()));
        System.out.println(binPart.getInputStream());

        System.out.println("--------------");

        return "{公式#}";
    }

    private static Node getChildNode(Node node, String nodeName) {
        if (!node.hasChildNodes()) {
            return null;
        }
        NodeList childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            if (nodeName.equals(childNode.getNodeName())) {
                return childNode;
            }
            childNode = getChildNode(childNode, nodeName);
            if (childNode != null) {
                return childNode;
            }
        }
        return null;
    }


    /**
     * <p>Description: xsl转换器</p>
     */
    public static String xslConvert(String s, String xslpath, URIResolver uriResolver) throws FileNotFoundException {
        TransformerFactory tFac = TransformerFactory.newInstance();
        if(uriResolver != null)  tFac.setURIResolver(uriResolver);
//        File file = new File(xslpath);
        StreamSource xslSource = new StreamSource(CreateMathMLFromWorld.class.getResourceAsStream(xslpath));
//        StreamSource xslSource = new StreamSource(new FileInputStream(file));
        StringWriter writer = new StringWriter();
        try {
            Transformer t = tFac.newTransformer(xslSource);
            Source source = new StreamSource(new StringReader(s));
            Result result = new StreamResult(writer);
            t.transform(source, result);
        } catch (TransformerException e) {
//            logger.error(e.getMessage(), e);
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
        }
        return writer.getBuffer().toString();
    }

    /**
     * <p>Description: 将mathml转为latx </p>
     * @param mml
     * @return
     */
    public static String convertMML2Latex(String mml) throws FileNotFoundException {
//        mml = mml.substring(mml.indexOf("?>")+2, mml.length()); //去掉xml的头节点
        URIResolver r = new URIResolver(){  //设置xls依赖文件的路径
            @Override
            public Source resolve(String href, String base) throws TransformerException {
                InputStream inputStream = CreateMathMLFromWorld.class.getResourceAsStream("/Users/gengchao/workspace/helloworld/java-word/xsl/" + href);
                return new StreamSource(inputStream);
            }
        };
        String latex = xslConvert(mml, "/Users/gengchao/workspace/helloworld/java-word/xsl/mmltex.xsl", r);
        if(latex != null && latex.length() > 1){
            latex = latex.substring(1, latex.length() - 1);
        }
        return latex;
    }

    /**
     * <p>Description: office mathml转为mml </p>
     * @param xml
     * @return
     */
    public static String convertOMML2MML(String xml) throws FileNotFoundException {
        String result = xslConvert(xml, "/Users/gengchao/workspace/helloworld/java-word/OMML2MML.XSL", null);
        return result;
    }

    public static void main(String[] args) throws Exception {

        XWPFDocument doc = null;
//        doc = new XWPFDocument(POIXMLDocument.openPackage("/Users/gengchao/work/果肉教育/教研中台/demo.docx"));
//
//        List<String> mathMLList = new ArrayList<String>();
//        for (IBodyElement ibodyelement : doc.getBodyElements()) {
//            if (ibodyelement.getElementType().equals(BodyElementType.PARAGRAPH)) {
//                XWPFParagraph paragraph = (XWPFParagraph)ibodyelement;
//                for (CTOMath ctomath : paragraph.getCTP().getOMathList()) {
//                    mathMLList.add(WordReadFormulas.getMathML(ctomath));
//                }
//                for (CTOMathPara ctomathpara : paragraph.getCTP().getOMathParaList()) {
//                    for (CTOMath ctomath : ctomathpara.getOMathList()) {
//                        mathMLList.add(WordReadFormulas.getMathML(ctomath));
//                    }
//                }
//            } else if (ibodyelement.getElementType().equals(BodyElementType.TABLE)) {
//                XWPFTable table = (XWPFTable)ibodyelement;
//                for (XWPFTableRow row : table.getRows()) {
//                    for (XWPFTableCell cell : row.getTableCells()) {
//                        for (XWPFParagraph paragraph : cell.getParagraphs()) {
//                            for (CTOMath ctomath : paragraph.getCTP().getOMathList()) {
//                                mathMLList.add(WordReadFormulas.getMathML(ctomath));
//                            }
//                            for (CTOMathPara ctomathpara : paragraph.getCTP().getOMathParaList()) {
//                                for (CTOMath ctomath : ctomathpara.getOMathList()) {
//                                    mathMLList.add(WordReadFormulas.getMathML(ctomath));
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }

        String s = "";
//        s = mathMLList.get(0);
      s = ConvertFromWordToMathML.getMathMLFromDocFile("/Users/gengchao/work/果肉教育/教研中台/demo.docx");


      System.out.println(s);
      System.out.println("========");
      System.out.println(ConvertFromMathMLToLatex.convertToLatex(s));


//        test();
//        String latex="$\\sin(\\alpha)^{\\theta}=\\sum_{i=0}^{n}(x^i + \\cos(f))$";
//        String mml=LatexToMathML(latex);
//        String latex2=MathMLToLatex(mml);
//        System.out.println(mml);
//        System.out.println("===========================");
//        System.out.println(latex2);
    }


}
