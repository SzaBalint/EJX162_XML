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
    //a dokumentumkészitõ gyárból egy new Document jön
    Document doc = builder.newDocument();
    Element root = doc.createElementNS("DOMEJX162", "users");
    doc.appendChild(root);
    root.appendChild(createUser(doc, "1", "Pal", "Kiss", "Web Developer"));
    root.appendChild(createUser(doc, "2", "Bela", "Nagy", "Manager"));
    root.appendChild(createUser(doc, "3", "Tamas", "Kovacs", "Java programozo"));
    //Létrehozunk egy gyökérelemet, és hozzáadjuk a dokumenthoz (appendChild())

    //Most következik az XML fájlba való irunk
        //A Java DOM a Transformer objektumot használja az XML-fájl létrehozásához
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transf = transformerFactory.newTransformer();

        //Beállitjuk a dokumentum kódolását és behúzását.
        transf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transf.setOutputProperty(OutputKeys.INDENT, "yes");
        transf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount","2");

        //Ez DOMSource tartalmazza a DOM fát
        DOMSource source = new DOMSource(doc);

        File myFile = new File("users2.xml");

        //Irjunk egy konzolba és egy fájlba
        //StreamResult transzformációs eredmény birtokosa
        StreamResult console = new StreamResult(System.out);
        StreamResult file = new StreamResult(myFile);

        //Az XML-forrásokat a stream eredményekhez irjuk.
        //a transform metódus átalakitja az elsõ paraméterként kapott source objektumot
        transf.transform(source, console);
        transf.transform(source, file);
    }

    //A createUser() metódusban új felhasználói elem jön létre createElement()
    private static Node createUser(Document doc, String id, String firstName, String lastName,String profession) {

        Element user = doc.createElement("user");
        //Az elem attribútumá-val van beállitva setAttribute()
        user.setAttribute("id", id);
        user.appendChild(createUserElement(doc, "firstName", firstName));
        user.appendChild(createUserElement(doc, "lastName", lastName));
        user.appendChild(createUserElement(doc, "profession", profession));

        return user;
    }

    //Egy elem hozzáadódik a szülõjéhez a appendChild() és szöveges csomópont jön létre e
    private static Node createUserElement(Document doc, String name, String value){
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));

        return node;
    }
}