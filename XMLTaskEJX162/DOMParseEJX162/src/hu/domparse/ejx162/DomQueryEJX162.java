package hu.domparse.ejx162;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DomQueryEJX162 {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		File file = new File("C:\\\\Users\\\\B�lint\\\\Desktop\\\\EGYETEM\\\\2022-23. 5.f�l�v\\\\XML\\\\XMLTaskEJX162\\\\DOMParseEJX162\\\\XMLEJX162.xml");
		// Parse-ol�s
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(file);
		doc.getDocumentElement().normalize();
		// Root element kiirasa
		System.out.print("Gyoker element: ");
		System.out.println(doc.getDocumentElement().getNodeName());
		NodeList nList = doc.getElementsByTagName("Pizzeria");

		// Minden pizzeria attrib�tum kiiratasa

		System.out.println("PIZZERIAK");
		for (int i = 0; i < nList.getLength(); i++) {
			Node node = nList.item(i);
			System.out.println("\nElement nev : " + node.getNodeName());
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element elem = (Element) node;
				System.out.println("ID:" + elem.getAttribute("Pikod"));
				NodeList nList2 = elem.getChildNodes();
				for (int j = 0; j < nList2.getLength(); j++) {
					Node node2 = nList2.item(j);
					if (node2.getNodeType() == Node.ELEMENT_NODE) {
						Element elem2 = (Element) node2;
						if (!node2.getNodeName().equals("Cim")) {
							System.out.println(node2.getNodeName() + " : " + node2.getTextContent());
						} else {
							System.out.println("Cim:");

							NodeList nList3 = elem2.getChildNodes();
							for (int k = 0; k < nList3.getLength(); k++) {
								Node node3 = nList3.item(k);

								if (node3.getNodeType() == Node.ELEMENT_NODE) {

									System.out.println("	" + node3.getNodeName() + " : " + node3.getTextContent());
								}
							}
						}
					}
				}
			}
		}
		//Kiirja annak a pizzerianak a nevet, ami Pesten van
		System.out.println("\nPESTI PIZZERIA\n");
		for (int i = 0; i < nList.getLength(); i++) {
			Node node = nList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element elem = (Element) node;
				NodeList nList2 = elem.getChildNodes();
				for (int j = 0; j < nList2.getLength(); j++) {
					Node node2 = nList2.item(j);
					if (node2.getNodeType() == Node.ELEMENT_NODE) {
						Element elem2 = (Element) node2;
						NodeList nList3 = elem2.getChildNodes();
						for (int k = 0; k < nList3.getLength(); k++) {
							Node node3 = nList3.item(k);
							if (node3.getNodeType() == Node.ELEMENT_NODE) {
								if (node3.getNodeName().equals("Varos")) {
									if (node3.getTextContent().equals("Budapest")) {
										node2 = nList2.item(1);
										System.out.println(node2.getNodeName() + ": " + node2.getTextContent());
									}

								}

							}
						}

					}
				}
			}
		}
	}
}