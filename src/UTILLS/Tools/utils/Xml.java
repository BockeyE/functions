package UTILLS.Tools.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/***
 * 基于JDK w3c 实现轻量级XML解析器,XML文档类
 * 
 * @author PanJun
 *
 */
public class Xml {

    /** XML 元素 */
    public static class Elem {

        private Node node;

        private Elem(Node node) {
            this.node = node;
        }

        /** 不会返回空值 */
        public String attr(String attrName) {
            if (node instanceof Element) {
                Attr eAttr = ((Element) node).getAttributeNode(attrName);
                if (eAttr == null) {
                    return "";
                } else {
                    return NULL.nvl(eAttr.getTextContent()).trim();
                }
            }
            return "";
        }

        /** 不会返回空值 */
        public String text() {
            return NULL.nvl(node.getTextContent());
        }

        public String textTrim() {
            return NULL.nvl(node.getTextContent()).trim();
        }

        public List<Elem> elements() {
            return elements("");
        }

        public Elem element(String name) {
            NodeList nodeList = node.getChildNodes();
            for (int k = 0; k < nodeList.getLength(); k++) {
                Node n = nodeList.item(k);
                String nodeName = n.getNodeName();
                int i = nodeName.indexOf(":");// 忽略命名空间
                if (i > -1) {
                    nodeName = nodeName.substring(i + 1);
                }
                if (n instanceof Element && nodeName.equals(name)) {
                    return new Elem(n);
                }
            }
            return null;
        }

        public Elem elementTo(String... names) {
            Elem ret = this;
            for (String name : names) {
                ret = ret.element(name);
                if (ret == null) {
                    return null;
                }
            }
            return ret;
        }

        public List<Elem> elements(String name) {
            List<Elem> ret = new ArrayList<Elem>();
            boolean isBlank = name == null || name.trim().length() == 0;
            NodeList nodeList = node.getChildNodes();
            for (int k = 0; k < nodeList.getLength(); k++) {
                Node n = nodeList.item(k);
                if (n instanceof Element) {
                    if (isBlank || name.equals(n.getNodeName())) {
                        ret.add(new Elem(n));
                    }
                }
            }
            return ret;
        }

        public String name() {
            return node.getNodeName();
        }

    }

    /** XML解析运行时异常，用来包装处理，申明式异常 */
    public static class XmlError extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public XmlError() {
        }

        public XmlError(String msg) {
            super(msg);
        }

        public XmlError(Throwable e) {
            super(e);
        }

        public XmlError(String msg, Throwable e) {
            super(msg, e);
        }

    }

    /** W3C DOC */
    private Document doc;

    public Xml(InputStream in) {
        initXml(in);
    }

    private void initXml(InputStream in) {
        try {
            try {
                doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(in);
            } finally {
                in.close();
            }
        } catch (Exception e) {
            throw new XmlError(e);
        }
    }

    public Xml(File file) {
        try {
            initXml(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new XmlError("The Xml file[" + file.getAbsolutePath() + "] doesn't exist!");
        }
    }

    public Xml(String xml) {
        this(new ByteArrayInputStream(xml.getBytes()));
    }

    public Elem getRoot() {
        return new Elem(doc.getDocumentElement());
    }

}
