import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement
public class NewsPost {

    private String link;
    private String title;
    private String description;

//    public NewsPost() {
//    }
//
//    public NewsPost(String link, String title, String description) {
//        this.link = link;
//        this.title = title;
//        this.description = description;
//    }

    @XmlAttribute
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
    @XmlAttribute
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    @XmlAttribute
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
