package belsoelem;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxEJX162_2 {
	public static void main(String[] args) {
		try {
			/*
			 * Dokumentumolvasó létrehozása, a gyár objektumot a SAXParserFactory osztály
			 * newInstance metódusa segítségével készítjük el.
			 */
			SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
			// példányosítja a SAX értelmezõt, a visszakapott gyár állítja elõ a SAX
			// elemzõt.
			SAXParser saxParser = saxParserFactory.newSAXParser();
			// saját eseménykezelõ objektum létrehozása
			SaxHandler handler = new SaxHandler();
			// dokumentum értelmezési folyamatának elindítása,
			// A parse metódus elsõ paramétere a beolvasandó XML fájl neve, a második
			// paramétere a kezelõobjektum
			saxParser.parse(new File("EJX162_kurzusfelvetel.xml"), handler);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}
}

// Tartalomkezelõ keret létrehozása, valamint az esemény- és hibakezelõ
// metódusok definiálása

// a DefaultHandler-bõl való leszármaztatással és a megfelelõ metódusainak
// definiálásával történik.

class SaxHandler extends DefaultHandler {
	private int indent = 0;

	private String formatAttributes(Attributes attributes) {
		int attrLength = attributes.getLength();
		if (attrLength == 0)
			return "";
		StringBuilder sb = new StringBuilder(", {");
		for (int i = 0; i < attrLength; i++) {
			sb.append(attributes.getLocalName(i) + "=" + attributes.getValue(i));
			if (i < attrLength - 1) {
				sb.append(", ");
			}
		}
		sb.append("}");
		return sb.toString();
	}

	private void indent() {
		for (int i = 0; i < indent; i++) {
			System.out.print("  ");
		}
	}

	// Esemény kezelő metódusok létrehozása, startElement metódus

	// elem kezdete és vége
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) {

		if (indent == 2) {
			indent++;
			indent();
			System.out.print(qName + formatAttributes(attributes) + " start ");
		} else {
			indent++; // behúz mindent
			indent(); // start húzza be
			System.out.println(qName + formatAttributes(attributes) + " start");
		}

	}

	// elem kezdete és vége
	@Override
	public void endElement(String uri, String localName, String qName) {

		if (indent == 3) {
			System.out.println(qName + " end");
		} else {
			indent(); // end húzza be, behúz mindent
			indent--; // behúzás csökkentése, start húzza be
			System.out.println(qName + " end");
		}

	}

	// Szövegelem feldolgozása, characters metódust újraimplementáljuk
	@Override
	public void characters(char ch[], int start, int length) { // szövegtartalmat egy tömbbe tároljuk le
		String chars = new String(ch, start, length).trim();
		if (indent == 3 && !chars.isEmpty()) {
			System.out.print(chars + " ");
		} else if (!chars.isEmpty()) {
			indent++; // behúz mindent
			indent(); // karakter behúzása
			indent--; // behúzás csökkentése
			System.out.println(chars);
		}
	}
}