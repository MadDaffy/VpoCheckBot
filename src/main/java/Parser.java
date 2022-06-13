import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.json.XML;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    private File file = new File("D:/JavaDev/VpoCheckBot/test.xml");
    private URL url;

    public String getPage() throws IOException {

        url =  new URL("https://sana.sy/ru/?feed=rss2");
        String context = Parser.ReadContext(url);
        Parser.writeContext(file, context);

          try {
              JAXBContext jaxbContext = JAXBContext.newInstance(NewsPost.class);
              Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
              NewsPost que= (NewsPost) jaxbUnmarshaller.unmarshal(file);
          } catch (JAXBException e) {
              e.printStackTrace();
          }

          return file.toString();
    }

    public static void writeContext(File file, String context) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)))){
            bufferedWriter.write(context);
            bufferedWriter.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String ReadContext(URL url) {
        URLConnection urlConnection = null;
        try {
            urlConnection = url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {
            StringBuffer stringBuffer = new StringBuffer();
            do {
                stringBuffer.append(in.readLine() + "\n");
            } while (in.readLine() != null);
            return stringBuffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "FAIL";
    }

    @Getter
    @Setter
    @AllArgsConstructor
    class Article {
          private String url;
          private String text;

        @Override
        public String toString() {
            return "Статья " + text + "\n"
                    + " Ссылка " + url+ "\n"
                    +"------------------"+"\n";

        }
    }
}
