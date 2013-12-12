package pporan.maven.framework.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.Node;
import org.dom4j.QName;
import org.dom4j.io.SAXReader;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * XPath를 이용하여 XML을 처리하기 위한 클래스
 *
 * @version 1.0
 */
public class DomForXPath {
    private Node m_objNode = null;
    private Document m_doc = null;
    private Namespace m_ns = null;

    /**
     * xml Node 객체로 dom 객체를 생성
     * @param node
     */
    public DomForXPath(Node node) {
        m_objNode = node;
    }
    /**
     * well-formed xml 문자열로 dom 객체를 생성
     * @param xmlData
     */
    public DomForXPath(String xmlData) {
        this(xmlData, false);
    }
    /**
     * xml 파일이나 xml 문자열로 dom 객체를 생성
     * @param xmlData
     * @param isFile
     */
    public DomForXPath(String xmlData, boolean isFile) {
        this(xmlData, isFile, true);
    }
    /**
     * dom 객체를 생성한다.
     * @param xmlData
     * @param isFile
     * @param bDtd : DTD 검증여부
     */
    public DomForXPath(String xmlData, boolean isFile, boolean bDtd) {
        if (isFile) { // xmlData가 file
            _loadXmlFile(xmlData, bDtd);
        } else { // xmlData String
            _loadXmlString(xmlData, bDtd);
        }
    }

    /**
     * xml 파일에서 dom 객체를 생성
     * @param strFilePath
     * @param bDtd
     */
    private void _loadXmlFile(String strFilePath, boolean bDtd) {
        try {
            XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
            SAXReader reader = new SAXReader(parser);
            reader.setValidation(false);
            if (!bDtd) {
                reader.setEntityResolver(new EntityResolver() {
                    public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
                        return new InputSource(new StringReader(""));
                    }
                });
            }
            BufferedReader bin = new BufferedReader(new FileReader(strFilePath));
            m_objNode = reader.read(bin);
            m_doc = m_objNode.getDocument();
            Element root = m_doc.getRootElement();
            m_ns = root.getNamespace();
            fixNamespaces(m_doc);
            bin.close();
            reader = null;
        } catch (FileNotFoundException ex) {
			throw new RuntimeException(ex);
        } catch (IOException ex) {
			throw new RuntimeException(ex);
        } catch (DocumentException ex) {
			throw new RuntimeException(ex);
        } catch (SAXException ex) {
			throw new RuntimeException(ex);
        }
    }

    /**
     * xml 문자열을 파싱한다.
     * @param csXmlData
     * @param bDtd
     */
    private void _loadXmlString(String csXmlData, boolean bDtd) {
        if (null == csXmlData || csXmlData.equals("")) return;
        try {
            XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
            SAXReader reader = new SAXReader(parser);
            reader.setValidation(false);
            if (!bDtd) {
                reader.setEntityResolver(new EntityResolver() {
                    public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
                        return new InputSource(new StringReader(""));
                    }
                });
            }
            String newXmlData = csXmlData;
            BufferedReader bin = new BufferedReader(new StringReader(newXmlData));
            m_objNode = reader.read(bin);
            bin.close();
            bin = null;
            reader = null;
        } catch (IOException ex) {
			throw new RuntimeException(ex);
        } catch (DocumentException ex) {
			throw new RuntimeException(ex);
        } catch (SAXException ex) {
			throw new RuntimeException(ex);
        }
    }

    /**
     * xpath에 해당하는 노드를 새로운 문자열로 설정한다.
     * @param strXPath
     * @param strText
     */
    public void setText(String strXPath, String strText) {
        if (null == m_objNode) return;
        List<?> results = m_objNode.selectNodes(strXPath);
        Iterator<?> resultIter = results.iterator();
        while (resultIter.hasNext()) {
            Object theObj = resultIter.next();
            if (theObj instanceof Node) {
                Node node = (Node)theObj;
                List<?> childNodes = node.selectNodes(strXPath + "/*");
                Iterator<?> childeIter = childNodes.iterator();
                while (childeIter.hasNext()) {
                    Object childObj = childeIter.next();
                    if (childObj instanceof Node) {
                        Node childNode = (Node)childObj;
                        childNode.detach();
                    }
                }
                node.setText(strText);
            }
        }
    }

    /**
     * xpath에 해당하는 노드를 삭제한다.
     * @param strXPath
     */
    public void delete(String strXPath) {
        if (null == m_objNode) return;
        List<?> results = m_objNode.selectNodes(strXPath);
        Iterator<?> resultIter = results.iterator();
        while (resultIter.hasNext()) {
            Object theObj = resultIter.next();
            if (theObj instanceof Node) {
                Node node = (Node)theObj;
                node.detach();
            }
        }
    }

    /**
     * xpath 밑으로 노드를 추가한다.
     * @param strXPath
     * @param strXml
     */
    public void add(String strXPath, String strXml) {
        if (null == m_objNode) return;
        if (strXml.equals("")) return;

        List<?> results = m_objNode.selectNodes(strXPath);
        Iterator<?> resultIter = results.iterator();
        while (resultIter.hasNext()) {
            Object theObj = resultIter.next();
            if (theObj instanceof Element) {
                Element parent = (Element)theObj;
                try {
                    Document doc = DocumentHelper.parseText(strXml);
                    parent.add(doc.node(0));
                } catch (DocumentException ex) {
                    ex.printStackTrace(System.out);
                }
            }
        }
    }

    /**
     * xpath에 해당하는 노드에 xml body를 설정한다.
     * @param strXPath
     * @param strXml
     */
    public void setBody(String strXPath, String strXml) {
        if (null == m_objNode) return;
        List<?> results = m_objNode.selectNodes(strXPath);
        Iterator<?> resultIter = results.iterator();
        while (resultIter.hasNext()) {
            Object theObj = resultIter.next();
            if (theObj instanceof Node) {
                Node node = (Node)theObj;
                Element parent = node.getParent();
                node.detach();
                try {
                    Document doc = DocumentHelper.parseText(strXml);
                    parent.add(doc.node(0));
                } catch (DocumentException ex) {
                    ex.printStackTrace(System.out);
                }
            }
        }
    }

    /**
     * xpath에 해당하는 문자열을 얻어온다.
     * @param strXPath
     * @return
     */
    public String getText(String strXPath) {
    	return DomForXPath.getText(m_objNode, strXPath);
    }
    public static String getText(Node pNode, String strXPath) {
        if (null == pNode) return "";
        String nodeText = "";

        List<?> results = pNode.selectNodes(strXPath);
        Iterator<?> resultIter = results.iterator();
        while (resultIter.hasNext()) {
            Object theObj = resultIter.next();
            if (theObj instanceof Node) {
                Node node = (Node)theObj;
                nodeText = node.getText();
            } else {
                nodeText = theObj.toString();
            }
        }
        return nodeText;
    }

    /**
     * xpath에 해당하는 노드를 얻어온다.
     * @param strXPath
     * @return
     */
    public Node getNode(String strXPath) {
        if (null == m_objNode) return null;

        List<?> results = m_objNode.selectNodes(strXPath);
        Iterator<?> resultIter = results.iterator();
        while (resultIter.hasNext()) {
            Object theObj = resultIter.next();
            if (theObj instanceof Node) return (Node)theObj;
        }
        return null;
    }

    /**
     * xpath에 해당하는 노드의 xml을 얻어온다.
     * @param strXPath
     * @return
     */
    public String getBody(String strXPath) {
        if (null == m_objNode) return "";
        String xml = "";
        List<?> results = m_objNode.selectNodes(strXPath);
        Iterator<?> resultIter = results.iterator();
        while (resultIter.hasNext()) {
            Object theObj = resultIter.next();
            if (theObj instanceof Node) {
                Node node = (Node)theObj;
                xml = node.asXML();
            } else {
                xml = theObj.toString();
            }
        }
        return xml;
    }

    /**
     * xpath에 해당하는 노드의 xml을 얻어온다.(태그제외)
     * @param csXPath
     * @return
     */
    public String getBodyText(String csXPath) {
        String xml = getBody(csXPath);
        StringBuffer newMatchingStr = new StringBuffer();
        for ( int cnt = 0 ; cnt < csXPath.length() ; cnt++ ) {
            char curChar = csXPath.charAt(cnt);
            if ( curChar == '[' || curChar == ']' )
                newMatchingStr.append("\\").append(curChar);
            else
                newMatchingStr.append(curChar);
        }
        xml = xml.replaceAll("^<"+_getLastPathName(newMatchingStr.toString())+">","");
        xml = xml.replaceAll("</"+_getLastPathName(newMatchingStr.toString())+">\\z","");
        newMatchingStr = null;
        return xml;
    }

    /**
     * xpath에 해당하는 노드 리스트를 구한다.
     */
    public List<Node> getNodeList(String strXPath) {
        if (null == m_objNode) return null;

        List<?> results = m_objNode.selectNodes(strXPath);
        List<Node> arrList = new ArrayList<Node>();
        Iterator<?> resultIter = results.iterator();
        while (resultIter.hasNext()) {
            Object theObj = resultIter.next();
            if (theObj instanceof Node) arrList.add((Node)theObj);
            else continue;
        }
    	return arrList;
    }

    /**
     * xpath에 해당하는 노드의 리스트를 구한다. (문자열)
     * @param strXPath
     * @return
     */
    public List<String> getTextList(String strXPath) {
        if (null == m_objNode) return null;
        List<?> results = m_objNode.selectNodes(strXPath);
        List<String> arrList = new ArrayList<String>();
        Iterator<?> resultIter = results.iterator();
        while (resultIter.hasNext()) {
            Object theObj = resultIter.next();
            if (theObj instanceof Node) {
                Node node = (Node)theObj;
                arrList.add(node.getText());
            } else {
                continue;
            }
        }
        return arrList;
    }

    /**
     * xpath에 해당하는 노드의 리스트를 구한다. (xml body)
     * @param strXPath
     * @return
     */
    public List<String> getBodyList(String strXPath) {
        if (null == m_objNode) return null;
        List<?> results = m_objNode.selectNodes(strXPath);
        List<String> arrList = new ArrayList<String>();
        Iterator<?> resultIter = results.iterator();
        while (resultIter.hasNext()) {
            Object theObj = resultIter.next();
            if (theObj instanceof Node) {
                Node node = (Node)theObj;
                arrList.add(node.asXML());
            } else {
                continue;
            }
        }
        return arrList;
    }

    /**
     * xpath의 마지막 노드 값을 구한다.
     * @param xpath
     * @return
     */
    private static String _getLastPathName(String xpath) {
        String path = xpath.substring(xpath.lastIndexOf("/")+1, xpath.length());
        return path;
    }

    /**
     * xpath에 해당하는 노드 리스트를 구한다.
     */
    public static List<Node> getNodeList(Node objNode, String strXPath) {
        if (null == objNode) return null;

        List<?> results = objNode.selectNodes(strXPath);
        List<Node> arrList = new ArrayList<Node>();
        Iterator<?> resultIter = results.iterator();
        while (resultIter.hasNext()) {
            Object theObj = resultIter.next();
            if (theObj instanceof Node) arrList.add((Node)theObj);
            else continue;
        }
    	return arrList;
    }

    public static String getNodeString(Node curNode, String strXPath) {
    	Node node = curNode.selectSingleNode(strXPath);
    	if (null == node) return null;
    	return node.getText();
    }

    /**
     * node의 attribute를 구함
     */
    public static String getAttribute(Node curNode, String attributeName) {
    	Node node = curNode.selectSingleNode("@" + attributeName);
    	if (null == node) return null;
    	return node.getText();
    }

    /*** NAMESPACE 문제 ***/
	/**
	 * 네임스페이스 제거<br>
	 * XPath를 이용하려면 네임스페이스를 제거해야 함
	 */
	public static void fixNamespaces(Document doc) {
		Element root = doc.getRootElement();
		if (root.getNamespace() != Namespace.NO_NAMESPACE) removeNamespaces(root.content());
	}
	/**
	 * 네임스페이스 원복
	 */
	public static void unfixNamespaces(Document doc, Namespace original) {
		Element root = doc.getRootElement();
		if (original != null) setNamespaces(root.content(), original);
	}
	/**
	 * 목록내 모든 element의 네임스페이스 제거
	 */
	@SuppressWarnings("unchecked")
	private static void removeNamespaces(List l) {
		setNamespaces(l, Namespace.NO_NAMESPACE);
	}
	/**
	 * Element의 네임스페이스 설정(child포함)
	 */
	private static void setNamespaces(Element elem, Namespace ns) {
		setNamespace(elem, ns);
		setNamespaces(elem.content(), ns);
	}
	/**
	 * 목록내 node(child포함)의 네임스페이스 설정
	 */
	@SuppressWarnings("unchecked")
	private static void setNamespaces(List l, Namespace ns) {
		Node n = null;
		for (int i = 0; i < l.size(); i++) {
			n = (Node) l.get(i);
			if (n.getNodeType() == Node.ATTRIBUTE_NODE) ((Attribute) n).setNamespace(ns);
			if (n.getNodeType() == Node.ELEMENT_NODE) setNamespaces((Element) n, ns);
		}
	}
	/**
	 * Elemnet의 네임스페이스 설정
	 */
	private static void setNamespace(Element elem, Namespace ns) {
		elem.setQName(QName.get(elem.getName(), ns, elem.getQualifiedName()));
	}

	public Namespace getNamespace() { return m_ns; }
	public Document getDocument() { return m_doc; }
}
