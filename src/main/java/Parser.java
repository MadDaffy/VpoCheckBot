import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
//import org.apache.commons.lang3.StringUtils.*;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class Parser {

    public String getPage(ArrayList<URL> urlList, String inputText) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            for (URL url : urlList) {
                DocumentBuilderFactory f =
                        DocumentBuilderFactory.newInstance();
                DocumentBuilder b = f.newDocumentBuilder();
                Document doc = b.parse(new InputSource(url.openStream()));
                doc.getDocumentElement().normalize();

//                NodeList headTitleList =
//                        doc.getElementsByTagName("title");
//                NodeList headLinkList =
//                        doc.getElementsByTagName("link");
//                NodeList headDescriptionList =
//                        doc.getElementsByTagName("description");
//
//                Element headTitleElem = (Element) headTitleList.item(0);
//                Element headLinkElem = (Element) headLinkList.item(0);
//                Element headDescriptionElem = (Element) headDescriptionList.item(0);
//                Node headTitleNode = headTitleElem.getChildNodes().item(0);
//                Node headLinkNode = headLinkElem.getChildNodes().item(0);
//                Node headDescriptionNode = headDescriptionElem.getChildNodes().item(0);
//                stringBuffer.append("===================================").append("\n")
//                        .append("Информационное Агенство:").append("\n")
//                        .append(headTitleNode.getNodeValue()).append("\n").append("\n")
//                        .append("Описание агенства:").append("\n")
//                        .append(headDescriptionNode.getNodeValue()).append("\n").append("\n")
//                        .append("Ссылка на новостной портал:").append("\n")
//                        .append(headLinkNode.getNodeValue()).append("\n")
//                        .append("===================================").append("\n").append("\n");
//                // loop through each item

                NodeList items = doc.getElementsByTagName("item");
                for (int i = 0; i < items.getLength(); i++) {
                    Node n = items.item(i);
                    if (n.getNodeType() != Node.ELEMENT_NODE)
                        continue;
                    Element e = (Element) n;
                    Node titleNode = null;
                    Node linkNode = null;
                    Node descriptionNode = null;
                    try {
                        NodeList titleList =
                                e.getElementsByTagName("title");
                        NodeList linkList =
                                e.getElementsByTagName("link");
                        NodeList descriptionList =
                                e.getElementsByTagName("description");
                        Element titleElem = (Element) titleList.item(0);
                        Element linkElem = (Element) linkList.item(0);
                        Element descriptionElem = (Element) descriptionList.item(0);
                        titleNode = titleElem.getChildNodes().item(0);
                        linkNode = linkElem.getChildNodes().item(0);
                        descriptionNode = descriptionElem.getChildNodes().item(0);
                    } catch (NullPointerException exception) {
                        continue;
                    }
                    if (titleNode.getNodeValue().toLowerCase().contains(inputText.toLowerCase())
                            || descriptionNode.getNodeValue().toLowerCase().contains(inputText.toLowerCase())) {
                        stringBuffer.append("Тема:").append("\n")
                                .append(titleNode.getNodeValue()).append("\n").append("\n")
                                .append("Содержание:").append("\n")
                                .append(descriptionNode.getNodeValue()).append("\n").append("\n")
                                .append("Ссылка на статью:").append("\n")
                                .append(linkNode.getNodeValue()).append("\n")
                                .append("----------------------").append("\n").append("\n");
                    }
                }
//                stringBuffer.append("+++++++++++++++++++++++++++++++++++++++++++++").append("\n").append("\n").append("\n");
            }

            return stringBuffer.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Что-то пошло не так"+"\n"
                +"Попробуйте еще раз ввести ключевую фразу/слово/часть слова ";
    }
}
