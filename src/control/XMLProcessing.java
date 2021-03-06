package control;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/*
 * this class for storing book's description in XML file 
 */
public class XMLProcessing {
	private static ArrayList<String> ISBNs;
	private static ArrayList<String> titles;
	private static ArrayList<String> descs;

	// Read XML file to get information about: isbn, title and description, trần
	public void readXML() {

		ISBNs = new ArrayList<String>();
		titles = new ArrayList<String>();
		descs = new ArrayList<String>();

		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File("D:\\Documents\\JavaJEE\\TimSach.vn\\WebContent\\library1.xml");
		try {
			Document document = (Document) builder.build(xmlFile);
			Element rootNode = document.getRootElement();
			List<?> list = rootNode.getChildren("book");
			for (int i = 0; i < list.size(); i++) {
				Element node = (Element) list.get(i);
				ISBNs.add(node.getAttributeValue("ISBN"));
				titles.add(node.getChildText("title"));
				descs.add(node.getChildText("description"));
			}
		} catch (IOException io) {
			System.out.println(io.getMessage());
		} catch (JDOMException jdomex) {
			System.out.println(jdomex.getMessage());
		}
	}

	public static void ReadXMLUTF8FileSAX() {
		try {

			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			DefaultHandler handler = new DefaultHandler() {

				boolean title = false;
				boolean desc = false;

				public void startElement(String uri, String localName, String qName, Attributes attributes)
						throws SAXException {

//					System.out.println("Start Element :" + qName);

					if (qName.equalsIgnoreCase("title")) {
						title = true;
					}

					if (qName.equalsIgnoreCase("description")) {
						desc = true;
					}
				}

				public void endElement(String uri, String localName, String qName) throws SAXException {

//					System.out.println("End Element :" + qName);

				}

				public void characters(char ch[], int start, int length) throws SAXException {

					System.out.println(new String(ch, start, length));

					if (title) {
//						System.out.println(new String(ch, start, length));
						title = false;
					}

					if (desc) {
						System.out.println(new String(ch, start, length));
						desc = false;
					}
				}

			};

			File file = new File("D:\\Documents\\JavaJEE\\TimSach.vn\\WebContent\\library1.xml");
			InputStream inputStream = new FileInputStream(file);
			Reader reader = new InputStreamReader(inputStream, "UTF-8");

			InputSource is = new InputSource(reader);
			is.setEncoding("UTF-8");

			saxParser.parse(is, handler);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void writeXML(String isbn, String title, String desc) {
		readXML();
		// System.out.println(ISBNs.size());

		try {
			Element library = new Element("library");
			Document doc = new Document(library);
			doc.setRootElement(library);

			for (int i = 0; i < ISBNs.size(); i++) {
				Element book = new Element("book");
				book.setAttribute(new Attribute("ISBN", ISBNs.get(i)));
				book.addContent(new Element("title").setText(titles.get(i)));
				book.addContent(new Element("description").setText(descs.get(i)));
				doc.getRootElement().addContent(book);
			}

			Element book = new Element("book");
			book.setAttribute(new Attribute("ISBN", isbn));
			book.addContent(new Element("title").setText(title));
			book.addContent(new Element("description").setText(desc));
			doc.getRootElement().addContent(book);

			XMLOutputter xmlOutput = new XMLOutputter();

			// display nice nice
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(doc, new FileWriter("D:\\Documents\\JavaJEE\\TimSach.vn\\WebContent\\library1.xml"));
			// xmlOutput.output(doc, new FileWriter("D:\\Portable
			// Softwares\\Eclipse-JEE-Mars1\\library1.xml"));

			System.out.println("File Saved!");
		} catch (IOException io) {
			System.out.println(io.getMessage());
		}
	}

	public static ArrayList<String> getISBNs() {
		return ISBNs;
	}

	public static ArrayList<String> getTitles() {
		return titles;
	}

	public static ArrayList<String> getDescs() {
		return descs;
	}
	
	public static void main(String[] args) {
		ReadXMLUTF8FileSAX();
	}
}
