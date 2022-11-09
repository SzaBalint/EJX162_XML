package xpathejx162;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import org.xml.sax.SAXException;
	
public class xPathEJX162 {

	public static void main(String[] args) {
		
		try {
			//File xmlFile = new File("student.xml");
			
			//DocumentBuilder l�trehoz�sa
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			
			Document document = documentBuilder.parse("studentEJX162.xml");
			
			document.getDocumentElement().normalize();
			
			//az XPath k�sz�t�se
			XPath xPath = XPathFactory.newInstance().newXPath();
		//1.
		//String expression="/class/student";
		//2.
		//String expression = "/class/student[@id=02]";
		//3.
		//String expression = "/class//*";
		//4.
		//String expression = "/class/student[position()=2]";
		//5.
		//String expression = "/class/student[last()]";
		//6.
		//String expression = "/class/student[last()-1]";
		//7.
		//String expression = "/class/student[position()<3]";
		//8.
		//String expression = "/class/*";
		//9.
		//String expression = "/class/student[@*]";
		//10.
		//String expression = "//*";
		//11.
		//String expression = "/class/student[kor>20]";
		//12.
		String expression = "/class/student/keresztnev | /class/student/vezeteknev";
			
		NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(document, XPathConstants.NODESET);
		
		//A for ciklus seg�ts�g�vel a NodeList csom�pontja v�gig kell iter�lni.
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			
			System.out.println("\nAktu�lis elem: " + node.getNodeName());
			
			//Meg kell vizsg�lni a csom�pontot, tesztelni kell a subelement
			if(node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("student")) {
				Element element = (Element) node;
				
				//az id attrib�tumot ad vissza
				System.out.println("Hallgat� ID: " + element.getAttribute("id"));
				
				//keresztnevet ad vissza
				System.out.println("Keresztn�v: " + element.getElementsByTagName("keresztnev").item(0).getTextContent());
				
				System.out.println("Vezet�kn�v: " + element.getElementsByTagName("vezeteknev").item(0).getTextContent());
				
				System.out.println("Becen�v: " + element.getElementsByTagName("becenev").item(0).getTextContent());
			}
		}
	} catch (ParserConfigurationException e) {
		e.printStackTrace();
	} catch (SAXException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} catch (XPathExpressionException e) {
		e.printStackTrace();
	}
}
}
