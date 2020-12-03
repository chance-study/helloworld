package org.chance.demo1;

import com.microsoft.schemas.vml.CTShape;
import fmath.conversion.ConvertFromMathMLToLatex;
import io.transpect.calabash.extensions.Ole2XmlConverter;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.openxmlformats.schemas.drawingml.x2006.main.CTGraphicalObject;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;
import org.openxmlformats.schemas.drawingml.x2006.picture.CTPicture;

import org.openxmlformats.schemas.officeDocument.x2006.math.CTOMath;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathPara;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTRPR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.util.*;

public class CreateMathMLFromWorld {
    private static File stylesheet = new File("/Users/gengchao/workspace/helloworld/java-word/OMML2MML.XSL");
    private static TransformerFactory tFactory = TransformerFactory.newInstance();
    private static StreamSource stylesource = new StreamSource(stylesheet);


    /**
     * 获取MathML
     * @param ctomath
     * @return
     * @throws Exception
     */
    static String getMathML(CTOMath ctomath) throws Exception {

        Transformer transformer = tFactory.newTransformer(stylesource);

        Node node = ctomath.getDomNode();

        DOMSource source = new DOMSource(node);
        StringWriter stringwriter = new StringWriter();
        StreamResult result = new StreamResult(stringwriter);
        transformer.setOutputProperty("omit-xml-declaration", "yes");
        transformer.transform(source, result);

        String mathML = stringwriter.toString();
        stringwriter.close();

        mathML = mathML.replaceAll("xmlns:m=\"http://schemas.openxmlformats.org/officeDocument/2006/math\"", "");
        mathML = mathML.replaceAll("xmlns:mml", "xmlns");
        mathML = mathML.replaceAll("mml:", "");

        return mathML;
    }

    public static String convertMML2Latex(String mml) throws FileNotFoundException {

        if(mml.indexOf("?>") > 0) {
            mml = mml.substring(mml.indexOf("?>")+2, mml.length()); //去掉xml的头节点
        }

        URIResolver r = new URIResolver(){  //设置xls依赖文件的路径
            @Override
            public Source resolve(String href, String base) {
                InputStream inputStream = null;

                try {
                    inputStream = new FileInputStream("java-word/xsl/mmltex.xsl");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                return new StreamSource(inputStream);

            }
        };
        System.out.println("hh"+ConvertFromMathMLToLatex.convertToLatex(mml));
        String latex = xslConvert(mml, "java-word/xsl/mmltex.xsl", null);
        if(latex != null && latex.length() > 1){
            // latex $ xxx $ 取消首尾的$
            latex = latex.substring(1, latex.length() - 1);
        }
        return latex;
    }

    public static String xslConvert(String s, String xslpath, URIResolver uriResolver) throws FileNotFoundException {
        TransformerFactory tFac = TransformerFactory.newInstance();
        if(uriResolver != null)  tFac.setURIResolver(uriResolver);
        StreamSource xslSource = new StreamSource(new FileInputStream(xslpath));
        StringWriter writer = new StringWriter();
        try {
            Transformer t = tFac.newTransformer(xslSource);
            Source source = new StreamSource(new StringReader(s));
            Result result = new StreamResult(writer);
            t.transform(source, result);
        } catch (TransformerException e) {
        }
        return writer.getBuffer().toString();
    }

    public static String convertOMML2MML(XWPFDocument doc) throws IOException, DocumentException {

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
        Element e = (Element)root.selectSingleNode("//m:oMath");    //用xpath得到OMML节点
        String omml = e.asXML();    //转为xml

        System.out.println(convertMML2Latex(convertOMML2MML(omml)));

        return null;

    }

    public static String convertOMML2MML(String xml) throws FileNotFoundException {

        String result = xslConvert(xml, "java-word/OMML2MML.XSL", null);
        return result;
    }
    public static void main(String[] args) throws Exception {

        XWPFDocument doc = null;
        doc = new XWPFDocument(POIXMLDocument.openPackage("/Users/gengchao/work/果肉教育/教研中台/demos.docx"));

//        System.out.println(convertOMML2MML(doc));

        List<String> mathMLList = new ArrayList<String>();
        for (IBodyElement ibodyelement : doc.getBodyElements()) {
            if (ibodyelement.getElementType().equals(BodyElementType.PARAGRAPH)) {
                XWPFParagraph paragraph = (XWPFParagraph)ibodyelement;
                for (CTOMath ctomath : paragraph.getCTP().getOMathList()) {
                    mathMLList.add(getMathML(ctomath));
                }
                for (CTOMathPara ctomathpara : paragraph.getCTP().getOMathParaList()) {
                    for (CTOMath ctomath : ctomathpara.getOMathList()) {
                        mathMLList.add(getMathML(ctomath));
                    }
                }

                StringBuffer text = new StringBuffer();

                XmlCursor pc = paragraph.getCTP().newCursor();
                pc.selectPath("./*");
                while(pc.toNextSelection()) {
                    XmlObject po = pc.getObject();
                    if(po instanceof CTR) {
                        CTR ctr = (CTR) po ;
                        //对子元素进行遍历
                        XmlCursor c = ctr.newCursor();
                        //这个就是拿到所有的子元素：
                        c.selectPath("./*");
                        while (c.toNextSelection()) {
                            XmlObject o = c.getObject();
                            //如果子元素是<w:drawing>这样的形式，使用CTDrawing保存图片
                            if (o instanceof CTDrawing) {
                                CTDrawing drawing = (CTDrawing) o;
                                CTInline[] ctInlines = drawing.getInlineArray();
                                for (CTInline ctInline : ctInlines) {
                                    CTGraphicalObject graphic = ctInline.getGraphic();
                                    //
                                    XmlCursor cursor = graphic.getGraphicData().newCursor();
                                    cursor.selectPath("./*");
                                    while (cursor.toNextSelection()) {
                                        XmlObject xmlObject = cursor.getObject();
                                        // 如果子元素是<pic:pic>这样的形式
                                        if (xmlObject instanceof CTPicture) {
                                            org.openxmlformats.schemas.drawingml.x2006.picture.CTPicture picture = (org.openxmlformats.schemas.drawingml.x2006.picture.CTPicture) xmlObject;
                                            //拿到元素的属性 rid8 xwpfDocument.getPictureDataByID(pictureId); 通过这个拿到图片
                                            XWPFPictureData xwpfPictureData = doc.getPictureDataByID(picture.getBlipFill().getBlip().getEmbed());
                                            text.append(xwpfPictureData.getFileName());
                                        }
                                    }
                                }
                            }
                            //使用CTObject保存图片
                            // <w:object>形式
                            if (o instanceof CTObject) {
                                CTObject object = (CTObject) o;
                                System.out.println(object);
                                XmlCursor w = object.newCursor();
                                w.selectPath("./*");
                                while (w.toNextSelection()) {
                                    XmlObject xmlObject = w.getObject();
                                    if (xmlObject instanceof CTShape) {
                                        CTShape shape = (CTShape) xmlObject;
                                        XWPFPictureData xwpfPictureData = doc.getPictureDataByID(shape.getImagedataArray()[0].getId2());
                                    }
                                }

                            }
                        }

                        Node runNode = ctr.getDomNode();
                        text.append(getText(runNode));

                        String math = getMathTypeMath(doc, runNode);
                        text.append(math);
                    } else if (po instanceof CTRPR) {

                    } else if (po instanceof CTOMath) {
                        CTOMath ctoMath = (CTOMath) po;
                        text.append(convertMML2Latex(getMathML(ctoMath)));
                    }

                }

                List<XWPFRun> runs = paragraph.getRuns();
                for (XWPFRun run : runs) {
//
//                    CTR ctr = run.getCTR();
//                    //对子元素进行遍历
//                    XmlCursor c = ctr.newCursor();
//                    //这个就是拿到所有的子元素：
//                    c.selectPath("./*");
//                    while (c.toNextSelection()) {
//                        XmlObject o = c.getObject();
//                        //如果子元素是<w:drawing>这样的形式，使用CTDrawing保存图片
//                        if (o instanceof CTDrawing) {
//                            CTDrawing drawing = (CTDrawing) o;
//                            CTInline[] ctInlines = drawing.getInlineArray();
//                            for (CTInline ctInline : ctInlines) {
//                                CTGraphicalObject graphic = ctInline.getGraphic();
//                                //
//                                XmlCursor cursor = graphic.getGraphicData().newCursor();
//                                cursor.selectPath("./*");
//                                while (cursor.toNextSelection()) {
//                                    XmlObject xmlObject = cursor.getObject();
//                                    // 如果子元素是<pic:pic>这样的形式
//                                    if (xmlObject instanceof CTPicture) {
//                                        org.openxmlformats.schemas.drawingml.x2006.picture.CTPicture picture = (org.openxmlformats.schemas.drawingml.x2006.picture.CTPicture) xmlObject;
//                                        //拿到元素的属性 rid8 xwpfDocument.getPictureDataByID(pictureId); 通过这个拿到图片
//                                        XWPFPictureData xwpfPictureData = doc.getPictureDataByID(picture.getBlipFill().getBlip().getEmbed());
//                                        text.append(xwpfPictureData.getFileName());
//                                    }
//                                }
//                            }
//                        }
//                        //使用CTObject保存图片
//                        // <w:object>形式
//                        if (o instanceof CTObject) {
//                            CTObject object = (CTObject) o;
//                            System.out.println(object);
//                            XmlCursor w = object.newCursor();
//                            w.selectPath("./*");
//                            while (w.toNextSelection()) {
//                                XmlObject xmlObject = w.getObject();
//                                if (xmlObject instanceof CTShape) {
//                                    CTShape shape = (CTShape) xmlObject;
//                                    XWPFPictureData xwpfPictureData = doc.getPictureDataByID(shape.getImagedataArray()[0].getId2());
//                                }
//                            }
//
//                        }
//                    }
//
//                    Node runNode = run.getCTR().getDomNode();
//                    text.append(getText(runNode));
//
//                    String math = getMath(run, runNode);
//                    text.append(math);
                }
                System.out.println("text : " + text);

            } else if (ibodyelement.getElementType().equals(BodyElementType.TABLE)) {
                XWPFTable table = (XWPFTable)ibodyelement;
                for (XWPFTableRow row : table.getRows()) {
                    for (XWPFTableCell cell : row.getTableCells()) {
                        for (XWPFParagraph paragraph : cell.getParagraphs()) {
                            for (CTOMath ctomath : paragraph.getCTP().getOMathList()) {
                                mathMLList.add(getMathML(ctomath));
                            }
                            for (CTOMathPara ctomathpara : paragraph.getCTP().getOMathParaList()) {
                                for (CTOMath ctomath : ctomathpara.getOMathList()) {
                                    mathMLList.add(getMathML(ctomath));
                                }
                            }
                        }
                    }
                }
            }
        }

        String s = mathMLList.get(0);

        System.out.println(s);
        System.out.println("========");
//        System.out.println(ConvertFromMathMLToLatex.convertToLatex(s));
        System.out.println(convertMML2Latex(s));


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

    private static String getMathTypeMath(XWPFDocument word, Node runNode) throws Exception {
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

//        Ole2XmlConverter ole2XmlConverter = new Ole2XmlConverter();
//        String mml = ole2XmlConverter.convertFormula(binPart.get);

        System.out.println("--------------");

        return "{公式#}";
    }

    private static String getMathTypeMath(XWPFRun run, Node runNode) throws Exception {
        return getMathTypeMath(run.getDocument(), runNode);
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

}
