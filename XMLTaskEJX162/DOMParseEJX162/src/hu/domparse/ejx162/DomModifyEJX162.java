package hu.domparse.ejx162;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.TreeWalker;
import org.xml.sax.SAXException;

public class DomModifyEJX162 {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException,
    XPathExpressionException, DOMException, ParseException {
		
		File xml = new File("C:\\Users\\B�lint\\Desktop\\EGYETEM\\2022-23. 5.f�l�v\\xml_beadando\\DOMParseEJX162\\src\\hu\\domparse\\ejx162\\XMLEJX162.xml");
		
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(xml);

        // a DOM document m�dos�t�sa
        DomModifier.modifyDom(document);

        // DOM document �talak�t�sa DOM DocumentTraversal form�ba
        DocumentTraversal traversal = (DocumentTraversal) document;

        //TreeWalker inicializ�l�sa
        TreeWalker walker = traversal.createTreeWalker(document.getDocumentElement(),
                NodeFilter.SHOW_ELEMENT | NodeFilter.SHOW_TEXT, null, true);

        //DOM bej�r�sa
        DomTraverser.traverseLevel(walker, "");

	}
	
    private static class DomModifier {
        public static void modifyDom(Document document) throws XPathExpressionException, DOMException, ParseException {
            XPathFactory factory = XPathFactory.newInstance();
            XPath xpath = factory.newXPath();

            // 1.) Kiss Imre nevenek megv�ltoztat�sa Nagyobb Imr�re
            Node owner = (Node) xpath.evaluate("//Tulajdonos[./Nev='Kiss Imre']",
                    document, XPathConstants.NODE);

            owner.setTextContent("Nagyobb Imre");

            // 2.) Minden 1300 forintn�l dr�g�bb pizz�nak az �ra cs�kken 10%-al
            NodeList pizzas = (NodeList) xpath.evaluate("//Pizza[./Ar>1300]/Ar", document, XPathConstants.NODESET);
            System.out.println(pizzas);
            for (int i = 0; i < pizzas.getLength(); i++) {
                Node pizza = pizzas.item(i);

                double price = Double.parseDouble(pizza.getTextContent());
                pizza.setTextContent(Double.toString(price * 0.9));
                
            }
        }
    }

    private static class DomTraverser {
        public static void traverseLevel(TreeWalker walker, String indent) {
            //Aktu�lis csom�pont
            Node node = walker.getCurrentNode();

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                printElementNode(node, indent);
            } else {
                printTextNode(node, indent);
            }

            // Rekurz�van megh�vjuk a bej�r�st a DOM f�ban
            for (Node n = walker.firstChild(); n != null; n = walker.nextSibling()) {
                traverseLevel(walker, indent + "    ");
            }

            walker.setCurrentNode(node);
        }

        private static void printElementNode(Node node, String indent) {
            System.out.print(indent + node.getNodeName());

            printElementAttributes(node.getAttributes());
        }

        private static void printElementAttributes(NamedNodeMap attributes) {
            int length = attributes.getLength();

            if (length > 0) {
                System.out.print(" [ ");

                for (int i = 0; i < length; i++) {
                    Node attribute = attributes.item(i);

                    System.out.printf("%s=%s%s", attribute.getNodeName(), attribute.getNodeValue(),
                            i != length - 1 ? ", " : "");
                }

                System.out.println(" ]");
            } else {
                System.out.println();
            }
        }

        private static void printTextNode(Node node, String indent) {
            String content_trimmed = node.getTextContent().trim();

            if (content_trimmed.length() > 0) {
                System.out.print(indent);
                System.out.printf("{ %s }%n", content_trimmed);
            }
        }
    }

}