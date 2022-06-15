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

    private File file = new File("D:/JavaDev/VpoCheckBot/test.xml");
    private URL url;
    private int currentNew = 0;

    public String getPage() throws IOException {

//        url = "https://www.aa.com.tr/ru/rss/default?cat=live";
//
//
//        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//        try {
//            Document doc = factory.newDocumentBuilder().parse(url);
//            Element root = doc.getDocumentElement();
//            XPath xPath = XPathFactory.newInstance().newXPath();
//            XPathExpression expression = xPath.compile("");
//            NodeList nodeList = (NodeList) expression.evaluate(root, XPathConstants.NODESET);
//            return nodeList.toString();
//        } catch (ParserConfigurationException e) {
//            e.printStackTrace();
//        } catch (SAXException e) {
//            e.printStackTrace();
//        } catch (XPathExpressionException e) {
//            e.printStackTrace();
//        }
//        return "SomeShit";

        url = new URL("https://sana.sy/ru/?feed=rss2");
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
            for (int i = currentNew; stringBuffer.length() < 3000; i++) {
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


//    @Getter
//    @Setter
//    @AllArgsConstructor
//    class Article {
//        private String url;
//        private String text;
//
//        @Override
//        public String toString() {
//            return "Статья " + text + "\n"
//                    + " Ссылка " + url + "\n"
//                    + "------------------" + "\n";
//
//        }
//    }
}
