import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Parser {

    public String getPage(URL url) throws IOException {

        try {
            DocumentBuilderFactory f =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder b = f.newDocumentBuilder();
            Document doc = b.parse(new InputSource(url.openStream()));

            doc.getDocumentElement().normalize();
            System.out.println("Root element: " +
                    doc.getDocumentElement().getNodeName());

            // loop through each item
            NodeList items = doc.getElementsByTagName("item");
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < items.getLength(); i++) {

                    Node n = items.item(i);
                    if (n.getNodeType() != Node.ELEMENT_NODE)
                        continue;
                    Element e = (Element) n;
                    NodeList titleList =
                            e.getElementsByTagName("title");
                    NodeList linkList =
                            e.getElementsByTagName("link");
                    NodeList descriptionList =
                            e.getElementsByTagName("description");
                    Element titleElem = (Element) titleList.item(0);
                    Element linkElem = (Element) linkList.item(0);
                    Element descriptionElem = (Element) descriptionList.item(0);
                    Node titleNode = titleElem.getChildNodes().item(0);
                    Node linkNode = linkElem.getChildNodes().item(0);
                    Node descriptionNode = descriptionElem.getChildNodes().item(0);
                    stringBuffer.append("Тема:").append("\n")
                            .append(titleNode.getNodeValue()).append("\n")
                            .append("Содержание:").append("\n")
                            .append(descriptionNode.getNodeValue()).append("\n")
                            .append("Ссылка на статью").append("\n")
                            .append(linkNode.getNodeValue()).append("\n")
                            .append("----------------------").append("\n");
            }

            return stringBuffer.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "someShit";
    }
}
