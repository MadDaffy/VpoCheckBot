package Service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Parser {

      public String getPage() throws IOException {

        StringBuffer stringBuffer = new StringBuffer();
        String url = "https://riafan.ru/category/tape_news";
        ArrayList<Article> articleList = new ArrayList<>();
        Document page = Jsoup.parse(new URL(url),10000);
        Element news = page.getElementsByAttributeValue("class", "container-fluid").first();
        Elements article = news.getElementsByTag("a");

          for (Element element : article) {
              String urlArticle = "https://riafan.ru"+element.attr("href");
              String text = element.text();
              stringBuffer.append(text)
                      .append("\n")
                      .append(urlArticle)
                      .append("\n")
                      .append("---------------------")
                      .append("\n");
              articleList.add(new Article(urlArticle,text));
          }
        String result = stringBuffer.toString();
        return result;
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
