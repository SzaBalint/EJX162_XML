package domejx162;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class DomWriteEJX162 {
    public static void main(String[] args) throws ParserConfigurationException, TransformerException{
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    //a dokumentumk�szit� gy�rb�l egy new Document j�n
    Document doc = builder.newDocument();
    Element root = doc.createElementNS("DOMEJX162", "users");
    doc.appendChild(root);
    root.appendChild(createUser(doc, "1", "Pal", "Kiss", "Web Developer"));
    root.appendChild(createUser(doc, "2", "Bela", "Nagy", "Manager"));
    root.appendChild(createUser(doc, "3", "Tamas", "Kovacs", "Java programozo"));
    //L�trehozunk egy gy�k�relemet, �s hozz�adjuk a dokumenthoz (appendChild())

    //Most k�vetkezik az XML f�jlba val� irunk
        //A Java DOM a Transformer objektumot haszn�lja az XML-f�jl l�trehoz�s�hoz
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transf = transformerFactory.newTransformer();

        //Be�llitjuk a dokumentum k�dol�s�t �s beh�z�s�t.
        transf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transf.setOutputProperty(OutputKeys.INDENT, "yes");
        transf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount","2");

        //Ez DOMSource tartalmazza a DOM f�t
        DOMSource source = new DOMSource(doc);

        File myFile = new File("users2.xml");

        //Irjunk egy konzolba �s egy f�jlba
        //StreamResult transzform�ci�s eredm�ny birtokosa
        StreamResult console = new StreamResult(System.out);
        StreamResult file = new StreamResult(myFile);

        //Az XML-forr�sokat a stream eredm�nyekhez irjuk.
        //a transform met�dus �talakitja az els� param�terk�nt kapott source objektumot
        transf.transform(source, console);
        transf.transform(source, file);
    }

    //A createUser() met�dusban �j felhaszn�l�i elem j�n l�tre createElement()
    private static Node createUser(Document doc, String id, String firstName, String lastName,String profession) {

        Element user = doc.createElement("user");
        //Az elem attrib�tum�-val van be�llitva setAttribute()
        user.setAttribute("id", id);
        user.appendChild(createUserElement(doc, "firstName", firstName));
        user.appendChild(createUserElement(doc, "lastName", lastName));
        user.appendChild(createUserElement(doc, "profession", profession));

        return user;
    }

    //Egy elem hozz�ad�dik a sz�l�j�hez a appendChild() �s sz�veges csom�pont j�n l�tre e
    private static Node createUserElement(Document doc, String name, String value){
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));

        return node;
    }
}