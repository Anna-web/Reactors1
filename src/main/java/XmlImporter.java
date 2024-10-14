
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class XmlImporter extends FileImporter {

    @Override
    public void importFile(File file, ReactorStorages reactorMap) throws IOException {
        if (file.getName().endsWith(".xml")) {
            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                try (var fileInputStream = new FileInputStream(file)) {
                    Document doc = builder.parse(fileInputStream);
                    doc.getDocumentElement().normalize();
                    NodeList nodeList = doc.getDocumentElement().getChildNodes();

                    for (int i = 0; i < nodeList.getLength(); i++) {
                        if (nodeList.item(i).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                            Element element = (Element) nodeList.item(i);
                            Reactor reactor = parseReactor(element);
                            reactorMap.addReactor(element.getNodeName(), reactor);
                        }
                    }
                }
            } catch (IOException | ParserConfigurationException | SAXException e) {}
        } else
            if (next != null) {
            next.importFile(file, reactorMap);
        } else {
            System.out.println("Unsupported file format");
        }
    }

    private Reactor parseReactor(Element element) {
        String type = element.getNodeName();
        String reactor_class = getElementTextContent(element, "class");
        Double burnup = getDoubleElementTextContent(element, "burnup");
        Double kpd = getDoubleElementTextContent(element, "kpd");
        Double enrichment = getDoubleElementTextContent(element, "enrichment");
        Double thermal_capacity = getDoubleElementTextContent(element, "termal_capacity");
        Double electrical_capacity = getDoubleElementTextContent(element, "electrical_capacity");
        Integer life_time = getIntElementTextContent(element, "life_time");
        Double first_load = getDoubleElementTextContent(element, "first_load");

        return new Reactor(type, reactor_class, burnup, kpd, enrichment, thermal_capacity, electrical_capacity, life_time, first_load, "XML");
    }

    private String getElementTextContent(Element element, String tagName) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        return nodeList.getLength() > 0 ? nodeList.item(0).getTextContent() : "";
    }

    private Double getDoubleElementTextContent(Element element, String tagName) {
        String textContent = getElementTextContent(element, tagName);
        return !textContent.isEmpty() ? Double.valueOf(textContent) : null;
    }

    private Integer getIntElementTextContent(Element element, String tagName) {
        String textContent = getElementTextContent(element, tagName);
        return !textContent.isEmpty() ? Integer.valueOf(textContent) : null;
    }
}