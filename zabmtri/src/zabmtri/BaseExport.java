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

@Deprecated
public abstract class BaseExport {

	protected Document doc;

	public BaseExport() {
		DocumentBuilder docBuilder = getDocumentBuilder();
		this.doc = docBuilder.newDocument();
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

	protected Node createNode(Node parent, String tag) {
		return createNode(parent, tag, null);
	}

	protected Node createNode(Node parent, String tag, Object value) {
		Node result = doc.createElement(tag);
		parent.appendChild(result);

		if (value != null) {
			result.setTextContent(value.toString());
		}

		return result;
	}

	protected Node node(String tag, Object value) {
		Node result = doc.createElement(tag);
		if (value != null) {
			result.setTextContent(value.toString());	
		}

		return result;
	}

	protected Node addAttr(Node node, String name, String value) {
		Attr attr = doc.createAttribute(name);
		attr.setValue(value);
		node.getAttributes().setNamedItem(attr);
		return node;
	}

	protected void saveToFile() {
		try {
			DOMSource source = new DOMSource(doc);

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();

			File file = new File(Util.outputFile(getOutputFileName()));
			StreamResult result = new StreamResult(file);

			System.out.println(file.getAbsolutePath());

			transformer.transform(source, result);
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	protected abstract String getOutputFileName();
}
