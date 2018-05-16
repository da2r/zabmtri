package zabmtri;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class GlAccountExport {

	Document doc;

	public void execute() {
		DocumentBuilder docBuilder = getDocumentBuilder();
		doc = docBuilder.newDocument();

		Node top = doc.createElement("NMEXML");
		addAttr(top, "BranchCode", "123");
		doc.appendChild(top);

		saveToFile();
		System.out.println("File saved!");
	}

	private void saveToFile() {
		try {
			DOMSource source = new DOMSource(doc);
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();

			File file = new File(Util.outputFile("output-glaccount.xml"));
			StreamResult result = new StreamResult(file);
			
			System.out.println(file.getAbsolutePath());
			
			transformer.transform(source, result);
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	private DocumentBuilder getDocumentBuilder() {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			return docBuilder;
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		}
	}

	protected Attr addAttr(Node node, String name, String value) {
		Attr attr = doc.createAttribute(name);
		attr.setValue(value);
		node.getAttributes().setNamedItem(attr);
		return attr;
	}
}
